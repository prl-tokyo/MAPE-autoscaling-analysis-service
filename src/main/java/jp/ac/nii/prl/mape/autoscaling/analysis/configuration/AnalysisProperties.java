package jp.ac.nii.prl.mape.autoscaling.analysis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.analysis")
public class AnalysisProperties {

	private Double maxThreshold;
	
	private Double minThreshold;
	
	private String securityGroup;

	public Double getMaxThreshold() {
		return maxThreshold;
	}

	public void setMaxThreshold(Double maxThreshold) {
		this.maxThreshold = maxThreshold;
	}

	public Double getMinThreshold() {
		return minThreshold;
	}

	public void setMinThreshold(Double minThreshold) {
		this.minThreshold = minThreshold;
	}

	public String getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(String securityGroup) {
		this.securityGroup = securityGroup;
	}
}
