package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InstanceType {

	@JsonIgnore
	@GeneratedValue
	@Id
	private Integer ID;
	
	@NotEmpty
	private String typeID;

	@JsonIgnore
	@ManyToOne
	private Deployment deployment;

	@DecimalMin("1")
	private Integer typeCPUs;
	
	@NotNull
	private Double typeRAM;
	
	@NotNull
	private Double typeCost;
	
	public Deployment getDeployment() {
		return deployment;
	}
	
	public Integer getID() {
		return ID;
	}

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

	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}

	public void setID(Integer iD) {
		ID = iD;
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
