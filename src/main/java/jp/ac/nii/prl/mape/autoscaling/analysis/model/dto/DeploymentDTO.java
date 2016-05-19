package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import java.util.List;

public class DeploymentDTO {

	private AdaptationDTO adaptation;

	private List<InstanceDTO> instances;

	private List<InstanceTypeDTO> instanceTypes;

	public AdaptationDTO getAdaptation() {
		return adaptation;
	}

	public List<InstanceDTO> getInstances() {
		return instances;
	}

	public List<InstanceTypeDTO> getInstanceTypes() {
		return instanceTypes;
	}

	public void setAdaptation(AdaptationDTO adaptation) {
		this.adaptation = adaptation;
	}

	public void setInstances(List<InstanceDTO> instances) {
		this.instances = instances;
	}
	
	public void setInstanceTypes(List<InstanceTypeDTO> instanceTypes) {
		this.instanceTypes = instanceTypes;
	}

}
