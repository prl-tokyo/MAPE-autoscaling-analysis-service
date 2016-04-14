package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;

public interface InstanceService {
	
	Instance save(Instance instance);

	Collection<Instance> findByDeploymentId(Integer deploymentId);
	
	Double getAverageLoadPerCPU(Integer instanceId);

	void setInstanceType(Instance instance);

}
