package jp.ac.nii.prl.mape.autoscaling.analysis;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.ac.nii.prl.mape.autoscaling.analysis.service.AdaptationService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.DeploymentService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.InstanceService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.InstanceTypeService;

@Configuration
public class TestContext {

	@Bean
	public DeploymentService deploymentService() {
		return Mockito.mock(DeploymentService.class);
	}
	
	@Bean
	public InstanceService instanceService() {
		return Mockito.mock(InstanceService.class);
	}
	
	@Bean
	public InstanceTypeService instanceTypeService() {
		return Mockito.mock(InstanceTypeService.class);
	}
	
	@Bean
	public AdaptationService adaptationService() {
		return Mockito.mock(AdaptationService.class);
	}
}
