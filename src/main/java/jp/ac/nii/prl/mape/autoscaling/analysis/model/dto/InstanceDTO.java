package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class InstanceDTO {

	@NotEmpty
	private String instID;
	
	@NotEmpty
	private String instType;
	
	@NotNull
	private Double instLoad;

	public String getInstID() {
		return instID;
	}

	public void setInstID(String instID) {
		this.instID = instID;
	}

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public Double getInstLoad() {
		return instLoad;
	}

	public void setInstLoad(Double instLoad) {
		this.instLoad = instLoad;
	}
}
