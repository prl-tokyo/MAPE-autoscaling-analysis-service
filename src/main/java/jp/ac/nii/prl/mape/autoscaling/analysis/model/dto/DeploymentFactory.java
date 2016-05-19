package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

public class DeploymentFactory {

	public static Deployment createDeployment(DeploymentDTO dto) {
		return null;
	}
	
	public static DeploymentDTO createDeploymentDTO(Deployment deployment) {
		return null;
	}
	
	public static Adaptation createAdaptation(AdaptationDTO dto, Deployment deployment) {
		Adaptation adaptation = new Adaptation();
		
		adaptation.setAdapt(dto.isAdapt());
		adaptation.setCpuCount(dto.getCpuCount());
		adaptation.setScaleUp(dto.isScaleUp());
		adaptation.setDeployment(deployment);
		
		return adaptation;
	}
	
	public static AdaptationDTO createAdaptationDTO(Adaptation adaptation) {
		AdaptationDTO dto = new AdaptationDTO();
		
		dto.setAdapt(adaptation.isAdapt());
		dto.setCpuCount(adaptation.getCpuCount());
		dto.setScaleUp(adaptation.isScaleUp());
		
		return dto;
	}
	
	public static Instance createInstance(InstanceDTO dto, 
			Deployment deployment, 
			InstanceType instanceType) {
		Instance instance = new Instance();
		
		instance.setDeployment(deployment);
		instance.setInstanceType(instanceType);
		instance.setInstID(dto.getInstID());
		instance.setInstLoad(dto.getInstLoad());
		instance.setInstType(dto.getInstType());
				
		return instance;
	}
	
	public static InstanceDTO createInstanceDTO(Instance instance) {
		InstanceDTO dto = new InstanceDTO();
		
		dto.setInstID(instance.getInstID());
		dto.setInstLoad(instance.getInstLoad());
		dto.setInstType(instance.getInstType());
		
		return dto;
	}
	
	public static InstanceType createInstanceType(InstanceTypeDTO dto) {
		return null;
	}
	
	public static InstanceTypeDTO createInstanceTypeDTO(InstanceType instanceType) {
		return null;
	}

}
