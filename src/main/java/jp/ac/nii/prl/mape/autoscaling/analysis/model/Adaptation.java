package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Adaptation {

	@JsonIgnore
	@Id
	@GeneratedValue
	private Integer id;
	
	private boolean adapt;
	
	private boolean scaleUp;
	
	@Min(0)
	private int cpuCount;
	
	@JsonIgnore
	@OneToOne
	private Deployment deployment;
	
	public int getCpuCount() {
		return cpuCount;
	}

	@JsonBackReference
	public Deployment getDeployment() {
		return deployment;
	}

	public Integer getId() {
		return id;
	}

	public boolean isAdapt() {
		return adapt;
	}

	public boolean isScaleUp() {
		return scaleUp;
	}
	
	public void setAdapt(boolean adapt) {
		this.adapt = adapt;
	}
	
	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}
	
	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setScaleUp(boolean scaleUp) {
		this.scaleUp = scaleUp;
	}
	
	@Override
	public String toString() {
		String str = "Adaptation ";
		if (!adapt)
			return str + "unnecessary.";
		str = str + "required. Scaling ";
		if (isScaleUp())
			str = str + "up";
		else
			str = str + "down";
		str = str + String.format(" by %s CPUs.", getCpuCount());
		
		return str;
	}
}
