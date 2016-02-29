package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.dao.IPredictionDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PredictionManager implements IPredictionManager {

	private final IPredictionDao predictionDao;

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
