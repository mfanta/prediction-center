package cz.mfanta.tip_centrum.view.render;

import cz.mfanta.tip_centrum.entity.Prediction;
import org.springframework.stereotype.Component;

@Component
public class PredictionRenderer {

	public String renderPrediction(Prediction prediction) {
		String result = "";
		if (!prediction.isEmpty()) {
			result = Integer.toString(prediction.getHomeGoals()) + ":" + Integer.toString(prediction.getAwayGoals());
		}
		return result;
	}

}
