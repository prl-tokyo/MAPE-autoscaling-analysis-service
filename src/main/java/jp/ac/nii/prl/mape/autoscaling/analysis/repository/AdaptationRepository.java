package jp.ac.nii.prl.mape.autoscaling.analysis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;

public interface AdaptationRepository extends JpaRepository<Adaptation, Integer> {

	Optional<Adaptation> findById(Integer adaptationId);
	
	Optional<Adaptation> findByDeploymentId(Integer deploymentId);
	
}
