package cz.mfanta.tip_centrum.entity.manager;

import cz.mfanta.tip_centrum.entity.Prediction;

public interface IPredictionManager extends IEntityManager {

	public Prediction loadPrediction(long fixtureId);

	public void storePrediction(Prediction prediction);

}
