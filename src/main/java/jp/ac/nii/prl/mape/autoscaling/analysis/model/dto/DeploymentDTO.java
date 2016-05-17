package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import java.util.List;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

public class DeploymentDTO {

	private Adaptation adaptation;

	private List<Instance> instances;

	private List<InstanceType> instanceTypes;

	public Adaptation getAdaptation() {
		return adaptation;
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public List<InstanceType> getInstanceTypes() {
		return instanceTypes;
	}

	public void setAdaptation(Adaptation adaptation) {
		this.adaptation = adaptation;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}
	
	public void setInstanceTypes(List<InstanceType> instanceTypes) {
		this.instanceTypes = instanceTypes;
	}

}
