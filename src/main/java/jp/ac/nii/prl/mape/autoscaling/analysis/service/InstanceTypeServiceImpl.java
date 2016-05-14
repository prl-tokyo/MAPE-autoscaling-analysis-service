package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;
import jp.ac.nii.prl.mape.autoscaling.analysis.repository.InstanceTypeRepository;

@Service("instanceTypeService")
public class InstanceTypeServiceImpl implements InstanceTypeService {

	@Autowired
	private InstanceTypeRepository instanceTypeRepository;

	@Override
	public InstanceType findById(Integer instTypeId) {
		return instanceTypeRepository.findById(instTypeId);
	}

	@Override
	public void save(InstanceType instType) {
		instanceTypeRepository.save(instType);
	}

	@Override
	public InstanceType findByDeploymentAndInstanceTypeIds(Integer deploymentId, String instType) {
		Collection<InstanceType> instanceTypes = instanceTypeRepository.findByDeploymentId(deploymentId);
		for (InstanceType instanceType:instanceTypes) {
			if (instanceType.getTypeID().equals(instType))
				return instanceType;
		}
		return null;
	}

	@Override
	public Collection<InstanceType> findByDeploymentId(Integer deploymentId) {
		return instanceTypeRepository.findByDeploymentId(deploymentId);
	}
}
