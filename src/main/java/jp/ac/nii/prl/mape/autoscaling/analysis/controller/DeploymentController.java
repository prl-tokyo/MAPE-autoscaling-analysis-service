package jp.ac.nii.prl.mape.autoscaling.analysis.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.dto.AdaptationDTO;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.dto.DeploymentDTO;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.dto.DeploymentFactory;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.dto.InstanceDTO;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.dto.InstanceTypeDTO;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.AdaptationService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.DeploymentService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.InstanceService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.InstanceTypeService;

@RestController
@RequestMapping(value="/deployment")
@Component
public class DeploymentController {

	private final DeploymentService deploymentService;
	private final InstanceService instanceService;
	private final InstanceTypeService instanceTypeService;
	private final AdaptationService adaptationService;
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	
	@Autowired
	DeploymentController(DeploymentService deploymentService, 
			InstanceService instanceService,
			InstanceTypeService instanceTypeService,
			AdaptationService adaptationService) {
		this.deploymentService = deploymentService;
		this.instanceService = instanceService;
		this.instanceTypeService = instanceTypeService;
		this.adaptationService = adaptationService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createDeployment(@RequestBody DeploymentDTO deploymentDTO) {
		
		logger.debug("Creating new deployment");
		
		Deployment deployment = DeploymentFactory.createDeployment(deploymentDTO);
		
		logger.trace(String.format("Deployment created from DTO, with %d instances and %d "
				+ "instance types", 
				deployment.getInstances().size(), 
				deployment.getInstanceTypes().size()));

		deploymentService.save(deployment);
		Adaptation adaptation = deploymentService.analyse(deployment);
		deployment.setAdaptation(adaptation);
		
		logger.debug(String.format("Analysis finished. Decision: %s", adaptation.isAdapt()));
		
		adaptationService.save(adaptation);
		adaptation.setDeployment(deployment);
		adaptationService.save(adaptation);
		
		logger.debug(String.format("Deployment saved with ID %s", deployment.getId()));
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(deployment.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Collection<DeploymentDTO> getAllDeployments() {
		
		logger.debug("Getting all deployments");
		
		Collection<Deployment> deployments = deploymentService.findAll();
		
		logger.debug(String.format("Found %d deployments", deployments.size()));
		
		Collection<DeploymentDTO> dtos = new ArrayList<>();
		for (Deployment deployment:deployments)
			dtos.add(DeploymentFactory.createDeploymentDTO(deployment));
		
		logger.debug("Created DTOs from deployments");
		
		return dtos;
	}
	
	@RequestMapping(value = "/{deploymentId}", method=RequestMethod.GET)
	DeploymentDTO getDeployment(@PathVariable Integer deploymentId) 
			throws DeploymentNotFoundException, AdaptationNotFoundException {
		
		logger.debug(String.format("Getting deployment with ID %s", deploymentId));
		
		if (!deploymentService.findById(deploymentId).isPresent()) {
			logger.error(String.format("No deployment with id %s", deploymentId));
			throw new DeploymentNotFoundException(String.format("No deployment with ID %s", deploymentId));
		}
		
		Deployment deployment = this.deploymentService.findById(deploymentId).get();
		
		if (!adaptationService.findByDeploymentId(deploymentId).isPresent()) {
			logger.error(String.format("No adaptation for deployment %s", deploymentId));
			throw new AdaptationNotFoundException(String.format("No adaptation for deployment %s", 
					deploymentId));
		}
		
		logger.debug("Found deployment and adaptation");
		
		Adaptation adaptation = adaptationService.findByDeploymentId(deploymentId).get();
		deployment.setAdaptation(adaptation);
		return DeploymentFactory.createDeploymentDTO(deployment);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{deploymentId}/instances", method=RequestMethod.GET)
	Collection<InstanceDTO> getInstances(@PathVariable Integer deploymentId) {
		
		logger.debug(String.format("Getting list of instances in deployment %s", deploymentId));
		
		Collection<Instance> instances = instanceService.findByDeploymentId(deploymentId);
		
		logger.trace(String.format("Found %d instances", instances.size()));
		
		return (Collection<InstanceDTO>) instances.stream()
				.map(inst -> DeploymentFactory.createInstanceDTO(inst));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{deploymentId}/instancetypes", method=RequestMethod.GET)
	Collection<InstanceTypeDTO> getInstanceTypes(@PathVariable Integer deploymentId) {
		
		logger.debug(String.format("Getting list of instance types in deployment %s", deploymentId));
		
		Collection<InstanceType> instanceTypes = instanceTypeService.findByDeploymentId(deploymentId);
		
		logger.trace(String.format("Found %d instance types", instanceTypes.size()));
		
		return (Collection<InstanceTypeDTO>) instanceTypes.stream()
				.map(it -> DeploymentFactory.createInstanceTypeDTO(it));
	}
	
	@RequestMapping(value = "/{deploymentId}/analysis", method=RequestMethod.GET)
	AdaptationDTO getAdaptation(@PathVariable Integer deploymentId) {
		
		logger.debug(String.format("Getting adaptation for deployment %s", deploymentId));
		
		Optional<Adaptation> adaptation = this.adaptationService.findByDeploymentId(deploymentId);
		if (adaptation.isPresent()) {
			logger.debug("Adaptation found");
			return DeploymentFactory.createAdaptationDTO(adaptation.get());
		}
		// else
		logger.debug(String.format("Could not find adaptation for deployment %s", deploymentId));
		throw new AdaptationNotFoundException(String.format("No adaptation with deployment ID %s", 
				deploymentId));
	}
}
