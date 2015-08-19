package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.dao.PredictionDao;
import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.service.AbstractService;
import org.springframework.stereotype.Component;

@Component
public class PredictionManager extends AbstractService implements IPredictionManager {

	@Autowired
	private PredictionDao predictionDao;

	@Override
	public Prediction loadPrediction(long fixtureId) {
		return predictionDao.getById(fixtureId);
	}

	@Override
	public void storePrediction(Prediction prediction) {
		final Prediction existingPrediction = loadPrediction(prediction.getFixtureId());
		if (existingPrediction != null) {
			predictionDao.update(prediction);
		} else {
			predictionDao.save(prediction);
		}
	}

}
