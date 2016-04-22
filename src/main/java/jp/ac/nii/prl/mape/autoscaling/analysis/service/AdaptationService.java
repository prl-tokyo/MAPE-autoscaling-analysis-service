package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Optional;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;

public interface AdaptationService {

	Adaptation save(Adaptation adaptation);

	Optional<Adaptation> findByDeploymentId(Integer deploymentId);
}