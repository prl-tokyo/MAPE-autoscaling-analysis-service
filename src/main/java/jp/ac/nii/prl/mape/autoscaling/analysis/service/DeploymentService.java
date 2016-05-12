package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;
import java.util.Optional;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;

public interface DeploymentService {

	Deployment save(Deployment deployment);

	Collection<Deployment> findAll();

	Optional<Deployment> findById(Integer deploymentId);
	
	Adaptation analyse(Deployment deployment);
	
	Double getAverageLoad(Deployment deployment);
	
}
