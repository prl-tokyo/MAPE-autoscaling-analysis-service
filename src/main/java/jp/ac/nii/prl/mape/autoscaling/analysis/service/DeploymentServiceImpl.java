package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.autoscaling.analysis.configuration.AnalysisProperties;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.repository.DeploymentRepository;

@Service("deploymentService")
public class DeploymentServiceImpl implements DeploymentService {

	@Autowired
	private DeploymentRepository deploymentRepository;
	
	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private AnalysisProperties analysisProperties;
	
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
		double load = getAverageLoad(deployment.getId());
		Adaptation adaptation = new Adaptation();
		if (load >= analysisProperties.getMaxThreshold()) {
			adaptation.setAdapt(true);
			adaptation.setScaleUp(true);
			adaptation.setCpuCount(new Double(deployment.getNumberCPUs() * 0.1).intValue());
		} else if ((load <= analysisProperties.getMinThreshold()) && (deployment.size() > 1)) {
			adaptation.setAdapt(true);
			adaptation.setScaleUp(false);
			adaptation.setCpuCount(1);
		} else {
			adaptation.setAdapt(false);
		}
		return adaptation;
	}

	@Override
	public Double getAverageLoad(Integer deploymentId) {
		Collection<Instance> instances = instanceService.findByDeploymentId(deploymentId);
		Double load = 0d;
		for (Instance instance:instances) {
			load += instance.getInstLoad();
		}
		return load / instances.size();
	}

}
