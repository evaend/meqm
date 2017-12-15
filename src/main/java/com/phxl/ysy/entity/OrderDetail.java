package com.phxl.ysy.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phxl.core.base.adapter.DecimalOf2Serializer;
import com.phxl.core.base.adapter.DecimalOf4Serializer;
import com.phxl.core.base.annotation.BaseSql;

import java.math.BigDecimal;
import java.util.Date;

@BaseSql(tableName="TB_ORDER_DETAIL", resultName="com.phxl.ysy.dao.OrderDetailMapper.BaseResultMap")
public class OrderDetail {
    private String orderDetailGuid;

    private String orderId;

    private Long fitemid;

    private String materialName;

    private String fmodel;

    private String spec;

    private String ref;

    private Long amount;

    @JsonSerialize(using = DecimalOf4Serializer.class)
    private BigDecimal tenderPrice;

	@JsonSerialize(using = DecimalOf2Serializer.class)
    private BigDecimal sumPrice;

    private String flot;

    private Date prodDate;

    private Date usefulDate;

    private String fbarcode;

    private String tenderUnit;

    private Long fsort;

    private BigDecimal conversion;

    private String tenderDetailGuid;

    private String certGuid;
    
    private String tfBrand;
    
    private String tfTexture;
    
    private String packingTexture;
    
    private String tfPacking;
    
    private String geName;
    
    private Integer sentoutAmount;
    private Integer allowAmount;
    
    private String fOrgName;
    private String fOrgAlias;

    public String getOrderDetailGuid() {
        return orderDetailGuid;
    }

    public void setOrderDetailGuid(String orderDetailGuid) {
        this.orderDetailGuid = orderDetailGuid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getFitemid() {
        return fitemid;
    }

    public void setFitemid(Long fitemid) {
        this.fitemid = fitemid;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getFmodel() {
        return fmodel;
    }

    public void setFmodel(String fmodel) {
        this.fmodel = fmodel;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getTenderPrice() {
        return tenderPrice;
    }

    public void setTenderPrice(BigDecimal tenderPrice) {
        this.tenderPrice = tenderPrice;
    }

    public String getFlot() {
        return flot;
    }

    public void setFlot(String flot) {
        this.flot = flot;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Date getUsefulDate() {
        return usefulDate;
    }

    public void setUsefulDate(Date usefulDate) {
        this.usefulDate = usefulDate;
    }

    public String getFbarcode() {
        return fbarcode;
    }

    public void setFbarcode(String fbarcode) {
        this.fbarcode = fbarcode;
    }

    public String getTenderUnit() {
        return tenderUnit;
    }

    public void setTenderUnit(String tenderUnit) {
        this.tenderUnit = tenderUnit;
    }

    public Long getFsort() {
        return fsort;
    }

    public void setFsort(Long fsort) {
        this.fsort = fsort;
    }

    public BigDecimal getConversion() {
        return conversion;
    }

    public void setConversion(BigDecimal conversion) {
        this.conversion = conversion;
    }

    public String getTenderDetailGuid() {
        return tenderDetailGuid;
    }

    public void setTenderDetailGuid(String tenderDetailGuid) {
        this.tenderDetailGuid = tenderDetailGuid;
    }

    public String getCertGuid() {
        return certGuid;
    }

    public void setCertGuid(String certGuid) {
        this.certGuid = certGuid;
    }

	public String getTfBrand() {
		return tfBrand;
	}

	public void setTfBrand(String tfBrand) {
		this.tfBrand = tfBrand;
	}

	public String getTfTexture() {
		return tfTexture;
	}

	public void setTfTexture(String tfTexture) {
		this.tfTexture = tfTexture;
	}

	public String getPackingTexture() {
		return packingTexture;
	}

	public void setPackingTexture(String packingTexture) {
		this.packingTexture = packingTexture;
	}

	public String getTfPacking() {
		return tfPacking;
	}

	public void setTfPacking(String tfPacking) {
		this.tfPacking = tfPacking;
	}

	public String getGeName() {
		return geName;
	}

	public void setGeName(String geName) {
		this.geName = geName;
	}

	public Integer getSentoutAmount() {
		return sentoutAmount;
	}

	public void setSentoutAmount(Integer sentoutAmount) {
		this.sentoutAmount = sentoutAmount;
	}

	public Integer getAllowAmount() {
		return allowAmount;
	}

	public void setAllowAmount(Integer allowAmount) {
		this.allowAmount = allowAmount;
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

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}
}