package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.VirtualMachine;

public interface VirtualMachineService {
	
	VirtualMachine save(VirtualMachine virtualMachine);

	Collection<VirtualMachine> findByDeploymentId(Integer deploymentId);

}
