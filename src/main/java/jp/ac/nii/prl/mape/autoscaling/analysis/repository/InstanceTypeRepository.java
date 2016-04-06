package jp.ac.nii.prl.mape.autoscaling.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;

public interface InstanceTypeRepository extends JpaRepository<Instance, Integer> {

}
