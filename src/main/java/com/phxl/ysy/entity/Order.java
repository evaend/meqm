package com.phxl.ysy.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phxl.core.base.adapter.CustomDateSerializer;
import com.phxl.core.base.adapter.DecimalOf2Serializer;
import com.phxl.core.base.annotation.BaseSql;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@BaseSql(tableName="TB_ORDER", resultName="com.phxl.ysy.dao.OrderMapper.BaseResultMap")
public class Order {
    private String orderId;

    private String orderNo;

    private Long rOrgId;

    private String storageGuid;
    
    private String bStorageGuid;

    private Long fOrgId;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date orderDate;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date sendDate;

    private String orderUserid;

    private String fstate;

    private String lxr;

    private String lxdh;

    private String deptGuid;

    private String deptName;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date expectDate;

    private String sendUserid;

    private String orderType;
    
    private String orderTypeName;

    private String addrGuid;

    private String tfAddress;
    
    private String applyId;
    
    private String planId;
    
    private String fsource;

    @JsonSerialize(using = DecimalOf2Serializer.class)
    private BigDecimal totalPrice;

    private String orderUsername;
    
    private String rOrgName;
    private String rStorageName;
    private String rOrgAlias;
    private String fOrgName;
    private String fOrgAlias;
    private Boolean allowCancleOrder;//是否允许取消订单
    private String fstateName;
    private String tfBrand;

    private Date cancleTime;//取消时间
    private String cancleUserName;//取消人姓名
	private String cancleReason;//取消理由
    
    private Boolean allowSettleGoods;//是否能够备货
    
    private BigDecimal deliveryCount;//送货单个数
    private BigDecimal sendoutDeliveryCount;//已发货送货货单个数
    
    private List<OrderDetail> detailList;
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getrOrgId() {
        return rOrgId;
    }

    public void setrOrgId(Long rOrgId) {
        this.rOrgId = rOrgId;
    }

    public String getStorageGuid() {
        return storageGuid;
    }

    public void setStorageGuid(String storageGuid) {
        this.storageGuid = storageGuid;
    }

    public Long getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(Long fOrgId) {
        this.fOrgId = fOrgId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getOrderUserid() {
        return orderUserid;
    }

    public void setOrderUserid(String orderUserid) {
        this.orderUserid = orderUserid;
    }

    public String getFstate() {
		return fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

	public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public String getSendUserid() {
        return sendUserid;
    }

    public void setSendUserid(String sendUserid) {
        this.sendUserid = sendUserid;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getAddrGuid() {
        return addrGuid;
    }

    public void setAddrGuid(String addrGuid) {
        this.addrGuid = addrGuid;
    }

    public String getTfAddress() {
        return tfAddress;
    }

    public void setTfAddress(String tfAddress) {
        this.tfAddress = tfAddress;
    }

    public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderUsername() {
        return orderUsername;
    }

    public void setOrderUsername(String orderUsername) {
        this.orderUsername = orderUsername;
    }

	public String getrOrgName() {
		return rOrgName;
	}

	public void setrOrgName(String rOrgName) {
		this.rOrgName = rOrgName;
	}

	public String getrOrgAlias() {
		return rOrgAlias;
	}

	public void setrOrgAlias(String rOrgAlias) {
		this.rOrgAlias = rOrgAlias;
	}

	public String getfOrgName() {
		return fOrgName;
	}

	public void setfOrgName(String fOrgName) {
		this.fOrgName = fOrgName;
	}

	public String getfOrgAlias() {
		return fOrgAlias;
	}

	public void setfOrgAlias(String fOrgAlias) {
		this.fOrgAlias = fOrgAlias;
	}

	public Boolean getAllowCancleOrder() {
		return allowCancleOrder;
	}

	public void setAllowCancleOrder(Boolean allowCancleOrder) {
		this.allowCancleOrder = allowCancleOrder;
	}

	public String getrStorageName() {
		return rStorageName;
	}

	public void setrStorageName(String rStorageName) {
		this.rStorageName = rStorageName;
	}

	public String getTfBrand() {
		return tfBrand;
	}

	public void setTfBrand(String tfBrand) {
		this.tfBrand = tfBrand;
	}

	public String getFstateName() {
		return fstateName;
	}

	public void setFstateName(String fstateName) {
		this.fstateName = fstateName;
	}

	public BigDecimal getDeliveryCount() {
		return deliveryCount;
	}

	public Date getCancleTime() {
		return cancleTime;
	}

	public void setCancleTime(Date cancleTime) {
		this.cancleTime = cancleTime;
	}

	public String getCancleReason() {
		return cancleReason;
	}

	public void setCancleReason(String cancleReason) {
		this.cancleReason = cancleReason;
	}

	public String getCancleUserName() {
		return cancleUserName;
	}

	public void setCancleUserName(String cancleUserName) {
		this.cancleUserName = cancleUserName;
	}

	public Boolean getAllowSettleGoods() {
		return allowSettleGoods;
	}

	public void setAllowSettleGoods(Boolean allowSettleGoods) {
		this.allowSettleGoods = allowSettleGoods;
	}

	public void setDeliveryCount(BigDecimal deliveryCount) {
		this.deliveryCount = deliveryCount;
	}

	public BigDecimal getSendoutDeliveryCount() {
		return sendoutDeliveryCount;
	}

	public void setSendoutDeliveryCount(BigDecimal sendoutDeliveryCount) {
		this.sendoutDeliveryCount = sendoutDeliveryCount;
	}

	public List<OrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetail> detailList) {
		this.detailList = detailList;
	}

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getFsource() {
        return fsource;
    }

    public void setFsource(String fsource) {
        this.fsource = fsource;
    }

	public String getbStorageGuid() {
		return bStorageGuid;
	}

	public void setbStorageGuid(String bStorageGuid) {
		this.bStorageGuid = bStorageGuid;
	}

}