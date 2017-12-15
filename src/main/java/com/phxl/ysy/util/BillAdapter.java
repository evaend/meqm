/**
* 普华信联公司源代码，版权归普华信联公司所有。
*
* 项目名称	：	commonModule
* 创建日期	：	2017年9月1日
* 所在包		：	com.phxl.ysy.util
* 文件名称	：	BillPrefixAdapter.java
* 修改历史	：
* 1.[2017年9月1日]创建文件 by 黄文君
*/

package com.phxl.ysy.util;

import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.LocalAssert;
import com.phxl.core.base.util.LocalStringUtils;
import com.phxl.ysy.constant.CustomConst.ApplyType;
import com.phxl.ysy.constant.CustomConst.BillPrefix;
import com.phxl.ysy.constant.CustomConst.DeliveryType;
import com.phxl.ysy.constant.CustomConst.GatherType;
import com.phxl.ysy.constant.CustomConst.OrderType;
import com.phxl.ysy.constant.CustomConst.PlanType;

/**
 * 单据类型相关适配器
 * @Date	2017年9月1日 下午5:04:44
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class BillAdapter {
	
	/**
	 * 单据号前缀适配
	 * @author	黄文君
	 * @date	2017年9月2日 上午10:21:01
	 * @param	type	单据类型
	 * @throws	ValidationException
	 * @return	String
	 */
	public static String prefixAdapter(String type) throws ValidationException {
		LocalAssert.notBlank(type, "单据类型，不能为空");
		return LocalStringUtils.caseWhenThrowable(type, 
				//订单类型
				OrderType.ORDER, BillPrefix.PO, 
				OrderType.HIGH_ORDER, BillPrefix.GO, 
				OrderType.SETTLE_ORDER, BillPrefix.JO,
				OrderType.OPER_ORDER, BillPrefix.SO,
				//送货单类型
				DeliveryType.DELIVERY, BillPrefix.PD, 
				DeliveryType.HIGH_DELIVERY, BillPrefix.GD, 
				DeliveryType.SETTLE_DELIVERY, BillPrefix.JD,
				DeliveryType.OPER_DELIVERY, BillPrefix.SD,
				//计划单类型
				PlanType.PLAN, BillPrefix.PP, 
				PlanType.HIGH_PLAN, BillPrefix.GP, 
				PlanType.SETTLE_PLAN, BillPrefix.JP,
				PlanType.OPER_PLAN, BillPrefix.SP,
				//汇总单类型
				GatherType.GATHER, BillPrefix.PG, 
				GatherType.HIGH_GATHER, BillPrefix.GG, 
				GatherType.SETTLE_GATHER, BillPrefix.JG,
				//申请单类型
				ApplyType.APPLY, BillPrefix.PA, 
				ApplyType.HIGH_APPLY, BillPrefix.GA, 
				ApplyType.OPER_APPLY, BillPrefix.SA
		);
	}
	
}
