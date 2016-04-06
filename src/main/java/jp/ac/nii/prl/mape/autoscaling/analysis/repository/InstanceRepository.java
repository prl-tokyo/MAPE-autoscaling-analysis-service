package jp.ac.nii.prl.mape.autoscaling.analysis.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

public interface InstanceRepository extends JpaRepository<Instance, Integer> {

	Collection<Instance> findByDeploymentId(Integer deploymentId);

	Instance findById(Integer instanceId);
	
}
