package cz.mfanta.tip_centrum.entity.stats;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.Result;

public class SimplePredictionStats extends AbstractPredictionStats {
	
	private int correctPredictionCount;
	
	private int exactPredictionCount;

	public SimplePredictionStats(IFixtureGroup fixtures) {
		super(fixtures);
		countCorrectPredictions(fixtures);
	}

	public int getCorrectPredictionCount() {
		return correctPredictionCount;
	}
	
	public int getExactPredictionCount() {
		return exactPredictionCount;
	}
	
	public int getPercentage() {
		return getMatchCount() == 0 ? 0 : (10000 * getCorrectPredictionCount()) / getMatchCount();
	}
	
	public int getExactPredictionPercentage() {
		return getMatchCount() == 0 ? 0 : (10000 * getExactPredictionCount()) / getMatchCount();
	}
	
	private void countCorrectPredictions(IFixtureGroup fixtures) {
		correctPredictionCount = 0;
		exactPredictionCount = 0;
		final int fixtureCount = fixtures.getCount();
		for (int index = 0; index < fixtureCount; index++) {
			final Fixture fixture = fixtures.getAt(index);
			if (fixture.isDecided()) {
				final Result result = fixture.getResult();
				final Prediction prediction = fixture.getPrediction();
				if (prediction.isAccurate(result)) {
					correctPredictionCount++;
					if (prediction.isExact(result)) {
						exactPredictionCount++;
					}
				}
			}
		}
	}

}
