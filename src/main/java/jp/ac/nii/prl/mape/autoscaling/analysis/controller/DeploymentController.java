package jp.ac.nii.prl.mape.autoscaling.analysis.controller;

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
import jp.ac.nii.prl.mape.autoscaling.analysis.service.AdaptationService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.DeploymentService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.InstanceService;

@RestController
@RequestMapping(value="/deployment")
@Component
public class DeploymentController {

	private final DeploymentService deploymentService;
	private final InstanceService instanceService;
	private final AdaptationService adaptationService;
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	
	@Autowired
	DeploymentController(DeploymentService deploymentService, 
			InstanceService instanceService,
			AdaptationService adaptationService) {
		this.deploymentService = deploymentService;
		this.instanceService = instanceService;
		this.adaptationService = adaptationService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createDeployment(@RequestBody Deployment deployment) {
		
		logger.debug("Creating new deployment");

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
	public Collection<Deployment> getAllDeployments() {
		return deploymentService.findAll();
	}
	
	@RequestMapping(value = "/{deploymentId}", method=RequestMethod.GET)
	Deployment getDeployment(@PathVariable Integer deploymentId) throws DeploymentNotFoundException {
		Deployment deployment = this.deploymentService.findById(deploymentId).get();
		Adaptation adaptation = adaptationService.findByDeploymentId(deploymentId).get();
		deployment.setAdaptation(adaptation);
		return deployment;
	}
	
	@RequestMapping(value = "/{deploymentId}/instances", method=RequestMethod.GET)
	Collection<Instance> getInstances(@PathVariable Integer deploymentId) {
		return this.instanceService.findByDeploymentId(deploymentId);
	}
	
	@RequestMapping(value = "/{deploymentId}/analysis", method=RequestMethod.GET)
	Adaptation getAdaptation(@PathVariable Integer deploymentId) {
		Optional<Adaptation> adaptation = this.adaptationService.findByDeploymentId(deploymentId);
		if (adaptation.isPresent())
			return adaptation.get();
		// else
		throw new AdaptationNotFoundException(String.format("No adaptation with deployment ID %s", 
				deploymentId));
	}
}
