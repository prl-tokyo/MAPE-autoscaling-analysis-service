package jp.ac.nii.prl.mape.autoscaling.analysis.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

public interface InstanceTypeRepository extends JpaRepository<InstanceType, Integer> {

	InstanceType findById(Integer instTypeId);

	Collection<InstanceType> findByDeploymentId(Integer deploymentId);

}
