package jp.ac.nii.prl.mape.autoscaling.analysis;

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
	private List<VirtualMachine> vms;

	public Integer getId() {
		return id;
	}

	public List<VirtualMachine> getVms() {
		return vms;
	}

	public void setVms(List<VirtualMachine> vms) {
		this.vms = vms;
	}

	public double getLoadPerCpu(int i) {
		double load = 0;
		for (VirtualMachine vm:vms) {
			load += vm.getAverageLoadPerCPU(1);
		}
		return load / vms.size();
	}

	public int size() {
		return vms.size();
	}
	
	public String toString() {
		return String.format("Deployment %d with %d virtual machines", id, vms.size());
	}

}
