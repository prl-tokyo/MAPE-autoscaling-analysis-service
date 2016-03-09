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
import jp.ac.nii.prl.mape.autoscaling.analysis.model.VirtualMachine;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.DeploymentService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.VirtualMachineService;

@RestController
@RequestMapping(value="/deployment")
@Component
public class DeploymentController {

	private final DeploymentService deploymentService;
	private final VirtualMachineService virtualMachineService;
	
	@Autowired
	DeploymentController(DeploymentService deploymentService, 
			VirtualMachineService virtualMachineService) {
		this.deploymentService = deploymentService;
		this.virtualMachineService = virtualMachineService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createDeployment(@RequestBody Deployment deployment) {
		deploymentService.save(deployment);
		for (VirtualMachine vm:deployment.getVms()) {
			vm.setDeployment(deployment);
			virtualMachineService.save(vm);
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
		return this.deploymentService.findById(deploymentId).get();
	}
	
	@RequestMapping(value = "/{deploymentId}/vms", method=RequestMethod.GET)
	Collection<VirtualMachine> getVirtualMachines(@PathVariable Integer deploymentId) {
		return this.virtualMachineService.findByDeploymentId(deploymentId);
	}
	
	@RequestMapping(value = "/{deploymentId}/analysis", method=RequestMethod.GET)
	Adaptation getAdaptation(@PathVariable Integer deploymentId) {
		return this.deploymentService.analyse(this.deploymentService.findById(deploymentId).get());
	}
}
