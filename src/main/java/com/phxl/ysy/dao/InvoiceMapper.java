package com.phxl.ysy.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.phxl.core.base.entity.Pager;

public interface InvoiceMapper {

    /**
     * 
     * findMyInvoiceList:(查询我的发票列表). <br/> 
     * 
     * @Title: findMyInvoiceList
     * @Description: TODO
     * @param pager
     * @return    设定参数
     * @return List<Map>    返回类型
     * @throws
     */
    List<Map<String, Object>> findMyInvoiceList(Pager pager);

    /**
     * 
     * findStorageListByOrgId:(根据医疗机构查询医疗机构库房列表). <br/> 
     * 
     * @Title: findStorageListByOrgId
     * @Description: TODO
     * @param pager1
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> findStorageListByOrgId(Pager pager);

    /**
     * 
     * searchDeliveryListByInvoiceId:(根据发票查询关联的送货单列表). <br/> 
     * 
     * @Title: searchDeliveryListByInvoiceId
     * @Description: TODO
     * @param pager1
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> searchDeliveryListByInvoiceId(Pager pager);
    
    /**
     * 
     * findTreatmentListFilterDrug:(根据发票GUID查询关联的送货单总金额). <br/> 
     * 
     * @Title: findTreatmentListFilterDrug
     * @Description: TODO
     * @param pager1
     * @return    设定参数
     * @return String    返回类型
     * @throws
     */
    String getTotalPriceByInvoiceId(Pager pager);

    /**
     * 
     * searchDeliveryList:(根据医疗机构和库房查询已关联和未关联的送货单列表). <br/> 
     * 
     * @Title: searchDeliveryList
     * @Description: TODO
     * @param pager1
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> searchDeliveryList(Pager pager);

    /**
     * 
     * getInvoiceById:(根据发票号查询发票信息). <br/> 
     * 
     * @Title: getInvoiceById
     * @Description: TODO
     * @param pager1
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> getInvoiceById(Pager pager1);
    
    /**
     * 
     * getDeliveryDetail:(根据发票GUID查询送货单明细). <br/> 
     * 
     * @Title: getDeliveryDetail
     * @Description: TODO
     * @param pager1
     * @return    设定参数
     * @return List<Map<String,Object>>    返回类型
     * @throws
     */
    List<Map<String, Object>> getDeliveryDetail(Pager pager1);
    
    /**
     * 
     * findInvoiceExist:(检查供方机构下，是否已存在该发票). <br/> 
     * 
     * @Title: findFbarCodeExist
     * @Description: TODO
     * @param invoiceNo
     * @param invoiceCode
     * @param fOrgId
     * @return    设定参数
     * @return int    返回类型
     * @throws
     */
    int findInvoiceExist(@Param(value = "invoiceId")String invoiceId,@Param(value = "invoiceNo")String invoiceNo, @Param(value = "invoiceCode")String invoiceCode, @Param(value = "fOrgId")Long fOrgId);

    /**
     * 查询送货单是否已入库（有无对应送货单的入库单）
     * @param sendId 送货单号
     * @return 
     */
    int selectImportByInvoice(String sendId);
    
    /**
     * 查询财务结账月结账列表
     * @param pager
     * @return
     */
    List<Map<String, Object>> selectInvoiceByMonth(Pager pager);
    
    /**
     * 查询财务结账月的金额
     * @param acctYh
     * @return
     */
    String selectSumMoneyByMonth(String	acctYh);
    
    /**
     * 已结账详情
     * @param pager
     * @return
     */
    List<Map<String, Object>> selectInvoiceDetailByMonth(Pager pager);
    
    /**
     * 已结账总金额
     * @param pager
     * @return
     */
    BigDecimal selectInvoiceByMonthCountMoney(Pager pager);
    
    /**
     * 未结账详情
     * @param pager
     * @return
     */
    List<Map<String, Object>> selectInvoiceDetailNotAcct(Pager pager);
    
    /**
     * 未结账总金额
     * @param pager
     * @return
     */
    BigDecimal selectInvoiceNotAcctCountMoney(Pager pager);
    
    /**
     * 查询结账月份
     * @param pager
     * @return
     */
    List<Map<String, Object>> selectInvoiceMonth(Pager pager);
    
    /**
     * 查询目前最大结账月份
     * @return
     */
    String selectInvoiceMaxAcctYh(String storageGuid);
    
    /**
     * 查询该库房最早的开票时间
     * @param storageGuid 库房ID
     * @return
     */
    String selectInvoiceMinDate(String storageGuid);
    
    /**
     * 确认结账
     * @param pager
     */
    void updateInvoiceSettleAccount(Map<String, Object> map);
}