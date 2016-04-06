package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InstanceType {

	@JsonIgnore
	@GeneratedValue
	@Id
	private Integer ID;
	
	private String typeID;
	
	private Integer typeCPUs;
	
	private Double typeRAM;
	
	private Double typeCost;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public Integer getTypeCPUs() {
		return typeCPUs;
	}

	public void setTypeCPUs(Integer typeCPUs) {
		this.typeCPUs = typeCPUs;
	}

	public Double getTypeRAM() {
		return typeRAM;
	}

	public void setTypeRAM(Double typeRAM) {
		this.typeRAM = typeRAM;
	}

	public Double getTypeCost() {
		return typeCost;
	}

	public void setTypeCost(Double typeCost) {
		this.typeCost = typeCost;
	}
	
	
}
