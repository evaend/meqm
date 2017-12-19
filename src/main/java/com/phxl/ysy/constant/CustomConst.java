/** 
 * Project Name:ysynet 
 * File Name:CustomConst.java 
 * Package Name:com.phxl.ysynet.common.constant 
 * Date:2015年9月25日下午3:15:37 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
 */

package com.phxl.ysy.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义类: 用于定义系统中用到的常量值或者码值
 * @date	2015年9月25日 下午3:15:42
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class CustomConst {

	/**
	 * 用户级别
	 * @date	2017年3月23日 下午4:38:31
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class UserLevel {
		/**01、系统管理员*/
		public static final String SUPER_ADMIN = "01";
		/**02、机构管理员*/
		public static final String ORG_ADMIN = "02";
		/**03、机构操作员*/
		public static final String ORG_USER = "03";
	}
	
	/**
	 * 机构类型
	 * @date	2017年3月23日 下午4:38:31
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class OrgType {
		/**01、医院*/
		public static final String HOSPITAL = "01";
		/**02、供应商*/
		public static final String SUPPLIER = "02";
		/**03、监管部门*/
		public static final String GOV_SUPERVISE = "03";
		/**09、运营商（服务商）*/
		public static final String PLATFORM = "09";
	}
	
	/**
	 * [系统管理]: 组类型
	 * @date	2017年3月23日 下午4:56:55
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class GroupType {
		/**01：系统组*/
		public static final String SYSTEM_GROUP = "01";
		/**02：机构引用组（机构核心组）*/
		public static final String ORG_REF = "02";
		/**03：机构自定义组*/
		public static final String ORG_GROUP = "03";
	}
	
	/**
	 * 系统内置组编码
	 * @date	2017年3月29日 上午9:46:36
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class SysGroupCode {
		/**系统管理员标准组*/
		public static final String SUPERMAN_STD_GROUP = "SUPERMAN_STD";
		/**医院标准组*/
		public static final String HOSPITAL_STD_GROUP = "HOSP_STD";
		/**供应商标准组*/
		public static final String SUPLLIER_STD_GROUP = "SUPL_STD";
		/**监管部门标准组*/
		public static final String GOV_STD_GROUP = "GOV_STD";
		/**运营商标准组*/
		public static final String PLATFORM_STD_GROUP = "PLATFORM_STD";
	}
	
	/**
	 * 用户成功登录后存储在session里的信息
	 */
	public final static class LoginUser {
		/**用户对象*/
		public final static String SESSION_USER_INFO = "sessionUserInfo";
		/**用户ID*/
		public final static String SESSION_USERID = "sessionUserid";
		/**用户NO*/
		public final static String SESSION_USERNO = "sessionUserno";
		/**用户名称*/
		public final static String SESSION_USERNAME = "sessionUsername";
		/**用户密码*/
		public final static String SESSION_PASSWORD = "sessionPassword";
		/**用户机构ID*/
		public final static String SESSION_USER_ORGID = "sessionOrgid";
		/**用户机构名称*/
		public final static String SESSION_USER_ORGNAME = "sessionOrgname";
		/**用户机构类型*/
        public final static String SESSION_USER_ORG_TYPE = "sessionOrgType";
		/**用户级别*/
        public final static String SESSISON_USER_LEVEL = "sessisonUserLevel";
		/**用户库房GUID*/
        public final static String CUR_USER_STORAGE_GUID = "sessionStorageGuid";
        /**用户库房编号*/
        public final static String CUR_USER_STORAGE_ID = "sessionStorageId";
        /**用户库房所属的库房类型0:自有库房，1：第三方来源*/
        public final static String CUR_USER_STORAGE_SOURCE_TPYE = "sessionSSourceType";
    	/**当前登录用户模块权限列表-会话*/
		public final static String CUR_USER_MENULIST = "curUserMenuList";
	}
	
	/**
	 * 启用|停用状态码（只适用: 1启用、0停用，按需使用）
	 */
	public static final class FlagCode {
		/** 启用 */
		public static final short usable = 1;
		/** 停用 */
		public static final short unusable = 0;
	}
	
	/**
	 * 状态码: 启用|停用|注销
	 * @date	2017年3月23日 下午4:27:37
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class Fstate {
		/**01:表示启用*/
		public static final String USABLE = "01";
		/**00:表示禁用*/
		public static final String DISABLE = "00";
		/**02:注销（表示: 移除、物理删除），不可再变*/
		public static final String REMOVED = "02";
	}
	
	/**用户默认密码*/
	public static final String DEFAULT_PASSWORD = "999999";

	/**
	 * 邮件发送状态
	 * @date	2017年4月12日 上午10:28:30
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class EmailFstate {
		/**发送成功*/
		public static final String SUCCESS = "01";
		/**发送失败*/
		public static final String FAIL = "02";
		/**终止发送*/
		public static final String CLOSED = "03";
	}
	
	/**
	 * 请求返回状态，本系统另外定义的（目前都是失败状态）
	 * 2017年4月13日 上午11:16:02
	 * @author 陶悠
	 */
	public static final class ResponseStatus {
		/**失败：未登录*/
		public static final int UNLOGIN = 999;
		/**失败：token验证失败*/
		public static final int TOKENFAIL = 998;
		/**失败：非法接口访问*/
		public static final int PERMISSIONREFUSE = 997;
	}
	
	/**
	 * 库房级别
	 * @date	2017年4月25日 下午4:43:11
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class StorageLevel {
		/**一级库房*/
		public static final String TOP = "01";
		/**二级库房*/
		public static final String SECOND = "02";
		/**三级库房*/
		public static final String THIRD = "03";
	}
	
	/**
	 * 库房类型
	 * @date	2017年4月25日 下午4:42:57
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class StorageType {
		/**01、实物库房*/
		public static final String REALLY = "01";
		/**02、虚拟库房*/
		public static final String VIRTUAL = "02";
		/**03、混合库房*/
		public static final String COMPOSITE = "03";
	}
	
	
	/**
	 * 库房:货物来源
	 * @date	2017年4月25日 下午4:42:40
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class StorageSourceType {
		/**00、招标*/
		public static final String TENDER = "00";
		/**01、库房*/
		public static final String REF_STORAGE = "01";
		/**02、标准产品库*/
		public static final String STD_PRDS = "02";
	}
	
	/**
	 * 是否标识（01是、00否）
	 * @date	2017年5月5日 下午4:27:25
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class YesOrNo {
		/**01、是*/
		public static final String YES = "01";
		/**00、否*/
		public static final String NO = "00";
	}
	
	/**
	 * 产品类型（00 医疗器械 01 办公耗材）
	 * @date	2017年5月26日
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class MaterialType {
		/**00、医疗器械*/
		public static final String YLQX = "00";
		/**01、办公耗材*/
		public static final String BGHC = "01";
	}
	
	/**
	 * 产品状态（00 到期 01正常 02异常）
	 * @date	2017年5月26日
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class ProdCertFstate {
		/**00、到期*/
		public static final String EXPIRE = "00";
		/**01、正常*/
		public static final String USEFUL = "01";
		/**02、异常*/
		public static final String WRONG = "02";
	}
	
	/**
	 * 审核状态 (00、保存 01、待审核，02、审核通过，03、审核不通过)
	 * @date	2017年5月26日
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class AuditFstate {
		/**00、保存 */
		public static final String DRAFT = "00";
		/**01、待审核*/
		public static final String AWAIT_AUDIT = "01";
		/**02、审核通过*/
		public static final String PASSED = "02";
		/**03、审核不通过*/
		public static final String NO_PASS = "03";
	}
	
	/**
	 * 申请类型
	 * @date	2017年6月1日 下午2:45:49
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class AuditType {
		/**00 、新产品*/
		public static final String NEW_MATERIAL = "00";
		/**01、变更产品*/
		public static final String TENDER_MATERIAL_CHANG = "01";
		/**02、申请单*/
		public static final String APPLY = "02";
	}
	
	/**
	 * 字典项编码定义
	 * @date	2017年6月5日 下午1:52:21
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class DictName {
		/**订单状态*/
		public static final String ORDER_FSTATE = "ORDER_FSTATE";
		/**单位*/
		public static final String UNIT = "UNIT";
		/**订单状态*/
		public static final String ORDER_TYPE = "ORDER_TYPE";
		/**运营端库房配置参数*/
		public static final String STORAGE_CONFIGYY = "storageConfigYY";
		/**客户端库房配置参数*/
		public static final String STORAGE_CONFIGKH = "storageConfigKH";
		/**运营端科室配置参数*/
		public static final String DEPT_CONFIGYY = "deptConfigYY";
		/**骨科产品属性*/
		public static final String GK_ATTRIBUTE = "GKATTRIBUTE";
		/**骨科工具*/
		public static final String GK_TOOL = "ATTRIBUTE_TOOL";
		/**申请单类型*/
		public static final String DEPT_BTYPE = "DEPT_BTYPE";
	}
	
	/**
	 * 消息项编码定义
	 * @date	2017年6月8日 下午1:54:52
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class MsgItemCode {
		/**IT00007: 新产品录入申请-审核结果通知*/
		public static final String AUDIT_NEW_PRODUCT  = "IT00007";
		/**IT00008: 招标产品变更申请-审核结果通知*/
		public static final String AUDIT_CHANGE_TENDER_CERT = "IT00008";
	}
	
	/**
	 * 订单状态
	 * @date	2017年6月14日 下午5:06:42
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class OrderFstate {
		/**00-草稿*/
		public static final String DRAFT = "00";
		/**20-已提交（待发送）*/
		public static final String AWAIT_SEND = "20";
		/**26-发送失败*/
		public static final String SEND_FAIL = "26";
		/**29-取消订单（需方）*/
		public static final String CANCEL = "29";
		/**30-待供方确认（已发送）*/
		public static final String AWAIT_AGGREE = "30";
		/**40 备货中（供方）*/
		public static final String PREPARE_GOODS = "40";
		/**70 关闭（供方）*/
		public static final String CLOSED = "70";
		/**80 订单完成*/
		public static final String FINISH = "80";
		/**90 撤销订单、拒绝订单（供方）*/
		public static final String REJECT = "90";
	}
	
	/**
	 * 送货单状态
	 * @date	2017年6月14日 下午5:06:42
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class DeliveryFstate {
		/**40:待发货*/
		public static final String AWAIT_SEND = "40";
		/**50:待验收*/
		public static final String AWAIT_CHECK = "50";
		/**60:验收通过*/
		public static final String CHECK_PASSED = "60";
		/**80:完结*/
		public static final String FINISH = "80";
		/**90:验收不通过*/
		public static final String CHECK_NO_PASS = "90";
	}

	/**
	 * 入库类型
	 * tb_import表 INTYPE字段
	 */
	public static  final  class InstockType{
		/**01、申购*/
		public static final String APPLY_PURCHASE = "01";
		/**02、补货*/
		public static final String REPLENISHMENT  = "02";
		/**03、结算*/
		public static final String BALANCE = "03";
		/**04、盘盈*/
		public static final String INVENTORY_PROFIT= "04";
		/**05、初始化*/
		public static final String INITIALIZATION = "05";
		/**06、退货*/
		public static final String RETURN_OF_GOODS = "06";
	}

	/**
	 * 入库方式
	 * tb_import表 INMODE字段
	 */
	public static  final  class InstockMode{
		/**01、采购入库*/
		public static final String PURCHASING_WAREHOUSING = "01";
		/**04、盘盈*/
		public static final String INVENTORY_PROFIT= "04";
		/**05、初始化*/
		public static final String INITIALIZATION = "05";
		/**06、退货*/
		public static final String RETURN_OF_GOODS = "06";
	}

	/**
	 * 单据类别
	 * @date    2017年9月8日 下午
	 * @author  黄文君
	 * @version 1.0
	 * @since   JDK 1.6
	 */
	public static final class BillType {
		/**送货单*/
		public static final String DELIVERY = "DELIVERY";
		/**订单*/
		public static final String ORDER = "ORDER";
		/**汇总单*/
		public static final String GATHER = "GATHER";
		/**计划单*/
		public static final String PLAN = "PLAN";
		/**申请单*/
		public static final String APPLY = "APPLY";
	}

	/**
	 * 订单类别
	 * @date	2017年9月1日 下午5:06:42
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class OrderType {
		/**普耗*/
		public static final String ORDER = "ORDER";
		/**高值*/
		public static final String HIGH_ORDER = "HIGH_ORDER";
		/**结算*/
		public static final String SETTLE_ORDER = "SETTLE_ORDER";
		/**手术*/
		public static final String OPER_ORDER = "OPER_ORDER";
	}
	
	/**
     * 订单来源
     * @date    2017年9月4日 下午7:06:42
     * @author  冯辉
     * @version 1.0
     * @since   JDK 1.6
     */
	public static final class OrderFsource {
        /**库房计划*/
        public static final String PLAN = "01";
        /**自建订单*/
        public static final String SELF_ORDER = "02";
    }
	
	/**
	 * 送货单类别
	 * @date	2017年9月1日 下午5:06:42
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class DeliveryType {
		/**普耗*/
		public static final String DELIVERY = "DELIVERY";
		/**高值*/
		public static final String HIGH_DELIVERY = "HIGH_DELIVERY";
		/**结算*/
		public static final String SETTLE_DELIVERY = "SETTLE_DELIVERY";
		/**手术*/
		public static final String OPER_DELIVERY = "OPER_DELIVERY";
	}
	
	/**
	 * 计划单类别
	 * @date	2017年9月1日 下午5:06:42
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class PlanType {
		/**普耗*/
		public static final String PLAN = "PLAN";
		/**高值*/
		public static final String HIGH_PLAN = "HIGH_PLAN";
		/**结算*/
		public static final String SETTLE_PLAN = "SETTLE_PLAN";
		/**手术*/
		public static final String OPER_PLAN = "OPER_PLAN";
	}
	
	/**
	 * 汇总单类别
	 * @date	2017年9月1日 下午5:06:42
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class GatherType {
		/**普耗*/
		public static final String GATHER = "GATHER";
		/**高值*/
		public static final String HIGH_GATHER = "HIGH_GATHER";
		/**结算*/
		public static final String SETTLE_GATHER = "SETTLE_GATHER";
	}
	
	/**
	 * 申请单类别
	 * @date	2017年9月1日 下午5:06:42
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class ApplyType {
		/**普耗*/
		public static final String APPLY = "APPLY";
		/**高值*/
		public static final String HIGH_APPLY = "HIGH_APPLY";
		/**手术*/
		public static final String OPER_APPLY = "OPER_APPLY";
	}
	
	/**
	 * 单据分类缩写
	 * @date	2017年6月28日 下午2:34:31
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class BillPrefix {
		/**申请单 普耗*/
		public static final String PA = "PA";
		/**申请单 手术*/
		public static final String SA = "SA";
		/**申请单 高值*/
		public static final String GA = "GA";
		
		/**拣货单*/
		public static final String JN = "JN";
		
		/**计划 普耗*/
		public static final String PP = "PP";
		/**计划 高值*/
		public static final String GP = "GP";
		/**计划 手术*/
		public static final String SP = "SP";
		/**计划 结算*/
		public static final String JP = "JP";
		
		/**汇总单 普耗*/
		public static final String PG = "PG";
		/**汇总单 高值*/
		public static final String GG = "GG";
		/**汇总单 结算*/
		public static final String JG = "JG";
		
		/**订单 普耗*/
		public static final String PO = "PO";
		/**订单 高值*/
		public static final String GO = "GO";
		/**订单 结算*/
		public static final String JO = "JO";
		/**订单 手术*/
		public static final String SO = "SO";
		
		/**送货单 普耗*/
		public static final String PD = "PD";
		/**送货单 高值*/
		public static final String GD = "GD";
		/**送货单 结算*/
		public static final String JD = "JD";
		/**送货单 手术*/
		public static final String SD = "SD";

		/**入库单*/
		public static final String RK = "RK";
		/**出库单*/
		public static final String CK = "CK";
		/**库存盘点*/
		public static final String KP = "KP";
		
		/**使用清单*/
		public static final String UE = "UE";
		/**计费 手术*/
		public static final String OC = "OC";
		/**计费 高值*/
        public static final String GC = "GC";
	}
	
	/**
     * 发票状态
     * @date    2017年6月15日 下午16:35:42
     * @author  fenghui
     * @version 1.0
     * @since   JDK 1.6
     */
	public static final class InvoiceFstate {
	    /**0:发票待验收*/
        public static final String AWAIT_CHECK = "0";
        /**1:发票验收通过*/
        public static final String CHECK_PASSED = "1";
        /**9:发票验收不通过*/
        public static final String CHECK_NO_PASS = "9";
    }
	
	/**
	 * 文件导出Excel表对应字段名(送货单)
	 */
	public final static Map<String,String> DeliveryExcelMap = new HashMap<String, String>() {{  
        put("fstateName", "送货单状态");  
        put("sendDate", "制单时间"); 
        put("fOrgName", "供应方");  
        put("rOrgName", "需求方");
        put("sendNo", "送货单号");
        put("orderNo", "订单号"); 
        put("tfAddress", "收货地址"); 
        put("sendUsername", "制单人");
        put("checkUserName", "验收人");
        put("checkTime", "验收时间");
    }};
    
    /**
	 * 文件导出Excel送货单明细表对应字段名
	 */
	public final static Map<String,String> DeliveryDetailExcelMap = new HashMap<String, String>() {{  
        put("materialName", "产品名称");  
        put("geName", "通用名称"); 
        put("spec", "规格");  
        put("fmodel", "型号");
        put("tenderUnit", "单位");
        put("tfPacking", "包装规格"); 
        put("tenderPrice", "价格"); 
        put("amount", "发货数量");
        put("amountMoney", "金额");
        put("flot", "批号");
        put("prodDate", "生产日期");
        put("usefulDate", "有效期至");
    }};
    
    /**
     * 文件导出Excel发票表对应字段名
     */
    public final static Map<String,String> invoiceExcelMap = new HashMap<String, String>() {{  
        put("invoiceCode","发票代码");
        put("invoiceNo","发票号");
        put("accountPayed","发票金额");
        put("invoiceDate","开票日期");
        put("fOrgName","供应商");
        put("rOrgName","医疗机构");
        put("rStorageName","医疗机构库房");
        put("fstate","发票状态");
        put("createUserName","制单人");
        put("createTime","制单时间");
    }};
    
    /**
     * 文件导出Excel普耗/高值计划表对应字段名
     */
    public final static Map<String,String> planCjListExcelMap = new HashMap<String, String>() {{  
        put("fstate","状态");
        put("planNo","计划单号");
        put("fsource","计划单来源");
        put("deptName","科室");
        put("storageName","库房");
        put("shippingAddress","收货地址");
        put("planUsername","申请人");
        put("planTime","申请时间");
        put("applyNo","申请单号");
    }};
    
    /**
     * 文件导出手术计划表对应字段名
     */
    public final static Map<String,String> planBjListExcelMap = new HashMap<String, String>() {{  
        put("fstate","状态");
        put("planNo","计划单号");
        put("treatmentNo","就诊号");
        put("operName","手术名称");
        put("name","患者姓名");
        put("deptName","申请科室");
        put("shippingAddress","收货地址");
        put("storageName","备货库房");
        put("planUsername","申请人");
        put("planTime","申请时间");
        put("applyNo","申请单号");
    }};
    
    
    /**
     * 文件导出Excel普耗/高值计划表对应字段名
     */
    public final static Map<String,String> settlePlanListExcelMap = new HashMap<String, String>() {{  
        put("fstate","状态");
        put("planNo","计划单号");
        put("totalPrice", "结算总金额");
        put("storageName","申请库房");
        put("shippingAddress","收货地址");
        put("planUsername","申请人");
        put("planTime","申请时间");
        put("applyNo","申请单号");
    }};
    
    /**
     * 文件导出普耗/高值计划产品表对应字段名
     */
    public final static Map<String,String> planCjDetailListExcelMap = new HashMap<String, String>() {{  
        put("materialName","产品名称");
        put("geName","通用名称");
        put("spec","规格");
        put("fmodel","型号");
        put("tenderUnit","招标单位");
        put("tenderPrice","价格");
        put("tfPacking","包装规格");
        put("amount","需求数量");
        put("price","金额");
        put("tfBrandName","品牌");
        put("fOrgName","供应商");
    }};
    
    /**
     * 文件导出结算计划产品表对应字段名
     */
    public final static Map<String,String> settlePlanDetailListExcelMap = new HashMap<String, String>() {{  
        put("materialName","产品名称");
        put("geName","通用名称");
        put("spec","规格");
        put("fmodel","型号");
        put("tenderUnit","招标单位");
        put("tenderPrice","价格");
        put("tfPacking","包装规格");
        put("amount","结算数量");
        put("price","金额");
        put("tfBrandName","品牌");
        put("fOrgName","供应商");
    }};
    
    /**
     * 文件导出手术计划品牌表对应字段名
     */
    public final static Map<String,String> planBjDetailListExcelMap = new HashMap<String, String>() {{  
        put("tfBrandName","品牌");
        put("fOrgName","供应商");
        put("lxr","联系人");
        put("lxdh","联系电话");
    }};
    
    /**
     * 文件导出手术计费产品明细表对应字段名
     */
    public final static Map<String,String> chargeShDetailListExcelMap = new HashMap<String, String>() {{  
        put("materialName","产品名称");
        put("geName","通用名称");
        put("spec","规格");
        put("fmodel","型号");
        put("tenderUnit","招标单位");
        put("tfPacking","包装规格");
        put("sl","使用数量");
        put("tenderPrice","价格");
        put("hisPrice","HIS计费价");
        put("flot","生产批号");
        put("prodDate","生产日期");
        put("usefulDate","有效期至");
    }};

    public final static Map<String,String> chargeInfoListExcelMap = new HashMap<String, String>() {{
        put("chargeFstateName","状态");
        put("chargeNo","计费清单号");
        put("chargeTypeName","清单类型");
        put("treatmentNo","就诊号");
        put("operName","手术名称");
        put("name","患者姓名");
        put("deptName","计费科室");
        put("chargeUserName","计费人");
        put("chargeTime","计费时间");
        put("sendNo","送货单号");
    }};
    
    /**
     * 文件导出高值计费产品明细表对应字段名
     */
    public final static Map<String,String> chargeGzDetailListExcelMap = new HashMap<String, String>() {{
        put("chargeFstateName","状态");
        put("qrcode","二维码");
        put("materialName","产品名称");
        put("geName","通用名称");
        put("spec","规格");
        put("fmodel","型号");
        put("tenderUnit","招标单位");
        put("tfPacking","包装规格");
        put("sl","使用数量");
        put("tenderPrice","价格");
        put("hisPrice","HIS计费价");
        put("flot","生产批号");
        put("prodDate","生产日期");
        put("usefulDate","有效期至");
    }};
    
    /**
     * 文件导出使用登记列表对应字段名
     */
    public final static Map<String,String> usedChargeInfoListExcelMap = new HashMap<String, String>() {{
        put("chargeFstateName","状态");
        put("chargeNo","使用清单号");
        put("treatmentNo","就诊号");
        put("operName","手术名称");
        put("hzName","患者姓名");
        put("sqDeptName","申请科室");
        put("fOrgName","供应商");
        put("deptName","登记科室");
        put("reateUserName","登记人");
        put("createTime","登记时间");
        put("sendNo","送货单号");
    }};
    
    /**
     * 文件导出使用登记产品明细表对应字段名
     */
    public final static Map<String,String> usedChargeDetailListExcelMap = new HashMap<String, String>() {{  
        put("materialName","产品名称");
        put("geName","通用名称");
        put("spec","规格");
        put("fModel","型号");
        put("tenderUnit","招标单位");
        put("tfPacking","包装规格");
        put("amount","发货数量");
        put("sysl","使用数量");
        put("tenderPrice","价格");
        put("flot","生产批号");
        put("prodDate","生产日期");
        put("usefulDate","有效期至");
    }};

	/**
	 * 状态变更类型标识
	 * @date	2017年6月15日 下午2:05:40
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class ChangeFstateType {
		/**订单*/
		public static final String ORDER = "订单";
		/**送货单*/
		public static final String DELIVERY = "送货单";
		/**发票*/
		public static final String INVOICE = "发票";
	}
	
	/**
	 * 是否同步标识
	 * @date	2017年8月18日
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class SyncFlag {
		/**需要同步*/
		public static final String NEED_SYNC = "01";
		/**不同步*/
		public static final String NO = "00";
	}

	
	/**
	 * 
	 * 2017年7月27日 上午11:39:51
	 * @author 陶悠
	 *
	 */
	public static final class ApplyFstate {
		/**00-草稿*/
		public static final String DRAFT = "00";
		/**10-提交，有审核时候，提交之后也就是待审核*/
		public static final String NEWAPPLY = "10";
		/**20-待确认，有审核时候，审核之后待确认；无审核时候，提交之后待确认*/
		public static final String AWAIT_AGGREE = "20";
		/**34-驳回 */
		public static final String REJECT = "34";
		/**40-采购中 */
		public static final String CAIGOU = "40";
		/**60-完结 */
		public static final String FINISH = "60";
		/**92-删除 */
		public static final String DELETE = "92";
	}

	/**
	 * 出库方式
	 * */
	public static final class OutMode {
		/** 拣货单出库 */
		public static final String PICK = "01";
		/** 科室领用出库 */
		public static final String CONSUM = "02";
		/** 申购出库 */
		public static final String APPLY = "03";
		/** 盘亏出库 */
		public static final String PDLOSE = "04";
		/** 退库出库 */
		public static final String BACK = "05";
		/** 结算出库 */
		public static final String SETTLE = "06";
	}
	/**
	 * 不同配置参数的类型
	 * 2017年8月14日 下午2:57:48
	 * @author 陶悠
	 */
	public static final class ConfigFlag {
		/**00、科室*/
		public static final String DEPT = "00";
		/**01、库房*/
		public static final String STORAGE = "01";
		/**03、机构*/
		public static final String ORG = "03";
	}
	/**
     * 招标发布状态码（只适用: 00未发布、01发布）
     */
    public static final class releaseFlag {
        /** 已发布 */
        public static final String publish = "01";
        /** 未发布 */
        public static final String unpublish = "00";
    }
	
    /**
     * 审批配置: 库房或科室标识
     * @date	2017年8月22日 下午2:49:31
     * @author	黄文君
     * @version	1.0
     * @since	JDK 1.6
     */
    public static final class ApprovalFlag {
		/**01、库房*/
		public static final String STORAGE = "01";
		/**02、科室*/
		public static final String DEPT = "02";
    }
    
    /**
     * 审批配置: 通过条件
     * @date	2017年8月22日 下午2:49:31
     * @author	黄文君
     * @version	1.0
     * @since	JDK 1.6
     */
    public static final class ApprovalCondition {
		/**01、单人*/
		public static final String ANY = "01";
		/**02、多人*/
		public static final String ALL = "02";
    }
    
    /**
     * 审批操作: 审核状态
     * @date	2017年8月22日 下午3:08:29
     * @author	黄文君
     * @version	1.0
     * @since	JDK 1.6
     */
    public static final class ApprovalFstate {
		/**00、待审批*/
		public static final String AWAIT_APPROVAL = "00";
		/**01、已审批*/
		public static final String PASSED= "01";
		/**02、未通过*/
		public static final String NO_PASS = "02";
    }
    
    /**
     * 计划单数据来源
     */
    public static final class PlanFsource {
        /**01:科室申请*/
        public static final String DEPTAPPLY = "01";
        /**02:库房申请*/
        public static final String STORAGEAPPLY = "02";
    }

	/**
	 * 入库供应商
 	 */
	public static final class WarehousingSupplier{
		//初始化供应商
		public static  final  String CSH_ORG_CODE= "CSHRK";
		//盘盈入库供应商
		public static  final  String PY_ORG_CODE= "PYRK";

	}
    /**
     * 计划单状态
     */
    public static final class PlanFstate {
        /**00-草稿*/
        public static final String DRAFT = "00";
        /**20-待确认*/
        public static final String AWAIT_AGGREE = "20";
        /**30-已确认*/
        public static final String ALREADY_AGGREE = "30";
        /**34-已驳回 */
        public static final String REJECT = "34";
        /**36-已汇总 */
        public static final String SUMMATION = "36";
        /**40-采购中 */
        public static final String PURCHASE = "40";
        /**60-完结 */
        public static final String FINISH = "60";
    }
    
    /**
     * 拣货单状态
     */
    public static final class ApplyPickFstate {
    	/**10-新建*/
    	public static final String NEW_BUILT = "10";
    	/**80-完结*/
    	public static final String FINISH = "80";
    	/**81-手动完结*/
    	public static final String MANUAL_FINISH = "81";
    }
    
    /**
     * 汇总单状态
     */
    public static final class GatherFstate {
    	/**10-新建*/
    	public static final String NEW_BUILT = "10";
    	/**60-完结*/
    	public static final String FINISH = "60";
		/**62、审核通过*/
		public static final String PASSED = "62";
    	/**64-不通过*/
    	public static final String NO_PASS = "64";
    }

	/**
	 * 数据保存标识（00-临时数据，01-已提交，02-提交后更新，03-提交后临时删除）
	 * @date	2017年9月21日
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public static final class  SaveFlag {
		/**草稿，临时数据*/
		public static final String DRAFT = "00";
		/**已提交*/
		public static final String SUBMIT = "01";
		/**提交后临时更新*/
		public static final String SUBMIT_PRE_UPDATE = "02";
		/**提交后临时删除*/
		public static final String SUBMIT_PRE_DELETE = "03";
	}

    /**
     * 申请单为采购还是派送
     */
    public static final class SendModeType {
    	/**00-采购*/
    	public static final String PURCHASE = "00";
    	/**61-派送*/
    	public static final String PLCK = "01";
    }

	/**
	 * 二维码状态
	 * @author 黄文君
	 * @date 2017年10月20日
	 */
	public static final class QrcodeFstate {
		/**新建*/
		public static final String created = "00";
		/**已入库*/
		public static final String imported = "20";
		/**退货*/
		public static final String sendback_to_supplier = "30";
		/**已出库*/
		public static final String outported = "40";
		/**退库*/
		public static final String sendback_to_storage = "50";
		/**已登记*/
		public static final String registed = "60";
		/**已计费*/
		public static final String charged = "70";
		/**已退费*/
		public static final String refunded = "80";
	}
	/**
	 * @date:2017/11/13 0013 上午 11:56
	 * @description:计费状态   TB_CHARGE_INFO表 CHARGE_FSTATE字段
	 * @author:heyi
	 * @return
	 */
	public static final class chargeFstate {
		/**待计费*/
		public static final String created = "00";
		/**已计费*/
		public static final String charged = "01";
		/**已退费*/
		public static final String refunded = "02";
		/**计费失败*/
		public static final String chargedFail = "08";
		/**退费失败*/
		public static final String refundedFail = "09";
	}
	
	/**
     * 计费类型
     * @date    2017年11月14日 下午4:38:31
     * @author  黄文君
     * @version 1.0
     * @since   JDK 1.6
     */
    public static final class ChargeType {
        /**01、高值*/
        public static final String HIGH_ORDER = "01";
        /**00、手术*/
        public static final String OPER_ORDER = "00";
    }

    /**
     * 维修标志
     * 2017年12月17日 下午4:48:25
     * @author 陶悠
     *
     */
    public static final class RepairFlag {
		/**00、正常*/
		public static final String USEFUL = "00";
		/**01、维修中*/
		public static final String REPAIRING= "01";
	}
    
    /**
     * 维修标志
     */
    public final static Map<String,String> RepairFlagMap = new HashMap<String, String>() {{
        put("00", "正常"); 
        put("01", "维修中");
    }};

    /**
     * 折旧方法
     */
    public final static Map<String,String> DepreciationTypeMap = new HashMap<String, String>() {{
        put("01", "平均年限法"); 
        put("02", "工作量法");
        put("03", "双倍余额递减法");
        put("04", "年数总和法");
    }};
    
    /**
     * 资产分类
     */
    public final static Map<String,String> ProductTypeMap = new HashMap<String, String>() {{
        put("01", "通用设备"); 
        put("02", "电气设备");
        put("03", "电子产品及通信设备");
        put("04", "仪器仪表及其他");
        put("05", "专业设备");
        put("06", "其他");
    }};
}
