package jp.ac.nii.prl.mape.autoscaling.analysis.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;

public interface VirtualMachineRepository extends JpaRepository<Instance, Integer> {

	Collection<Instance> findByDeploymentId(Integer deploymentId);

}
