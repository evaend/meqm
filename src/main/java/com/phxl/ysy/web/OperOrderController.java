package com.phxl.ysy.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.phxl.core.base.util.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phxl.core.base.entity.Pager;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.LocalStringUtils;
import com.phxl.ysy.service.ProcedureService;
import com.phxl.ysy.entity.Delivery;
import com.phxl.ysy.entity.Order;
import com.phxl.ysy.service.DeliveryService;
import com.phxl.ysy.service.OrderService;
import com.phxl.ysy.service.PackageService;
import com.phxl.ysy.web.dto.DeliveryDetailDto;
import com.phxl.ysy.web.dto.DeliveryPackageDto;
import com.phxl.ysy.web.dto.SettleGoodsDto;
import com.phxl.ysy.constant.CustomConst.BillPrefix;
import com.phxl.ysy.constant.CustomConst.DeliveryFstate;
import com.phxl.ysy.constant.CustomConst.DictName;
import com.phxl.ysy.constant.CustomConst.LoginUser;
import com.phxl.ysy.constant.CustomConst.OrderFstate;
import com.phxl.ysy.constant.CustomConst.OrderType;
import com.phxl.ysy.constant.CustomConst.YesOrNo;
import com.phxl.ysy.constant.GkAttribute;
import com.phxl.ysy.service.StaticDataService;

/**
 * 手术订单相关接口
 * @author 黄文君
 * @version 1.0
 * @date 2017年09月20日 17:46
 * @since JDK 1.6
 */
