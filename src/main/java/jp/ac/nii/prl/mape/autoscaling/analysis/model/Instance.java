package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Instance {
	
	@JsonIgnore
	@ManyToOne
	private Deployment deployment;
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private Integer id;
	
	@NotEmpty
	private String instID;
	
	@NotEmpty
	private String instType;
	
	@JsonIgnore
	@ManyToOne
	private InstanceType instanceType;
	
	@NotNull
	private Double instLoad;

	@JsonBackReference
	public Deployment getDeployment() {
		return deployment;
	}

	public Integer getId() {
		return id;
	}

	public InstanceType getInstanceType() {
		return instanceType;
	}

	public String getInstID() {
		return instID;
	}
	
	public Double getInstLoad() {
		return instLoad;
	}
	
	public String getInstType() {
		return instType;
	}
	
	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setInstanceType(InstanceType instanceType) {
		this.instanceType = instanceType;
	}
	
	public void setInstID(String instID) {
		this.instID = instID;
	}
	
	public void setInstLoad(Double instLoad) {
		this.instLoad = instLoad;
	}
	
	public void setInstType(String instType) {
		this.instType = instType;
	}
	
	@Override
	public String toString() {
		return String.format("VM [%s]: type: %s, load: %4.2f, deployment: %s[%s]", 
				id, instType, instLoad, deployment.getId(), instID);
	}
}
