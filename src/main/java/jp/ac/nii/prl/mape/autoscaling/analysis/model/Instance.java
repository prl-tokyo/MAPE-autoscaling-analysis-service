package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Instance {
	
	@JsonIgnore
	@ManyToOne
	private Deployment deployment;
	
	@GeneratedValue
	@JsonIgnore
	@Id
	private Integer vmId;
	
	@NotEmpty
	private String instID;
	
	@NotEmpty
	private String instType;
	
	@NotNull
	private Double instLoad;

	@Min(0)
	private int cpus;

	public double getAverageLoadPerCPU() {
		return getLoad() / cpus;
	}

	public int getCpus() {
		return cpus;
	}

	public Deployment getDeployment() {
		return deployment;
	}
	
	public String getId() {
		return instID;
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

	private double getLoad() {
		return instLoad;
	}

	public void setCpus(int cpus) {
		this.cpus = cpus;
	}
	
	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}
	
	public void setId(String id) {
		this.instID = id;
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
	
	public void setLoad(Double load) {
		this.instLoad = load;
	}
	
	@Override
	public String toString() {
		return String.format("VM [%s]: %d vCPUs, load: %4.2f, deployment: %s[%s]", 
				vmId, cpus, instLoad, deployment.getId(), instID);
	}
}
