package jp.ac.nii.prl.mape.autoscaling.analysis.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Deployment {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@JsonIgnore
	@OneToOne(mappedBy="deployment")
	private Adaptation adaptation;

	@OneToMany(mappedBy="deployment")
	private List<Instance> instances;

	public Integer getId() {
		return id;
	}

	public List<Instance> getInstances() {
		return instances;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public int size() {
		return instances.size();
	}
	
	public String toString() {
		return String.format("Deployment %d with %d virtual machines", id, instances.size());
	}

}
