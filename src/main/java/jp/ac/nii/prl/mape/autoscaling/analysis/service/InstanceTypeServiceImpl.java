package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.autoscaling.analysis.repository.InstanceTypeRepository;

@Service("InstanceTypeService")
public class InstanceTypeServiceImpl implements InstanceTypeService {

	@Autowired
	private InstanceTypeRepository instanceTypeRepository;
	
}
