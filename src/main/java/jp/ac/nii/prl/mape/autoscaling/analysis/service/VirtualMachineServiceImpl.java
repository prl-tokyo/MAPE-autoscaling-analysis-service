package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.repository.VirtualMachineRepository;

@Service("virtualMachineService")
public class VirtualMachineServiceImpl implements VirtualMachineService {

	@Autowired
	private VirtualMachineRepository virtualMachineRepository;
	
	@Override
	public Instance save(Instance virtualMachine) {
		return virtualMachineRepository.save(virtualMachine);
	}

	@Override
	public Collection<Instance> findByDeploymentId(Integer deploymentId) {
		return virtualMachineRepository.findByDeploymentId(deploymentId);
	}

	@Override
	public Double getAverageLoadPerCPU() {
		// TODO Auto-generated method stub
		return null;
	}

}
