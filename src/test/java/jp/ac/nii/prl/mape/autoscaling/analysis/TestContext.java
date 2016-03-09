package jp.ac.nii.prl.mape.autoscaling.analysis;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.ac.nii.prl.mape.autoscaling.analysis.service.DeploymentService;

@Configuration
public class TestContext {

	@Bean
	public DeploymentService deploymentService() {
		return Mockito.mock(DeploymentService.class);
	}
}
