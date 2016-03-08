package jp.ac.nii.prl.mape.autoscaling.analysis.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.VirtualMachine;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Integer> {

	Collection<VirtualMachine> findByDeploymentId(Integer deploymentId);

}
