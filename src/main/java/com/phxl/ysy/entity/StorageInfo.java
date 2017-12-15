package com.phxl.ysy.entity;

import java.util.List;

import com.phxl.core.base.annotation.BaseSql;
import com.phxl.ysy.entity.ConfigInfo;

@BaseSql(tableName="TD_STORAGE_INFO", resultName="com.phxl.ysy.dao.StorageInfoMapper.BaseResultMap")
public class StorageInfo {
    private String storageGuid;

    private Long orgId;

    private String storageCode;

    private String storageName;

    private String parentStorageGuid;

    private String storageLevelCode;

    private String storageFtypeCode;

    private String fstate;
    private String tfRemark;

    private String openFlag;

    private String storageSourceType;

    private String sourceStorageGuid;

    private String deptGuid;
    
    private String orgName;
    
    private String parentStorageName;
    
    private String sourceStorageName;
    
    private String deptName;
    
    private String isAutoApply;
    private String isAutoGzss;
    private String isAutoSsApply;
    private String applyHandlePerson;
    private String gzssHandlePerson;
    private String ssApplyHandlePerson;
    private String applyHandlePersonName;
    private String gzssHandlePersonName;
    private String ssApplyHandlePersonName;
    
    private String jsfs;
    private String shfs;
    
    private String defaultAddress;
    
    private String qrFlag;
    
    private List<ConfigInfo> configInfos;

    public String getStorageGuid() {
        return storageGuid;
    }

    public void setStorageGuid(String storageGuid) {
        this.storageGuid = storageGuid;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getParentStorageGuid() {
        return parentStorageGuid;
    }

    public void setParentStorageGuid(String parentStorageGuid) {
        this.parentStorageGuid = parentStorageGuid;
    }

    public String getStorageLevelCode() {
        return storageLevelCode;
    }

    public void setStorageLevelCode(String storageLevelCode) {
        this.storageLevelCode = storageLevelCode;
    }

    public String getStorageFtypeCode() {
        return storageFtypeCode;
    }

    public void setStorageFtypeCode(String storageFtypeCode) {
        this.storageFtypeCode = storageFtypeCode;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }

    public String getTfRemark() {
		return tfRemark;
	}

	public void setTfRemark(String tfRemark) {
		this.tfRemark = tfRemark;
	}

	public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getStorageSourceType() {
        return storageSourceType;
    }

    public void setStorageSourceType(String storageSourceType) {
        this.storageSourceType = storageSourceType;
    }

    public String getSourceStorageGuid() {
        return sourceStorageGuid;
    }

    public void setSourceStorageGuid(String sourceStorageGuid) {
        this.sourceStorageGuid = sourceStorageGuid;
    }

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid;
    }

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getParentStorageName() {
		return parentStorageName;
	}

	public void setParentStorageName(String parentStorageName) {
		this.parentStorageName = parentStorageName;
	}

	public String getSourceStorageName() {
		return sourceStorageName;
	}

	public void setSourceStorageName(String sourceStorageName) {
		this.sourceStorageName = sourceStorageName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getIsAutoApply() {
		return isAutoApply;
	}

	public void setIsAutoApply(String isAutoApply) {
		this.isAutoApply = isAutoApply;
	}

	public String getIsAutoGzss() {
		return isAutoGzss;
	}

	public void setIsAutoGzss(String isAutoGzss) {
		this.isAutoGzss = isAutoGzss;
	}

	public String getIsAutoSsApply() {
		return isAutoSsApply;
	}

	public void setIsAutoSsApply(String isAutoSsApply) {
		this.isAutoSsApply = isAutoSsApply;
	}

	public String getApplyHandlePerson() {
		return applyHandlePerson;
	}

	public void setApplyHandlePerson(String applyHandlePerson) {
		this.applyHandlePerson = applyHandlePerson;
	}

	public String getGzssHandlePerson() {
		return gzssHandlePerson;
	}

	public void setGzssHandlePerson(String gzssHandlePerson) {
		this.gzssHandlePerson = gzssHandlePerson;
	}

	public String getSsApplyHandlePerson() {
		return ssApplyHandlePerson;
	}

	public void setSsApplyHandlePerson(String ssApplyHandlePerson) {
		this.ssApplyHandlePerson = ssApplyHandlePerson;
	}

	public List<ConfigInfo> getConfigInfos() {
		return configInfos;
	}

	public void setConfigInfos(List<ConfigInfo> configInfos) {
		this.configInfos = configInfos;
	}

	public String getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public String getJsfs() {
		return jsfs;
	}

	public void setJsfs(String jsfs) {
		this.jsfs = jsfs;
	}

	public String getShfs() {
		return shfs;
	}

	public void setShfs(String shfs) {
		this.shfs = shfs;
	}

	public String getApplyHandlePersonName() {
		return applyHandlePersonName;
	}

	public void setApplyHandlePersonName(String applyHandlePersonName) {
		this.applyHandlePersonName = applyHandlePersonName;
	}

	public String getGzssHandlePersonName() {
		return gzssHandlePersonName;
	}

	public void setGzssHandlePersonName(String gzssHandlePersonName) {
		this.gzssHandlePersonName = gzssHandlePersonName;
	}

	public String getSsApplyHandlePersonName() {
		return ssApplyHandlePersonName;
	}

	public void setSsApplyHandlePersonName(String ssApplyHandlePersonName) {
		this.ssApplyHandlePersonName = ssApplyHandlePersonName;
	}

	public String getQrFlag() {
		return qrFlag;
	}

	public void setQrFlag(String qrFlag) {
		this.qrFlag = qrFlag;
	}
}