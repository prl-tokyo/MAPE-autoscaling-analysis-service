package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Deployment {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private Integer id;
	
	@JsonIgnore
	@OneToOne(mappedBy="deployment")
	private Adaptation adaptation;

	@OneToMany(mappedBy="deployment")
	private List<Instance> instances;
	
	@OneToMany(mappedBy="deployment")
	private List<InstanceType> instanceTypes;
	
	@JsonManagedReference
	public Adaptation getAdaptation() {
		return adaptation;
	}

	public Integer getId() {
		return id;
	}

	public Optional<Instance> getInstanceByInstID(String instID) {
		for (Instance instance:instances) {
			if (instance.getInstID().equals(instID))
				return Optional.of(instance);
		}
		return Optional.empty();
	}
	
	public Optional<InstanceType> getInstanceTypeByInstType(String instType) {
		for (InstanceType instanceType:instanceTypes) {
			if (instanceType.getTypeID().equals(instType))
				return Optional.of(instanceType);
		}
		return Optional.empty();
	}

	@JsonManagedReference
	public List<Instance> getInstances() {
		return instances;
	}
	
	@JsonManagedReference
	public List<InstanceType> getInstanceTypes() {
		return instanceTypes;
	}

	public int getNumberCPUs() {
		int cpus = 0;
		for (Instance instance:instances)
			cpus += instance.getInstanceType().getTypeCPUs();
		return cpus;
	}

	public void setAdaptation(Adaptation adaptation) {
		this.adaptation = adaptation;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public void setInstanceTypes(List<InstanceType> instanceTypes) {
		this.instanceTypes = instanceTypes;
	}

	public int size() {
		return instances.size();
	}
	
	@Override
	public String toString() {
		return String.format("Deployment %d with %d virtual machines", id, instances.size());
	}

}
