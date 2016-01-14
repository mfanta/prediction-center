package cz.mfanta.tip_centrum.view.model;

import cz.mfanta.tip_centrum.entity.Prediction;

public class PredictionRenderer {

	public String renderPrediction(Prediction prediction) {
		String result = "";
		if (!prediction.isEmpty()) {
			result = Integer.toString(prediction.getHomeGoals()) + ":" + Integer.toString(prediction.getAwayGoals());
		}
		return result;
	}

}
