package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentServiceImpl.class);
	
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
		
		logger.debug("Starting analysis");
		
		double load = getAverageLoad(deployment.getId());
		
		logger.debug(String.format("Average Load per CPU is %d", load));
		
		Adaptation adaptation = new Adaptation();
		if (load >= analysisProperties.getMaxThreshold()) {
			
			logger.debug("Average load per CPU is over the max threshold, scaling up required");
			
			adaptation.setAdapt(true);
			adaptation.setScaleUp(true);
			adaptation.setCpuCount(
					new Double(deployment.getNumberCPUs() 
							* analysisProperties.getScaleUp())
					.intValue());
		} else if ((load <= analysisProperties.getMinThreshold()) && (deployment.size() > 1)) {
			
			logger.debug("Average load per CPU is under the min threshold, scaling down required");
			
			adaptation.setAdapt(true);
			adaptation.setScaleUp(false);
			adaptation.setCpuCount(1);
		} else {
			
			logger.debug("No adaptation necessary");
			
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
		
		logger.debug(String.format("Cumulative load of %d over %d instances. Average load is %d", 
				load, instances.size(), load / instances.size()));
		
		return load / instances.size();
	}

}
