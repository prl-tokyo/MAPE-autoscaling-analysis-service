package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InstanceType {

	@JsonIgnore
	@GeneratedValue
	@Id
	private Integer id;
	
	@NotEmpty
	private String typeID;

	@JsonIgnore
	@ManyToOne
	private Deployment deployment;

	@DecimalMin("1")
	private Integer typeCPUs;
	
	@JsonIgnore
	@OneToMany
	private List<Instance> instances;
	
	@NotNull
	private Double typeRAM;

	@NotNull
	private Double typeCost;

	@JsonBackReference
	public Deployment getDeployment() {
		return deployment;
	}
	
	public Integer getId() {
		return id;
	}
	
	public List<Instance> getInstances() {
		return instances;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
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
