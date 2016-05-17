package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class InstanceTypeDTO {

	@NotEmpty
	private String typeID;

	@DecimalMin("1")
	private Integer typeCPUs;
	
	@NotNull
	private Double typeRAM;

	@NotNull
	private Double typeCost;

	public Double getTypeCost() {
		return typeCost;
	}

	public Integer getTypeCPUs() {
		return typeCPUs;
	}

	public String getTypeID() {
		return typeID;
	}

	public Double getTypeRAM() {
		return typeRAM;
	}

	public void setTypeCost(Double typeCost) {
		this.typeCost = typeCost;
	}

	public void setTypeCPUs(Integer typeCPUs) {
		this.typeCPUs = typeCPUs;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public void setTypeRAM(Double typeRAM) {
		this.typeRAM = typeRAM;
	}
}
