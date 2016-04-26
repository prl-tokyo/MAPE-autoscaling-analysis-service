package jp.ac.nii.prl.mape.autoscaling.analysis;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jp.ac.nii.prl.mape.autoscaling.analysis.controller.AdaptationNotFoundException;
import jp.ac.nii.prl.mape.autoscaling.analysis.controller.DeploymentNotFoundException;

@ControllerAdvice
public class RestErrorHandler {
	
	public RestErrorHandler() {
	}
	
	@ExceptionHandler(DeploymentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleDeploymentNotFoundException(DeploymentNotFoundException ex) {
		
	}
	
	@ExceptionHandler(AdaptationNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleAdaptationNotFoundException(AdaptationNotFoundException ex) {
		
	}

}
