package jp.ac.nii.prl.mape.autoscaling.analysis.configuration;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.analysis")
public class AnalysisProperties {

	@Valid
	@NotNull
	@Min(0)
	@Max(1)
	private Double maxThreshold;
	
	@Valid
	@NotNull
	@Min(0)
	@Max(1)
	private Double minThreshold;
	
	@Valid
	@NotEmpty
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
