package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.repository.InstanceRepository;

@Service("virtualMachineService")
public class InstanceServiceImpl implements InstanceService {

	@Autowired
	private InstanceRepository virtualMachineRepository;
	
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
