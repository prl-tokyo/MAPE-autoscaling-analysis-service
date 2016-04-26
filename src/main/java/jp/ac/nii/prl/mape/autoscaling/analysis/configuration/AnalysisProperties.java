package jp.ac.nii.prl.mape.autoscaling.analysis.configuration;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.analysis")
public class AnalysisProperties {

	/**
	 * Max CPU utilization threshold, before scaling up is triggered
	 */
	@Valid
	@NotNull
	@Min(0)
	@Max(1)
	private Double maxThreshold;
	
	/**
	 * Min CPU utilization threshold, before scaling down is triggered
	 */
	@Valid
	@NotNull
	@Min(0)
	@Max(1)
	private Double minThreshold;
	
	/**
	 * Scaling up percentage
	 */
	@Valid
	@NotNull
	@Min(0)
	private Double scaleUp;

	public Double getMaxThreshold() {
		return maxThreshold;
	}

	public Double getMinThreshold() {
		return minThreshold;
	}

	public Double getScaleUp() {
		return scaleUp;
	}

	public void setMaxThreshold(Double maxThreshold) {
		this.maxThreshold = maxThreshold;
	}

	public void setMinThreshold(Double minThreshold) {
		this.minThreshold = minThreshold;
	}

	public void setScaleUp(Double scaleUp) {
		this.scaleUp = scaleUp;
	}
}
