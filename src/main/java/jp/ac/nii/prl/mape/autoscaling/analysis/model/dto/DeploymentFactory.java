package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

public class DeploymentFactory {
	
	private final static Logger logger = LoggerFactory.getLogger(DeploymentFactory.class);

	public static Deployment createDeployment(DeploymentDTO dto) {
		logger.trace("Creating Deployment from DeploymentDTO");
		
		Deployment deployment = new Deployment();
		
		deployment.setAdaptation(createAdaptation(dto.getAdaptation(), deployment));
		
		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		List<Instance> instances = new ArrayList<>();
		for (InstanceDTO instanceDTO:dto.getInstances()) {
			map.add(instanceDTO.getInstType(), instanceDTO.getInstID());
			// we don't have instance types yet, so we're using null, and add it later
			instances.add(createInstance(instanceDTO, deployment, null));
		}
		deployment.setInstances(instances);
		
		List<InstanceType> instanceTypes = new ArrayList<>();
		for (InstanceTypeDTO instanceTypeDTO:dto.getInstanceTypes()) {
			List<Instance> instancesOfType = new ArrayList<>();
			for (String instID:map.get(instanceTypeDTO.getTypeID())) {
				Optional<Instance> instance = deployment.getInstanceByInstID(instID);
				if (instance.isPresent())
					instancesOfType.add(instance.get());
				else {
					logger.error(String.format("Can't find instance with ID %s", instID));
					throw new RuntimeException(String.format("Can't find instance with ID %s", 
							instID));
				}
			}
			instanceTypes.add(createInstanceType(instanceTypeDTO, deployment, instancesOfType));
		}
		deployment.setInstanceTypes(instanceTypes);
		
		for (Instance instance:deployment.getInstances()) {
			Optional<InstanceType> instanceType = deployment.
					getInstanceTypeByInstType(instance.getInstType());
			if (instanceType.isPresent())
				instance.setInstanceType(instanceType.get());
			else {
				logger.error(String.format("Can't find instance type with ID %s",
						instance.getInstType()));
				throw new RuntimeException(String.format("Can't find instance type with ID %s", 
						instance.getInstType()));
			}
		}
		
		logger.trace(String.format("Done creating Deployment from DeploymentDTO: %d instances, "
				+ "and %d instance types", 
				deployment.getInstances().size(), 
				deployment.getInstanceTypes().size()));
		return deployment;
	}
	
	public static DeploymentDTO createDeploymentDTO(Deployment deployment) {
		logger.trace("Creating DeploymentDTO from Deployment");
		
		DeploymentDTO dto = new DeploymentDTO();
		
		dto.setAdaptation(createAdaptationDTO(deployment.getAdaptation()));
		
		List<InstanceDTO> instanceDTOs = new ArrayList<>();
		for (Instance instance:deployment.getInstances())
			instanceDTOs.add(createInstanceDTO(instance));
		dto.setInstances(instanceDTOs);
		
		List<InstanceTypeDTO> instanceTypeDTOs = new ArrayList<>();
		for (InstanceType instanceType:deployment.getInstanceTypes())
			instanceTypeDTOs.add(createInstanceTypeDTO(instanceType));
		dto.setInstanceTypes(instanceTypeDTOs);
		
		logger.trace(String.format("Creating DeploymentDTO: %d instances, %d instance types", 
				dto.getInstances().size(), 
				dto.getInstanceTypes().size()));
		
		return dto;
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
	
	public static InstanceType createInstanceType(InstanceTypeDTO dto,
			Deployment deployment,
			List<Instance> instances) {
		InstanceType instanceType = new InstanceType();
		
		instanceType.setDeployment(deployment);
		instanceType.setInstances(instances);
		instanceType.setTypeCost(dto.getTypeCost());
		instanceType.setTypeCPUs(dto.getTypeCPUs());
		instanceType.setTypeID(dto.getTypeID());
		instanceType.setTypeRAM(dto.getTypeRAM());
		
		return instanceType;
	}
	
	public static InstanceTypeDTO createInstanceTypeDTO(InstanceType instanceType) {
		InstanceTypeDTO dto = new InstanceTypeDTO();
		
		dto.setTypeCost(instanceType.getTypeCost());
		dto.setTypeCPUs(instanceType.getTypeCPUs());
		dto.setTypeID(instanceType.getTypeID());
		dto.setTypeRAM(instanceType.getTypeRAM());
		
		return dto;
	}

}
