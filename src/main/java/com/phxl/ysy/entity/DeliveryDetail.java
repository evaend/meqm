package com.phxl.ysy.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phxl.core.base.adapter.CustomDateSerializer;
import com.phxl.core.base.adapter.DecimalOf2Serializer;
import com.phxl.core.base.adapter.DecimalOf4Serializer;
import com.phxl.core.base.annotation.BaseSql;

import java.math.BigDecimal;
import java.util.Date;

@BaseSql(tableName="TB_DELIVERY_DETAIL", resultName="com.phxl.ysy.dao.DeliveryDetailMapper.BaseResultMap")
public class DeliveryDetail {
    private String sendDetailGuid;

    private String sendId;

    private Integer fsort;

    private Long fitemid;

    private Long amount;

    private Long usedAmount;

    private BigDecimal conversion;

	@JsonSerialize(using = DecimalOf4Serializer.class)
    private BigDecimal tenderPrice;

	@JsonSerialize(using = DecimalOf2Serializer.class)
    private BigDecimal amountMoney;

    private String flot;

	@JsonSerialize(using = CustomDateSerializer.class)
    private Date prodDate;

	@JsonSerialize(using = CustomDateSerializer.class)
    private Date usefulDate;

    private String fbarcode;

    private String orderDetailGuid;

    private String tenderUnit;

    private String tenderDetailGuid;

    private String materialName;

    private String fmodel;

    private String spec;

    private String ref;
    
    private String tfBrand;
    
    private String tfTexture;
    
    private String packingTexture;
    
    private String tfPacking;
    
    private String geName;

    private String attributeId;

    private String attributeName;

    private String saveFlag;

    private String certGuid;

    public String getCertGuid() {
        return certGuid;
    }

    public void setCertGuid(String certGuid) {
        this.certGuid = certGuid;
    }

    public BigDecimal getConversion() {
        return conversion;
    }

    public void setConversion(BigDecimal conversion) {
        this.conversion = conversion;
    }

    public String getSendDetailGuid() {
        return sendDetailGuid;
    }

    public void setSendDetailGuid(String sendDetailGuid) {
        this.sendDetailGuid = sendDetailGuid;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public Integer getFsort() {
        return fsort;
    }

    public void setFsort(Integer fsort) {
        this.fsort = fsort;
    }

    public Long getFitemid() {
        return fitemid;
    }

    public void setFitemid(Long fitemid) {
        this.fitemid = fitemid;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Long usedAmount) {
        this.usedAmount = usedAmount;
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

    public String getOrderDetailGuid() {
        return orderDetailGuid;
    }

    public void setOrderDetailGuid(String orderDetailGuid) {
        this.orderDetailGuid = orderDetailGuid;
    }

    public String getTenderUnit() {
        return tenderUnit;
    }

    public void setTenderUnit(String tenderUnit) {
        this.tenderUnit = tenderUnit;
    }

    public String getTenderDetailGuid() {
        return tenderDetailGuid;
    }

    public void setTenderDetailGuid(String tenderDetailGuid) {
        this.tenderDetailGuid = tenderDetailGuid;
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

	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public BigDecimal getAmountMoney() {
		return amountMoney;
	}

	public void setAmountMoney(BigDecimal amountMoney) {
		this.amountMoney = amountMoney;
	}
}