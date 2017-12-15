package com.phxl.ysy.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.phxl.core.base.annotation.BaseSql;
import com.phxl.core.base.exception.ValidationException;
import com.phxl.core.base.util.LocalStringUtils;
import com.phxl.ysy.constant.CustomConst.DeliveryType;
import com.phxl.ysy.constant.CustomConst.OrderType;

@BaseSql(tableName="TB_DELIVERY", resultName="com.phxl.ysy.dao.DeliveryMapper.BaseResultMap")
public class Delivery {
    private String sendId;

    private String sendNo;

    private Long rOrgId;

    private String rStorageGuid;

    private String bStorageGuid;

    private Long fOrgId;

    private String fStorageGuid;

    private String orderId;

    private String deptGuid;

    private Date sendDate;

    private String fstate;

    private String lxr;

    private String lxdh;

    private String boxUserid;

    private String sendUserid;

    private String orderType;

    private String rejectReason;

    private BigDecimal totalPrice;

    private String addrGuid;

    private String tfAddress;

    private String deptName;

    private String sendUsername;
    
    private String fOrgName;
    private String rOrgName;
    private String invoiceNo;
    private BigDecimal accountPayed;
    private String chargeGuid;
    private String useNo;

    public Delivery() {
		super();
	}

	/**
	 * 订单主信息封装成送货货主信息
	 * @param	order
	 * @throws	ValidationException 
	 */
	public Delivery(Order order) throws ValidationException {
		setrOrgId(order.getrOrgId());
		setrStorageGuid(order.getStorageGuid());
		setbStorageGuid(order.getbStorageGuid());
		setfOrgId(order.getfOrgId());
		setfStorageGuid(null);
		setOrderId(order.getOrderId());
		
		String deliveryType = LocalStringUtils.caseWhenThrowable(order.getOrderType(),
				OrderType.ORDER, DeliveryType.DELIVERY,
				OrderType.HIGH_ORDER, DeliveryType.HIGH_DELIVERY,
				OrderType.SETTLE_ORDER, DeliveryType.SETTLE_DELIVERY,
				OrderType.OPER_ORDER, DeliveryType.OPER_DELIVERY
		);
		setOrderType(deliveryType);
		
		setDeptGuid(order.getDeptGuid());
		setDeptName(order.getDeptName());
		setTfAddress(order.getTfAddress());
		setLxr(order.getLxr());
		setLxdh(order.getLxdh());
		setAddrGuid(order.getAddrGuid());
		setfOrgName(order.getfOrgName());
    }

    public String getChargeGuid() {
        return chargeGuid;
    }

    public void setChargeGuid(String chargeGuid) {
        this.chargeGuid = chargeGuid;
    }

    public String getUseNo() {
        return useNo;
    }

    public void setUseNo(String useNo) {
        this.useNo = useNo;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public Long getrOrgId() {
        return rOrgId;
    }

    public void setrOrgId(Long rOrgId) {
        this.rOrgId = rOrgId;
    }

    public String getrStorageGuid() {
        return rStorageGuid;
    }

    public void setrStorageGuid(String rStorageGuid) {
        this.rStorageGuid = rStorageGuid;
    }

    public Long getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(Long fOrgId) {
        this.fOrgId = fOrgId;
    }

    public String getfStorageGuid() {
        return fStorageGuid;
    }

    public void setfStorageGuid(String fStorageGuid) {
        this.fStorageGuid = fStorageGuid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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

    public String getBoxUserid() {
        return boxUserid;
    }

    public void setBoxUserid(String boxUserid) {
        this.boxUserid = boxUserid;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
    
    public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }

	public String getfOrgName() {
		return fOrgName;
	}

	public void setfOrgName(String fOrgName) {
		this.fOrgName = fOrgName;
	}

	public String getrOrgName() {
		return rOrgName;
	}

	public void setrOrgName(String rOrgName) {
		this.rOrgName = rOrgName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getAccountPayed() {
		return accountPayed;
	}

	public void setAccountPayed(BigDecimal accountPayed) {
		this.accountPayed = accountPayed;
	}

	public String getbStorageGuid() {
		return bStorageGuid;
	}

	public void setbStorageGuid(String bStorageGuid) {
		this.bStorageGuid = bStorageGuid;
	}
}