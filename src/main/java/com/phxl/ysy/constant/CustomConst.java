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
 * 常量定义类: 用于定义系统中用到的常量值或者码值Hello1
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

    /**
     * 经费来源
     */
    public final static Map<String,String> sourceFundsMap = new HashMap<String, String>() {{
        put("01", "通用设备"); 
        put("02", "自建");
        put("03", "融资租入");
        put("04", "接受捐赠");
        put("05", "盘盈");
        put("06", "其他");
    }};

    /**
     * Excel经费来源
     */
    public final static Map<String,String> sourceFundsExcelMap = new HashMap<String, String>() {{
        put("通用设备","01"); 
        put("自建","02");
        put("融资租入","03");
        put("接受捐赠","04");
        put("盘盈","05");
        put("其他","06");
    }};
    
    /**
     * 资产信息字段修改
     * @author zhangyanli
     *
     */
    public static final class AssetsRecordInfoUpdate {
    	/**EQUIPMENT_STANDARD_NAME、 通用名称*/
        public static final String equipmentName = "EQUIPMENT_NAME";
        /**DEPOSIT、 存放地址*/
        public static final String deposit = "DEPOSIT";
        /**RRPAIR_TYPE、维修分类 */
        public static final String rrpairType = "RRPAIR_TYPE";
        /**SPARE、有无备用 */
        public static final String spare = "SPARE";
        /**MAINTAIN_DAY、保养周期 */
        public static final String maintainDay = "MAINTAIN_DAY";
        /**MAINTAIN_TYPE、保养分类 */
        public static final String maintainType = "MAINTAIN_TYPE";
        /**METERING_TYPE、计量分类 */
        public static final String meteringType = "METERING_TYPE";
    }

    /**
     * 维修状态
     * @author zhangyanli
     */
    public static final class RrpairOrderFstate {
		/**10、待维修（申请）*/
		public static final String AWAITING_REPAIR = "10";
		/**20、已指派（指派）*/
		public static final String ASSIGNED = "20";
		/**30、维修中（维修）*/
		public static final String MAINTENANCE = "30";
		/**50、待验收（验收）*/
		public static final String AWAIT_CHECK = "50";
		/**80、已拒绝（拒绝）*/
		public static final String REJECT = "80";
		/**90 、已关闭（关闭）*/
		public static final String FINISH = "90";
	}
    
    /**
     * 维修状态分类
     */
    public final static Map<String,String> RrpairFstateMap = new HashMap<String, String>() {{
        put("10", "待接修"); 
        put("20", "已指派");
        put("30", "维修中");
        put("50", "待验收");
        put("80", "已拒绝");
        put("90", "已关闭");
    }};
    
    /**
     * 维修结果
     * @author zhangyanli
     */
    public static final class RrpairResult {
		/**00、完全修复*/
		public static final String ENTIRELY_REPAIR = "00";
		/**01、部分修复*/
		public static final String PORTION_REPAIR = "01";
		/**02、未修复*/
		public static final String NO_REPAIR = "02";
	}

    /**
     * 紧急度
     * @author zhangyanli
     */
    public static final class UrgentFlag {
    	/**10、紧急*/
		public static final String URGENCY = "10";
		/**20、不急*/
		public static final String NO_HURRY = "20";
		/**30、一般*/
		public static final String ORDINARY = "30";
	}

    /**
     * 保修
     * @author zhangyanli
     */
    public static final class GuaranteeFlag {
		/**00、出保*/
		public static final String EXPIRING_INSURANCE_POLICY = "00";
		/**01、在保*/
		public static final String UNDER_WARRANTY = "01";
	}

    /**
     * 维修方式
     * @author zhangyanli
     */
    public static final class RrpairType {
		/**00、内修*/
		public static final String IN_REPAIR = "00";
		/**01、外修*/
		public static final String OUT_REPAIR = "01";
	}

    /**
     * 备用机
     * @author zhangyanli
     */
    public static final class Spare {
		/**00、无*/
		public static final String NO = "00";
		/**01、有*/
		public static final String YES = "01";
	}

    /**
     * 维修内容-故障类型
     * @author zhangyanli
     */
    public static final class RepairContentType {
		/**00、暂时性故障*/
		public static final String TEMPORARY_FAILURE = "00";
		/**01、永久性故障*/
		public static final String PERPETUAL_FAILURE = "01";
		/**02、突发性故障*/
		public static final String OUTBURST_FAILURE = "02";
		/**03、渐发性故障*/
		public static final String GRADUALLY_FAILURE = "03";
		/**04、破坏性故障*/
		public static final String DESTROY_FAILURE = "04";
		/**05、非破坏性故障*/
		public static final String NON_DESTROY_FAILURE = "05";
		/**99、其他*/
		public static final String OTHER = "99";
	}

    /**
     * 维修内容-故障原因
     * @author zhangyanli
     */
    public static final class RepairContentTyp {
		/**00、设备老化*/
		public static final String BURN_IN = "00";
		/**01、使用不当*/
		public static final String MISUSE = "01";
		/**02、缺乏维护*/
		public static final String OUT_OF_REPAIR = "02";
		/**99、其他*/
		public static final String OTHER = "99";
	}

    /**
     * 拒绝原因
     * @author zhangyanli
     */
    public static final class RefuseCause {
		/**00、误报*/
		public static final String MISINFORMATION = "00";
		/**01、无法维修*/
		public static final String unable_to_repair = "01";
		/**02、缺乏维护*/
		public static final String HIGH_MAINTENANCE_COST = "02";
		/**99、其他*/
		public static final String OTHER = "99";
	}

    /**
     * 后续处理
     * @author zhangyanli
     */
    public static final class FollowupTreatment {
		/**00、正常使用*/
		public static final String NORMAL_USE = "00";
		/**01、报废*/
		public static final String SCRAP = "01";
		/**99、其他*/
		public static final String OTHER = "99";
	}

    /**
     * 关闭原因
     * @author zhangyanli
     */
    public static final class OffCause {
    	/**00、误报*/
		public static final String MISINFORMATION = "00";
		/**01、无法维修*/
		public static final String unable_to_repair = "01";
		/**02、缺乏维护*/
		public static final String HIGH_MAINTENANCE_COST = "02";
		/**99、其他*/
		public static final String OTHER = "99";
	}

    /**
     * 经费来源
     * @author zhangyanli
     */
    public static final class SourceFunds {
		/**01、购入*/
		public static final String BUY = "01";
		/**02、自建*/
		public static final String SELF_BUILT = "02";
		/**03、融资租入*/
		public static final String FOR = "03";
		/**04、接受捐赠*/
		public static final String ACCEPT_DONATIONS = "04";
		/**05、盘盈*/
		public static final String INVENTORY_SURPLUS = "05";
		/**06、其他*/
		public static final String OTHER = "06";
	}
    
    /**
     * 折旧方法
     */
    public final static class DepreciationType {
		/**01、平均年限法*/
		public static final String AVERAGE_LIFE_OF = "01";
		/**02、工作量法*/
		public static final String WORKLOAD = "02";
		/**03、双倍余额递减法*/
		public static final String DOUBLE_DECREMENT = "03";
		/**04、年数总和法*/
		public static final String SUM_OF = "04";
    }

    /**
     * 使用状态
     */
    public final static class UseFstate {
		/**01、正常在用（启用后或科室验收通过）*/
		public static final String IN_NORMAL_USE = "01";
		/**02、故障中（报修）*/
		public static final String IN_THE_FAULT = "02";
		/**03、已报废*/
		public static final String HAVE_BEEN_SCRAPPED = "03";
		/**04、借出*/
		public static final String LEND = "04";
		/**05、闲置*/
		public static final String IDLE = "05";
    }

    /**
     * 维修验收
     */
    public final static class RrAcce {
    	/**00、无须验收*/
		public static final String NO_NEED_TO_ACCEPTANCE = "00";
		/**01、一次验收*/
		public static final String AN_ACCEPTANCE = "01";
		/**02、二次验收*/
		public static final String SECOND_ACCEPTANCE = "02";
    }

    /**
     * 一次验收 
     */
    public final static class RrAcceA {
    	/**00、使用科室验收*/
		public static final String USE_ACCEPTANCE = "00";
		/**01、管理科室验收*/
		public static final String MANAGEMENT_ACCEPTANCE = "01";
    }

    /**
     * 二次验收 
     */
    public final static class RrAcceB {
    	/**00、使用科室验收*/
		public static final String USE_ACCEPTANCE = "00";
		/**01、管理科室验收*/
		public static final String MANAGEMENT_ACCEPTANCE = "01";
    }

    /**
     * 非规范资产标识
     */
    public final static class StandardAssetsFlag {
    	/**00、非标*/
		public static final String NON_STANDARD = "00";
		/**01、标准*/
		public static final String STANDARD = "01";
    }
    
    /**
     * 报修后验收人验收
     */
    public final static class RrAcceFstate {
    	/** 50、待验收 */
		public static final String FOR_ACCEPTANCE = "00";
		/** 60、管理科室已验收*/
		public static final String MANAGEMENT_PASS = "01";
    	/** 61、管理科室验收不通过 */
		public static final String MANAGEMENT_NO_PASS = "00";
		/** 65、使用科室已验收*/
		public static final String USE_PASS = "65";
    	/** 66、使用科室验收不通过*/
		public static final String USE_NO_PASS = "66";
		/** 90、关闭*/
		public static final String CLOSE = "90";
    }
    
    /**
     * 设备前端项目域名+端口
     */
    public final static String MeqmMobile = "http://hsms.com.cn/meqm/index.jsp";
    
    /**
     * 保养状态
     */
    public final static class MaintainFstate{
    	/** 00、待完成*/
		public static final String NO_FINISH = "00";
    	/** 01、已完成*/
		public static final String FINISH = "01";
    	/** 02、关闭*/
		public static final String CLOSE = "02";
    }
    
    /**
     * 设备是否正常（保养）
     */
    public final static class EquipmentFstate{
		/**01、正常*/
		public static final String USEFUL = "01";
		/**00、不正常*/
		public static final String NO_USEFUL= "00";
    }
    
    public final static class MaintainPlanFstate{
		/**00、草稿*/
		public static final String DRAFT = "00";
		/**20、待执行*/
		public static final String EXECUTORY = "20";
		/**30、执行中*/
		public static final String IN_EXECUTORY = "30";
		/**40、已执行*/
		public static final String EXECUTED = "40";
		/**80、已关闭*/
		public static final String COLSE = "80";
    }
}