@Controller
@RequestMapping("order/oper")
public class OperOrderController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	OrderService            orderService;
	@Autowired
	DeliveryService         deliveryService;
	@Autowired
	PackageService          packageService;
	@Autowired
	StaticDataService       staticDataService;
	@Autowired
	ProcedureService        procedureService;

	/**
	 * 订单备货之前，清理临时包工具数据<p>
	 * 订单备货页面，订单未生成送货单之前，包工具临时数据清理。
	 * @author	黄文君
	 * @date	2017年9月4日 上午11:53:17
	 * @param	orderId
	 * @throws	ValidationException
	 * @return	Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/clearByOrderId")
	public void clearByOrderId(String orderId, HttpSession session) throws ValidationException {
		LocalAssert.notBlank(orderId, "订单Id，不能为空");
		Order order = orderService.find(Order.class, orderId);
		Assert.notNull(order, "订单（"+orderId+"）不存在");
		//手术订单备货期间的基本检查
		checkPermissionSettleGoodsForOper(session, order, "清理临时包工具");
		//某一个订单备货之前，清理临时包工具数据
		orderService.clearByOrderId(orderId);
	}

	/**
	 * 手术订单:临时保存备货数据
	 * @author	黄文君
	 * @date	2017年9月21日 下午
	 * @param	request
	 * @param	session
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("saveDraft4Oper")
	@ResponseBody
	public void saveSettleGoodsAsDraft(HttpServletRequest request, HttpSession session) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));//配置项:默认日期格式
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性

		//订单备货信息
		SettleGoodsDto dto = objectMapper.readValue(request.getReader(), SettleGoodsDto.class);
		if(logger.isTraceEnabled()){
			logger.trace("saveDraft4Oper => 订单备货信息dto: " + JSONUtils.toPrettyJsonLoosely(dto));
		}

		//备货信息
		Assert.notNull(dto, "订单备货信息，不能为空");
		String orderId = dto.getOrderId();
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		Order order = orderService.find(Order.class, orderId);
		Assert.notNull(order, "订单（"+orderId+"）不存在");

		//手术订单: 检查当前会话是否有权对该订单作备货操作
		checkPermissionSettleGoodsForOper(session, order, "临时保存备货数据");

		//备货产品列表
		List<DeliveryDetailDto> detailList = dto.getDetailList();
		if(CollectionUtils.isNotEmpty(detailList)) {
			for (int i = 0; i < detailList.size(); i++) {
				DeliveryDetailDto dd = detailList.get(i);
				LocalAssert.notBlank(dd.getTenderDetailGuid(), "招标明细guid，不能为空");
				Assert.notNull(dd.getAmount(), "发货数量，不能为空");
				LocalAssert.intGreaterThan(dd.getAmount().intValue(), 0, "发货数量，必需大于0");
				dd.setFsort(i + 1);//顺序号
			}
		}

		//手术包列表（横表）
		List<Map<String, Integer>> packageList = dto.getPackageList();
		//手术包列表（坚表）
		List<DeliveryPackageDto> packageAttrList = null;
		if(CollectionUtils.isNotEmpty(packageList)) {//如果有手术包
			Pager<Map<String, Object>> coditions = new Pager<Map<String, Object>>(false);
			coditions.addQueryParam("orgId", order.getrOrgId());
			coditions.addQueryParam("tfClo", DictName.GK_ATTRIBUTE);
			//查询全部骨科产品属性
			List<Map<String, String>> gkAttributes = staticDataService.findStaticDataList(coditions);
			String[] attributeIds = null;
			if(CollectionUtils.isNotEmpty(gkAttributes)){
				attributeIds = new String[gkAttributes.size()];
				for(int i=0; i<gkAttributes.size(); i++){
					Map<String, String> c = gkAttributes.get(i);
					attributeIds[i] = c.get("TF_CLO_CODE");
				}
			}else{
				throw new ValidationException("该供应商没有产品类型字典，不能做手术包的操作!!");
			}

			for(Map<String, Integer> packager: packageList){
				Integer packageId = packager.get("packageId");
				LocalAssert.notNull(packageId, "手术包：包序号，不能为空");
				LocalAssert.containKey(packager, "operTool", "手术器械，不能为空");
				for(String fname: packager.keySet()){
					Integer amount = packager.get(fname);
					if(ArrayUtils.contains(attributeIds, fname) || "operTool".equals(fname)){
						LocalAssert.notBlank(fname, "手术包：产品类型，不能为空");
						LocalAssert.notNull(amount, "手术包：数量，不能为空");
						LocalAssert.intGreaterEqual(amount, 0, "产品类型数量，必需大于或等于0，而实际值却是"+amount);
						if(amount==0) continue;//数量为0的跳过继续

						//包产品属性信息
						DeliveryPackageDto pa = new DeliveryPackageDto();
						pa.setPackageId(packageId);//包序号
						pa.setAttributeId(fname);//属性编码
						pa.setTfAmount(amount);//数量
						pa.setIsImplantFlag(!fname.equals("operTool") && !fname.equals(GkAttribute.STERILIZE.getCode())?YesOrNo.YES:YesOrNo.NO);//外来植入物:不是灭菌（12）,也不是手术器械（operTool）
						pa.setIsToolFlag(fname.equals("operTool")?YesOrNo.YES:YesOrNo.NO);//是否手术器械（operTool）
						packageAttrList = packageAttrList==null?new ArrayList<DeliveryPackageDto>():packageAttrList;
						packageAttrList.add(pa);
					}
				}
			}
		}

		//手术送货单：临时保存备货数据
		deliveryService.saveSettleGoodsAsDraft(orderId, detailList, packageAttrList, dto.getMaterialScope());
	}

	/**
	 * 手术订单备货生成送货单
	 * @author	黄文君
	 * @date	2017年9月21日 上午
	 * @param	orderId     订单id
	 * @throws	Exception
	 * @return	void
	 */
	@RequestMapping("settleGoods4Oper")
	@ResponseBody
	public void settleGoods4Oper(String orderId, HttpSession session) throws Exception {
		LocalAssert.notBlank(orderId, "订单id，不能为空");
		Order order = orderService.find(Order.class, orderId);
		Assert.notNull(order, "订单（"+orderId+"）不存在");

		//手术订单: 检查当前会话是否有权对该订单作备货操作
		Delivery delivery = checkPermissionSettleGoodsForOper(session, order, "备货操作");

		List<Map<String, Object>> detailList = deliveryService.findMaterials4OperBySendId(orderId);//查询指定手术送货单的产品列表
		Assert.notEmpty(detailList, "备货明细，不能为空");

		StringBuffer message = new StringBuffer();
		//手术包产品类型数量比较
		List<Map<String, Object>> check1 = packageService.checkAttributeAmountOfOperPackage(orderId);
		if(CollectionUtils.isNotEmpty(check1)){
			for(Map<String, Object> a: check1){
				int diff = ((Number)a.get("DIFF")).intValue();
				if (diff != 0) {
					message.append(a.get("ATTRIBUTE_NAME")).append(": 数量").append(diff>0 ? "缺少" : "超出").append(Math.abs(diff)).append("; ");
				}
			}
		}
		LocalAssert.isEmpty(message, "[手术包-产品类型数量核对] " + message.toString());

		//外带手术器械数量比较
		List<Map<String, Object>> check2 = packageService.checkToolAmountOfOperPackage(orderId);
		if(CollectionUtils.isNotEmpty(check2)){
			for(Map<String, Object> t: check2){
				int diff = ((Number)t.get("DIFF")).intValue();
				if(diff != 0){
					message.append("第"+t.get("PACKAGE_ID")+"个包").append(": 手术器械数量").append(diff>0 ? "超出":"缺少").append(Math.abs(diff)).append("; ");
				}
			}
		}
		LocalAssert.isEmpty(message, "[手术包-手术器械数量核对] " + message.toString());

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
		}

		//订单备货生成送货单
		deliveryService.settleGoods4Oper(isFreshSettleGood, delivery);
	}

	/**
	 * 手术订单: 检查当前会话是否有权对该订单作备货相关的操作
	 * @param   session        会话
	 * @param   order          订单信息
	 * @param   actionName    动作名
	 * @throws  ValidationException
	 */
	public Delivery checkPermissionSettleGoodsForOper(HttpSession session, Order order, String actionName) throws ValidationException {
		LocalAssert.notBlank(order.getOrderType(), "数据异常: 订单类型为空!");
		Long sessionOrgId = (Long)session.getAttribute(LoginUser.SESSION_USER_ORGID);//会话机构id
		//检查:用户不能跨机构处理订单
		if(order.getrOrgId().longValue()!=sessionOrgId && order.getfOrgId().longValue()!=sessionOrgId){
			throw new ValidationException("不支持处理其他机构的订单");
		}
		//检查:只有手术订单，才能进行手术订单的备货操作
		if(!order.getOrderType().equals(OrderType.OPER_ORDER)){
			throw new ValidationException("只有手术订单，才能进行该操作");
		}
		//检查:只有备货中的订单，才能进行备货操作
		if(!order.getFstate().equals(OrderFstate.PREPARE_GOODS)){//备货中
			throw new ValidationException("只有备货中的订单，才能"+actionName+"!");
		}
		//检查:只有订单的供方，才能进行备货操作
		if(order.getfOrgId().longValue() != sessionOrgId){
			throw new ValidationException("只有订单的供方，才能"+actionName+"!");
		}
		//检查:只有“备货中或验收不通过”的送货单，才能做备货操作
		Delivery delivery = deliveryService.find(Delivery.class, order.getOrderId());
		if(delivery!=null){
			if(!delivery.getFstate().equals(DeliveryFstate.AWAIT_SEND) && !delivery.getFstate().equals(DeliveryFstate.CHECK_NO_PASS)) {//状态:备货中或验收不通过
				String tip = LocalStringUtils.caseWhen(delivery.getFstate(), DeliveryFstate.AWAIT_CHECK, "已发货", DeliveryFstate.CHECK_PASSED, "已验收通过", "未识别的状态");
				throw new ValidationException("该送货单" + tip + "，不能做" + actionName + "!");
			}
		}
		return delivery;
	}

}
