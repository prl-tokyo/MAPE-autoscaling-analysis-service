package jp.ac.nii.prl.mape.autoscaling.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.repository.AdaptationRepository;

@Service("adaptationService")
public class AdaptationServiceImpl implements AdaptationService {

	@Autowired
	private AdaptationRepository adaptationRepository;
	
	/* (non-Javadoc)
	 * @see jp.ac.nii.prl.mape.autoscaling.analysis.service.AdaptationService#save(jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation)
	 */
	@Override
	public Adaptation save(Adaptation adaptation) {
		return adaptationRepository.save(adaptation);
	}
}
