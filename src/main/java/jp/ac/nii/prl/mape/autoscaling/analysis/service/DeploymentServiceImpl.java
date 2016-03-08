package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.repository.DeploymentRepository;

@Service("deploymentService")
public class DeploymentServiceImpl implements DeploymentService {

	@Autowired
	private DeploymentRepository deploymentRepository;
	
	@Override
	public Deployment save(Deployment deployment) {
		return deploymentRepository.save(deployment);
	}

	@Override
	public Collection<Deployment> findAll() {
		return deploymentRepository.findAll();
	}

	@Override
	public Optional<Deployment> findById(Integer deploymentId) {
		return deploymentRepository.findById(deploymentId);
	}

	@Override
	public Adaptation analyse(Deployment deployment) {
		double load = deployment.getLoadPerCpu(1);
		Adaptation adaptation = new Adaptation();
		if (load >= 2) {
			adaptation.setAdapt(true);
			adaptation.setScaleUp(true);
			adaptation.setCpuCount(Double.valueOf(load).intValue());
		} else if ((load <= 0.4) && (deployment.size() > 1)) {
			adaptation.setAdapt(true);
			adaptation.setScaleUp(false);
			adaptation.setCpuCount(2);
		} else {
			adaptation.setAdapt(false);
		}
		return adaptation;
	}

}
