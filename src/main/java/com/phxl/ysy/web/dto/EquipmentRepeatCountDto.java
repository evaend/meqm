package com.phxl.ysy.web.dto;

import java.util.ArrayList;
import java.util.List;

public class EquipmentRepeatCountDto {
	
	private String fmodel;

    private String spec;
    
    private List<String> positions;
    
    private int times;
    
	public EquipmentRepeatCountDto() {
		super();
	}

	public EquipmentRepeatCountDto(String fmodel, String spec) {
		super();
		this.fmodel = fmodel;
		this.spec = spec;
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

	public List<String> getPositions() {
		return positions;
	}

	public void setPositions(List<String> positions) {
		this.positions = positions;
	}
	
	public void addPosition(String position) {
		if(this.positions==null){
			positions = new ArrayList<String>();
		}
		positions.add(position);
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fmodel == null) ? 0 : fmodel.hashCode());
		result = prime * result + ((spec == null) ? 0 : spec.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentRepeatCountDto other = (EquipmentRepeatCountDto) obj;
		if (fmodel == null) {
			if (other.fmodel != null)
				return false;
		} else if (!fmodel.equals(other.fmodel))
			return false;
		if (spec == null) {
			if (other.spec != null)
				return false;
		} else if (!spec.equals(other.spec))
			return false;
		return true;
	}
    
}
