package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

public interface InstanceTypeService {

	InstanceType findById(Integer instTypeId);

	void save(InstanceType instType);

	InstanceType findByDeploymentAndInstanceTypeIds(Integer deploymentId, String instType);

}