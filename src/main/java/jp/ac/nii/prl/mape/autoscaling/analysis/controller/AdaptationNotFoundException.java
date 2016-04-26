package jp.ac.nii.prl.mape.autoscaling.analysis.controller;

import org.springframework.web.client.RestClientException;

public class AdaptationNotFoundException extends RestClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4254703530439049802L;

	public AdaptationNotFoundException(String msg) {
		super(msg);
	}

	public AdaptationNotFoundException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
