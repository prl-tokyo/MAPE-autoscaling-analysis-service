package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

public interface InstanceTypeService {

	InstanceType findById(Integer instTypeId);
	
	Collection<InstanceType> findByDeploymentId(Integer deploymentId);

	void save(InstanceType instType);

	InstanceType findByDeploymentAndInstanceTypeIds(Integer deploymentId, String instType);

}