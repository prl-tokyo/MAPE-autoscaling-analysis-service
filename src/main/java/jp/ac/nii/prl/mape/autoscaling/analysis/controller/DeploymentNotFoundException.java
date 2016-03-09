package jp.ac.nii.prl.mape.autoscaling.analysis.controller;

import org.springframework.web.client.RestClientException;

public class DeploymentNotFoundException extends RestClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3187515571409056146L;

	public DeploymentNotFoundException(String message) {
		super(message);
	}
	
	public DeploymentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
