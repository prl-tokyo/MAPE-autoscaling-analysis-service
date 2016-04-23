package jp.ac.nii.prl.mape.autoscaling.analysis.controller;

import java.util.Collection;

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
	public ResponseEntity<?> createDeployment(@RequestBody Deployment deployment) {
		Adaptation adaptation = deploymentService.analyse(deployment);
		deployment.setAdaptation(adaptation);
		adaptationService.save(adaptation);
		deploymentService.save(deployment);
		adaptation.setDeployment(deployment);
		adaptationService.save(adaptation);
		for (InstanceType instType:deployment.getInstanceTypes()) {
			instanceTypeService.save(instType);
		}
		for (Instance instance:deployment.getInstances()) {
			instance.setDeployment(deployment);
			instanceService.setInstanceType(instance);
			instanceService.save(instance);
		}
		
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
		return this.adaptationService.findByDeploymentId(deploymentId).get();
	}
}
