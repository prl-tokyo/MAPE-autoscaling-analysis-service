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
	private List<Instance> vms;

	public Integer getId() {
		return id;
	}

	public double getLoadPerCpu(int i) {
		double load = 0;
		for (Instance vm:vms) {
			load += vm.getAverageLoadPerCPU();
		}
		return load / vms.size();
	}

	public List<Instance> getVms() {
		return vms;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setVms(List<Instance> vms) {
		this.vms = vms;
	}

	public int size() {
		return vms.size();
	}
	
	public String toString() {
		return String.format("Deployment %d with %d virtual machines", id, vms.size());
	}

}
