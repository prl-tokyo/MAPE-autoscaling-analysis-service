package jp.ac.nii.prl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import jp.ac.nii.prl.mape.autoscaling.analysis.MapeAutoscalingAnalysisApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MapeAutoscalingAnalysisApplication.class)
@WebAppConfiguration
public class MapeAutoscalingAnalysisApplicationTests {

	@Test
	public void contextLoads() {
	}

}
