package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import javax.validation.constraints.Min;

public class AdaptationDTO {

	private boolean adapt;
	
	private boolean scaleUp;
	
	@Min(0)
	private int cpuCount;

	public int getCpuCount() {
		return cpuCount;
	}

	public boolean isAdapt() {
		return adapt;
	}

	public boolean isScaleUp() {
		return scaleUp;
	}

	public void setAdapt(boolean adapt) {
		this.adapt = adapt;
	}

	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}

	public void setScaleUp(boolean scaleUp) {
		this.scaleUp = scaleUp;
	}

}
