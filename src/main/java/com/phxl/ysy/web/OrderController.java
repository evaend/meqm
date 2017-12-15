package com.phxl.ysy.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phxl.core.base.adapter.CustomDateTransfer;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.support.excel.hssf.*;
import com.phxl.core.base.util.*;
import com.phxl.ysy.entity.Address;
import com.phxl.ysy.entity.OrgInfo;
import com.phxl.ysy.entity.StorageInfo;
import com.phxl.ysy.service.ProcedureService;
import com.phxl.ysy.service.StorageService;
import com.phxl.ysy.dto.PatientOperDto;
import com.phxl.ysy.entity.Delivery;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.entity.OrderDetail;
import com.phxl.ysy.service.DeliveryService;
import com.phxl.ysy.service.OrderService;
import com.phxl.ysy.service.PackageService;
import com.phxl.ysy.service.TreatmentOperService;
import com.phxl.ysy.web.dto.DeliveryDetailDto;
import com.phxl.ysy.web.dto.DeliveryPackageDto;
import com.phxl.ysy.web.dto.SettleGoodsDto;
import com.phxl.ysy.constant.CustomConst;
import com.phxl.ysy.constant.CustomConst.*;
import com.phxl.ysy.constant.GkAttribute;
import com.phxl.ysy.service.StaticDataService;
import com.phxl.ysy.util.BillAdapter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OrderService			orderService;
	@Autowired
	DeliveryService			deliveryService;
	@Autowired
	ProcedureService		procedureService;
	@Autowired
	StaticDataService		staticDataService;
	@Autowired
	StorageService			storageService;
	@Autowired
	PackageService          packageService;
	@Autowired
	TreatmentOperService    treatmentOperService;
	
	/**
	 * 查询我的订单列表
	 * @author	黄文君
	 * @date	2017年6月12日 下午6:00:43
	 * @param	buyerOrSeller		查询方标识（B:我是需方-我是买家，S:我是供方-我是卖家）
	 * @param	rOrgNameLike		医疗机构（模糊查询关键字）
	 * @param	fOrgNameLike		供应商（模糊查询关键字）
	 * @param	orderNo				订单号（模糊查询关键字）
	 * @param	rOrgId				需方机构id
	 * @param	fOrgId				供方机构id
	 * @param	storageGuid			库房guid
	 * @param	orderType			订单类型
	 * @param	orderTypes			订单类型（多选）
	 * @param	fstate				订单状态
	 * @param	fstates				订单状态（多选）
	 * @param	amountStart			订单总金额-起始
	 * @param	amountEnd			订单总金额-截止
	 * @param	orderDateStart		下单时间-起始
	 * @param	orderDateEnd		下单时间-截止
	 * @param	cancleDateStart		取消时间-起始
	 * @param	cancleDateEnd		取消时间-截止
	 * @param	planNo				计划单号（预留）
	 * @param	applyNo				申请单号（预留）
	 * @param	pagesize			每页记录数
	 * @param   page				当前页码
	 * @param   sidx				排序字段名称
	 * @param   sord				排序方式（desc/descend：降序； asc/ascend：升序）
	 * @param	allowAutoPagable	是否自动分页（true:是，false:否）
	 * @return	Pager<Order>
	 * @throws	Exception 
	 */
	@RequestMapping("/findMyOrderList")
	@ResponseBody
	public Pager<Order> findOrderList(String buyerOrSeller, String rOrgNameLike, String fOrgNameLike, String orderNo, Long rOrgId, Long fOrgId,
										String storageGuid, String orderType, String orderTypes, String fstate, String fstates,
										Integer amountStart, Integer amountEnd, Date orderDateStart, Date orderDateEnd,
										Date cancleDateStart, Date cancleDateEnd, String planNo, String applyNo,
										Integer pagesize, Integer page, String sidx, String sord, 
										Boolean allowAutoPagable,
										HttpSession session) throws Exception {
		Pager<Order> pager = new Pager<Order>(allowAutoPagable==null?true:allowAutoPagable);
		pager.setPageSize(pagesize);
		pager.setPageNum(page);
		
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);
		String sessionOrgType = (String)session.getAttribute(LoginUser.SESSION_USER_ORG_TYPE);
		String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		pager.addQueryParam("sessionOrgId", sessionOrgId);
		pager.addQueryParam("sessionOrgType", sessionOrgType);
		pager.addQueryParam("sessionUserId", sessionUserId);
		
		if(!OrgType.PLATFORM.equals(sessionOrgType)){//非运营商
			LocalAssert.notEmpty(buyerOrSeller, "buyerOrSeller，不能为空");
			LocalAssert.contain(new String[]{"B","S"}, buyerOrSeller, "buyerOrSeller，上送值只能是B或者S");
			
			pager.addQueryParam("buyerOrSeller", buyerOrSeller);
			if("B".equals(buyerOrSeller)){//我是需方
				pager.addQueryParam("rOrgId", sessionOrgId);
				pager.addQueryParam("fOrgId", fOrgId);
			}else{//我是供方
				pager.addQueryParam("fOrgId", sessionOrgId);
				pager.addQueryParam("rOrgId", rOrgId);
				pager.addQueryParam("excludeFstates", new String[]{
						OrderFstate.DRAFT,
						OrderFstate.AWAIT_SEND,
						OrderFstate.SEND_FAIL
				});//排除:草稿、已提交、发送失败
			}
		}else{//运营商
			pager.addQueryParam("rOrgId", rOrgId);
			pager.addQueryParam("fOrgId", fOrgId);
		}
		
		pager.addQueryParam("rOrgNameLike", rOrgNameLike);
		pager.addQueryParam("fOrgNameLike", fOrgNameLike);
		pager.addQueryParam("orderNo", orderNo);
		pager.addQueryParam("storageGuid", storageGuid);
		pager.addQueryParam("orderType", orderType);
		if(StringUtils.isNotBlank(orderTypes)){
			String[] ots = orderTypes.split(",");
			pager.addQueryParam("orderTypes", ots.length>0 ? ots : null);
		}
		//订单状态
		if(StringUtils.isNotBlank(fstate)){
			pager.addQueryParam("fstate", fstate);
		}else if(StringUtils.isNotBlank(fstates)){
			String[] fs = fstates.split(",");
			pager.addQueryParam("fstates", fs.length>0 ? fs : null);
		}else{
			pager.addQueryParam("excludeCancledOrder", true);//排除取消的订单
		}
		
		pager.addQueryParam("amountStart", amountStart);
		pager.addQueryParam("amountEnd", amountEnd);
		pager.addQueryParam("orderDateStart", orderDateStart);
		pager.addQueryParam("orderDateEnd", orderDateEnd);
		pager.addQueryParam("cancleDateStart", cancleDateStart);
		pager.addQueryParam("cancleDateEnd", cancleDateEnd);
		pager.addQueryParam("planNo", planNo);
		pager.addQueryParam("applyNo", applyNo);
		
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
			pager.addQueryParam("resultMapName", "com.phxl.ysy.dao.OrderMapper.BaseResultExtMap");//设置ResultMap映射关系
			pager.addQueryParam("orderField", sidx);
			pager.addQueryParam("orderMark", LocalStringUtils.forceConstIgnoreCase("asc", "desc", sord, "ascend", "asc"));
		}
		
		//查询我的订单列表（运营人员）
		pager.setRows(orderService.findOrderList(pager));
		return pager;
	}
	
	/**
	 * 查询一个订单的明细列表
	 * @author	黄文君
	 * @date	2017年6月14日 下午6:00:55
	 * @param	orderId		订单id
	 * @param   sidx		排序字段名称
	 * @param   sord		排序方式（desc/descend：降序； asc/ascend：升序）
	 * @throws	ValidationException
	 * @return	List<OrderDetail>
	 */
	@RequestMapping("/findDetailListByOrderId")
	@ResponseBody
	public List<OrderDetail> findDetailListByOrderId(String orderId, String sidx, String sord) throws ValidationException {
		LocalAssert.notBlank(orderId, "orderId，不能为空");
		
		Pager<OrderDetail> pager = new Pager<OrderDetail>(false);
		pager.addQueryParam("orderId", orderId);
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
			pager.addQueryParam("resultMapName", "com.phxl.ysy.dao.OrderDetailMapper.BaseResultExtMap");//设置ResultMap映射关系
			pager.addQueryParam("orderField", sidx);
			pager.addQueryParam("orderMark", LocalStringUtils.forceConstIgnoreCase("asc", "desc", sord, "ascend", "asc"));
		}
		
		//查询一个订单的明细列表
		return orderService.findDetailListByOrderId(pager);
	}
	
	/**
	 * 判断指定订单是否已经备货完成
	 * @author	黄文君
	 * @date	2017年6月14日 下午6:02:07
	 * @param	orderId		订单id
	 * @throws	ValidationException
	 * @return	Boolean
	 */
	@RequestMapping("/judgeFullReceiveToBuyer")
	@ResponseBody
	public Boolean judgeFullReceiveToBuyer(String orderId, HttpSession session) throws ValidationException {
		LocalAssert.notBlank(orderId, "orderId，不能为空");

		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);

		Order origin = orderService.find(Order.class, orderId);
		Assert.notNull(origin, "订单（"+orderId+"）不存在");
		//检查:用户不能跨机构查看订单信息
		if(ObjectUtils.notEqual(origin.getrOrgId(), sessionOrgId)){
			throw new ValidationException("不支持处理其他机构的订单");
		}
		
		//判断指定订单是否已经备货完成
		return orderService.judgeFullReceiveToBuyer(orderId) == 0;
	}
	
	/**
	 * 保存草稿
	 * @author	黄文君
	 * @date	2017年6月26日 下午2:07:31
	 * @param	request
	 * @param	session
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("/saveDraft")
	@ResponseBody
	public String saveDraft(HttpServletRequest request, HttpSession session) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项:默认日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		
		//获取会话中的信息
		String sessionUserId = (String)session.getAttribute(LoginUser.SESSION_USERID);
		String sessionUserName = (String)session.getAttribute(LoginUser.SESSION_USERNAME);
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);
		
		//订单主信息
		Order od = objectMapper.readValue(request.getReader(), Order.class);
		Assert.notNull(od, "请上送订单的信息!");
		LocalAssert.notBlank(od.getStorageGuid(), "请选择库房");
		LocalAssert.notBlank(od.getAddrGuid(), "地址主键，不能为空");
		//Assert.notNull(od.getExpectDate(), "请指定：期望到货日期");

		//送货地址（库房地址）
		Address addr = orderService.find(Address.class, od.getAddrGuid());
		Assert.notNull(addr, "地址不存在（"+od.getAddrGuid()+"）");
		
		//订单产品明细列表
		Assert.notEmpty(od.getDetailList(), "请上送订单的产品明细!");
		//检查产品明细上送项
		for(OrderDetail d: od.getDetailList()){
			LocalAssert.notBlank(d.getTenderDetailGuid(), "供应产品guid，不能为空");
			Assert.notNull(d.getAmount(), "采购数量，不能为空");
			if(d.getAmount() <= 0){
				throw new ValidationException("采购数量，必需是正整数");
			}
		}
		
		Long[] fOrgIds = orderService.findSuppliersByDetails(od.getDetailList());
		Assert.notEmpty(fOrgIds, "未明确产品供应商");
		if(logger.isDebugEnabled()){
			logger.debug("该订单涉及{}个供应商:{}", fOrgIds.length, Arrays.toString(fOrgIds));
		}
		
		Order order = new Order();
		boolean isEdit = false;//是否编辑操作
		if(StringUtils.isNotEmpty(od.getOrderId())){
			isEdit = true;
			Order origin = orderService.find(Order.class, od.getOrderId());
			Assert.notNull(origin, "订单（"+od.getOrderId()+"）不存在");
			order.setOrderId(od.getOrderId());
			logger.debug("草稿编辑操作: orderId= " + od.getOrderId());
		}else{
			order.setOrderId(IdentifieUtil.getGuId());//订单id
			logger.debug("草稿新建操作");
		}
		order.setrOrgId(sessionOrgId);//需方机构id: sessionOrgId
		order.setfOrgId(null);/**草稿状态时，多供方不明确*/
		order.setStorageGuid(od.getStorageGuid());//需求库房
		order.setbStorageGuid(storageService.findRootByStorageGuid(od.getStorageGuid()));//采购库房
		order.setOrderNo(null);/**草稿状态时，没有单号*/
		order.setFstate(OrderFstate.DRAFT);//状态:草稿
		order.setOrderDate(new Date());//制单日期
		order.setSendDate(null);//提交日期（发送）
		order.setOrderUserid(sessionUserId);//制单人id
		order.setOrderUsername(sessionUserName);//制单人姓名
		order.setTfAddress(addr.getTfAddress());//送货地址
		order.setLxr(addr.getLinkman());//联系人
		order.setLxdh(addr.getLinktel());//联系电话
		order.setDeptGuid(null);//科室guid
		order.setDeptName(null);//科室名称
		order.setExpectDate(od.getExpectDate());//期望到货日期
		order.setSendUserid(sessionUserId);//发送人id
		order.setOrderType(OrderType.ORDER);//订单类型:普耗订单
		order.setFsource(CustomConst.OrderFsource.SELF_ORDER);//订单来源：自建订单
		order.setAddrGuid(od.getAddrGuid());//地址guid
		
		//保存草稿信息及其产品列表
		orderService.saveDraft(isEdit, order, od.getDetailList());
		return order.getOrderId();
	}
	
	/**
	 * 直接提交订单
	 * @author	黄文君
	 * @date	2017年6月14日 下午6:03:13
	 * @param	request
	 * @param	session
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("/createOrder")
	@ResponseBody
	public void summitOrder(HttpServletRequest request, HttpSession session) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项:默认日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		
		//订单主信息
		Order order = objectMapper.readValue(request.getReader(), Order.class);
		order.setOrderType(OrderType.ORDER);//订单类型：普耗订单
		order.setFsource(CustomConst.OrderFsource.SELF_ORDER);//订单来源：自建订单
		
		//提交订单（共用）
		orderService.summitOrderInternal(order, session);
	}
	
	/**
	 * 更新订单状态
	 * @author	黄文君
	 * @date	2017年6月14日 下午6:07:35
	 * @param	orderId		订单id
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("/updateFstate")
	@ResponseBody
	public void updateOrderFstate(String orderId, String fstate, String cancleReason,  HttpSession session) throws Exception {
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		String[] orderIds = orderId.split(",");
		LocalAssert.notEmpty(orderIds, "订单id，至少指定一个");
		LocalAssert.notBlank(fstate, "订单状态，不能为空");
		LocalAssert.contain(
				new String[]{
						OrderFstate.AWAIT_SEND,
						OrderFstate.CANCEL,
						OrderFstate.AWAIT_AGGREE,
						OrderFstate.PREPARE_GOODS,
						OrderFstate.CLOSED,
						OrderFstate.REJECT,
						"-1"},
		fstate, "订单状态更新：非法状态"+fstate);

		//更新订单状态，并添加订单状态更新记录（单个与批量同时支持）
		orderService.updateOrderFstate(orderIds, fstate, cancleReason, session);
	}
	
	/**
	 * 普耗、高值、结算订单备货生成送货单
	 * @author	黄文君
	 * @date	2017年6月18日 下午2:23:30
	 * @param	request
	 * @param	session
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("/settleGoods")
	@ResponseBody
	public void settleGoods(HttpServletRequest request, HttpSession session) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项:默认日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		
		//订单备货信息
		SettleGoodsDto dto = objectMapper.readValue(request.getReader(), SettleGoodsDto.class);
		Assert.notNull(dto, "订单备货信息，不能为空");
		String orderId = dto.getOrderId();
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		
		List<DeliveryDetailDto> detailList = dto.getDetailList();
		Assert.notEmpty(detailList, "备货明细，不能为空");
		for(int i=0; i<detailList.size(); i++){
			DeliveryDetailDto dd = detailList.get(i);
			
			LocalAssert.notBlank(dd.getOrderDetailGuid(), "订单明细id，不能为空");
			Assert.notNull(dd.getAmount(), "发货数量，不能为空");
			if(dd.getAmount().intValue()<=0){
				throw new ValidationException("发货数量，必需大于0");
			}
			//LocalAssert.notBlank(dd.getFlot(), "批次，不能为空");
			//Assert.notNull(dd.getProdDate(), "生产日期，不能为空");
			//Assert.notNull(dd.getUsefulDate(), "有效期，不能为空");
			dd.setFsort(i+1);//顺序号
		}
		
		Order order = orderService.find(Order.class, orderId);
		Assert.notNull(order, "订单（"+orderId+"）不存在");
		//检查:用户不能跨机构处理订单
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);//会话机构id
		if(order.getrOrgId().longValue()!=sessionOrgId && order.getfOrgId().longValue()!=sessionOrgId){
			throw new ValidationException("不支持处理其他机构的订单");
		}
		
		LocalAssert.contain(new String[]{OrderType.ORDER, OrderType.HIGH_ORDER, OrderType.SETTLE_ORDER}, order.getOrderType(), 
				"只有普耗、高值、结算订单，才能进行该操作");
		
		//检查:只有备货中的订单，才能进行备货操作
		if(!order.getFstate().equals(OrderFstate.PREPARE_GOODS)){//备货中
			throw new ValidationException("只有备货中的订单，才能进行备货操作");
		}
		
		//检查:指定订单，过滤出非法备货的备货产品
		List<Map<String, Object>> checkResult = orderService.filterSettleGoods(orderId, detailList);
		StringBuffer error = new StringBuffer();
		if(CollectionUtils.isNotEmpty(checkResult)){
			for(Map<String, Object> r: checkResult){
				logger.debug("[订单备货:过滤非法数据] {}|{}|{} => 采购数量:{}， 已发货数量：{}，剩余发货数量：{}，请求发货数量：{}", 
						new Object[]{
							r.get("MATERIAL_NAME"), r.get("FMODEL"), r.get("SPEC"),
							r.get("TOTAL_AMOUNT"), r.get("SENDOUT_AMOUNT"), r.get("ALLOW_AMOUNT"), r.get("REQUEST_AMOUNT")
						}
				);
				
				if(r.get("ORDER_DETAIL_GUID")==null){
					error.append("未知的订单产品（").append(r.get("REQUEST_ORDER_DETAIL_GUID")).append("）");
				} else if(r.get("DIFFERENCE")==null){
					error.append("订单产品的数量值可能有空值，需检查数据");
				} else {
					error.append("[发货数量超出检查]");
					error.append(r.get("MATERIAL_NAME")).append("/").append(r.get("FMODEL")).append("/").append(r.get("SPEC"));
					error.append(": 可发货数量是").append(r.get("ALLOW_AMOUNT")).append("，实际请求数量是").append(r.get("REQUEST_AMOUNT"));
					error.append("，超出").append(Math.abs(((Number)r.get("DIFFERENCE")).longValue())).append("，单位:").append(r.get("TENDER_UNIT"));
				}
				error.append("<br/>");
			}
		}
		if(error.length()>0){
			throw new ValidationException("订单备货数据检查:" + "<p>" + error.toString());
		}
		
		//检查:判断指定订单是否已经备货完成（true:全部已发货；false：未发货或部分发货）
		int difference = orderService.judgeFullReceiveToBuyer(orderId);
		if(difference==0){
			throw new ValidationException("订单产品已经全部备货完成，不能继续备货");
		}else if(difference<0){
			throw new ValidationException("产品发货数量超过采购数量，请通知实施人员核实处理。");
		}
		
		Delivery delivery = new Delivery(order);
		delivery.setSendId(IdentifieUtil.getGuId());
		String billPrefix = BillAdapter.prefixAdapter(delivery.getOrderType());
		delivery.setSendNo(procedureService.callSpGetBill(sessionOrgId, "送货单", billPrefix, 5));//送货单号
		delivery.setSendDate(new Date());
		delivery.setFstate(DeliveryFstate.AWAIT_SEND);//待发货
		delivery.setSendUserid((String)session.getAttribute(LoginUser.SESSION_USERID));
		delivery.setSendUsername((String)session.getAttribute(LoginUser.SESSION_USERNAME));
		
		//订单备货生成送货单
		deliveryService.settleGoods(delivery, detailList);
	}
	
	/**
	 * 手术订单备货生成送货单
	 * @author	黄文君
	 * @date	2017年9月1日 上午10:33:44
	 * @param	request
	 * @param	session
	 * @throws	Exception
	 * @return	void
	 * @Deprecated  由于前端实现困难，换成使用OperOrderController#settleGoods4Oper(String, HttpSession)。
	 */
	@RequestMapping("settleGoods4Oper")
	@ResponseBody
	@Deprecated
	public void settleGoods4Oper(HttpServletRequest request, HttpSession session) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项:默认日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		
		//订单备货信息
		SettleGoodsDto dto = objectMapper.readValue(request.getReader(), SettleGoodsDto.class);
		
		//备货信息
		Assert.notNull(dto, "订单备货信息，不能为空");
		String orderId = dto.getOrderId();
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		Order order = orderService.find(Order.class, orderId);
		Assert.notNull(order, "订单（"+orderId+"）不存在");
		LocalAssert.notBlank(order.getOrderType(), "数据异常: 订单类型为空!");

		//备货产品列表
		List<DeliveryDetailDto> detailList = dto.getDetailList();
		Assert.notEmpty(detailList, "备货明细，不能为空");
		for(int i=0; i<detailList.size(); i++){
			DeliveryDetailDto dd = detailList.get(i);
			LocalAssert.notBlank(dd.getTenderDetailGuid(), "招标明细guid，不能为空");
			Assert.notNull(dd.getAmount(), "发货数量，不能为空");
			LocalAssert.intGreaterThan(dd.getAmount().intValue(), 0, "发货数量，必需大于0");
			dd.setFsort(i+1);//顺序号
		}
		
		//手术包列表（横表）
		List<Map<String, Integer>> packageList = dto.getPackageList();
		//手术包列表（坚表）
		List<DeliveryPackageDto> packageAttrList = new ArrayList<DeliveryPackageDto>();
		if(CollectionUtils.isNotEmpty(packageList)) {//如果有手术包
			Pager<Map<String, Object>> coditions = new Pager<Map<String, Object>>(false);
			coditions.addQueryParam("orgId", order.getrOrgId());
			coditions.addQueryParam("tfClo", DictName.GK_ATTRIBUTE);
			//查询全部骨科产品属性
			List<Map<String, String>> gkAttributes = staticDataService.findStaticDataList(coditions);
			//全部产品属性码值映射
			Map<String, String> attributeMapping = new HashMap<String, String>();

			//统计汇总:根据产品明细列表，统计各产品属性分布
			Map<String, BigDecimal> subtotalAOfAttributeIds = orderService.subtotalOfAttributeIds(gkAttributes, detailList);
			LocalAssert.notEmpty(subtotalAOfAttributeIds, "统计汇总:产品列表各产品属性分布，返回结果不能为空");

			//统计汇总（前端）:根据手术包列表，统计各产品属性分布
			Map<String, Integer> subtotalBOfAttributeIds = new LinkedHashMap<String, Integer>();
			List<Map<String, Integer>> toolAmountListByPackageId = new ArrayList<Map<String, Integer>>();
			for(Map<String, String> attr: gkAttributes){
				subtotalBOfAttributeIds.put(attr.get("TF_CLO_CODE"), 0);
				attributeMapping.put(attr.get("TF_CLO_CODE"), attr.get("TF_CLO_NAME"));
			}
			for(Map<String, Integer> packager: packageList){
				Integer packageId = packager.get("packageId");
				LocalAssert.notNull(packageId, "手术包：包序号，不能为空");
				if(!packager.containsKey("operTool")){
					throw new ValidationException("手术器械，不能为空");
				}
				for(String fname: packager.keySet()){
					Integer amount = packager.get(fname);
					if(!ArrayUtils.contains(new String[]{"packageId", "hasImplantFlag"}, fname)){
						LocalAssert.notBlank(fname, "手术包：产品类型，不能为空");
						LocalAssert.notNull(amount, "手术包：数量，不能为空");
						LocalAssert.intGreaterEqual(amount, 0, "产品类型数量，必需大于或等于0，而实际值却是"+amount);

						Integer preAmount = (Integer) ObjectUtils.defaultIfNull(subtotalBOfAttributeIds.get(fname),0);
						//明确每个包外带手术器械数量
						if (fname.equals("operTool")) {
							Map<String, Integer> pamount = new HashMap<String, Integer>();
							pamount.put("packageId", packageId);
							pamount.put("toolAmount", amount);
							toolAmountListByPackageId.add(pamount);
						} else if(amount <= 0) {
							continue;//数量为0的跳过继续
						} else {
							//该产品属性的数量递增
							subtotalBOfAttributeIds.put(fname, preAmount + amount);
						}
						
						//包产品属性信息
						DeliveryPackageDto pa = new DeliveryPackageDto();
						pa.setPackageId(packageId);//包序号
						pa.setAttributeId(fname);//属性编码
						pa.setTfAmount(amount);//数量
						pa.setIsImplantFlag(!fname.equals("operTool") && !fname.equals(GkAttribute.STERILIZE.getCode())?YesOrNo.YES:YesOrNo.NO);//外来植入物
						pa.setIsToolFlag(fname.equals(GkAttribute.TOOL.getCode())?YesOrNo.YES:YesOrNo.NO);//是否工具:10工具, 12灭菌
						packageAttrList.add(pa);
					}
				}
			}
			
			if(logger.isTraceEnabled()){
				logger.trace("\n\t gkAttributes={}\n\t subtotalAOfAttributeIds={}\n\t subtotalBOfAttributeIds={}", new String[]{
						JSONUtils.toJsonLoosely(gkAttributes), JSONUtils.toJsonLoosely(subtotalAOfAttributeIds), JSONUtils.toJsonLoosely(subtotalBOfAttributeIds)
				});
			}

			//检查:送货产品列表中各类产品属性的数量是手术包各产品属性拆分的数据，两边必需相等
			StringBuffer message = new StringBuffer();
			for(String code: subtotalAOfAttributeIds.keySet()){
				if(!code.equals("operTool")) {
					int offset = subtotalAOfAttributeIds.get(code).intValue() - subtotalBOfAttributeIds.get(code);
					if (offset != 0) {
						message.append(attributeMapping.get(code)).append(": 数量").append(offset > 0 ? "缺少" : "超出").append(Math.abs(offset)).append("; ");
					}
				}
			}
			String msg = message.toString();
			LocalAssert.isEmpty(msg, "[手术包产品类型数量核对] " + msg);

			//检查:外带手术器械数量核对
			List<Map<Integer, Integer>> diffes = packageService.checkToolAmountOfOperPerPackage(orderId, toolAmountListByPackageId);
			for(Map<Integer, Integer> entry: diffes){
				Integer packageId = entry.get("packageId");
				Integer diff = entry.get("diff");
				if(diff != 0){
					message.append("第"+packageId+"个包").append(": 数量").append(diff>0?"超出":"缺少").append(Math.abs(diff)).append("; ");
				}
			}
			msg = message.toString();
			LocalAssert.isEmpty(msg, "[每个手术包的手术器械数量核对] " + msg);
		}
		
		//检查:用户不能跨机构处理订单
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);//会话机构id
		if(order.getrOrgId().longValue()!=sessionOrgId && order.getfOrgId().longValue()!=sessionOrgId){
			throw new ValidationException("不支持处理其他机构的订单");
		}
		
		if(!order.getOrderType().equals(OrderType.OPER_ORDER)){
			throw new ValidationException("只有手术订单，才能进行该操作");
		}
		
		//检查:只有备货中的订单，才能进行备货操作
		if(!order.getFstate().equals(OrderFstate.PREPARE_GOODS)){//备货中
			throw new ValidationException("只有备货中的订单，才能进行备货操作");
		}
		
		Delivery delivery = orderService.find(Delivery.class, orderId);
		Boolean isFreshSettleGood = false;//是否首次备货
		if(delivery==null){
			isFreshSettleGood = true;
			delivery = new Delivery(order);
			delivery.setSendId(orderId);//强调: sendId就用orderId
			delivery.setSendNo(procedureService.callSpGetBill(order.getrOrgId(), "手术送货单", BillPrefix.SD, 5));//送货单号
			delivery.setSendDate(new Date());
			delivery.setFstate(DeliveryFstate.AWAIT_SEND);//待发货
			delivery.setSendUserid((String)session.getAttribute(LoginUser.SESSION_USERID));
			delivery.setSendUsername((String)session.getAttribute(LoginUser.SESSION_USERNAME));
		}else if(delivery.getFstate().equals(DeliveryFstate.AWAIT_CHECK)){//状态:待需方验收
			throw new ValidationException("送货单已经发货，不能继续备货!");
		}
		
		//订单备货生成送货单
		deliveryService.settleGoods4Oper(isFreshSettleGood, delivery, detailList, packageAttrList);
	}

}
