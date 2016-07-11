package cz.mfanta.tip_centrum.entity.stats;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.Odds;
import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.Result;

class OddsBasedPredictionStats extends AbstractPredictionStats {
	
	private int percentage;
	
	// the sum of all the match prediction outcomes
	// for correctly predicted match, the odds of the outcomes multiplied by 1000 are added, e.g. 2655 for odds 2.655
	// for badly predicted match, 1000 is subtracted
	private int balance;

	OddsBasedPredictionStats(IFixtureGroup fixtures) {
		super(fixtures);
		countBalanceAndPercentage(fixtures);
	}

	@Override
	public int getPercentage() {
		return percentage;
	}
	
	int getBalance() {
		return balance;
	}
	
	private void countBalanceAndPercentage(IFixtureGroup fixtures) {
		final int allMatchCount = fixtures.getCount();
		final int decidedMatchCount = getMatchCount();
		balance = 0;
		for (int index = 0; index < allMatchCount; index++) {
			final Fixture fixture = fixtures.getAt(index);
			if (fixture.canResolvePrediction()) {
				final Prediction prediction = fixture.getPrediction();
				final Result result = fixture.getResult();
				if (prediction.isAccurate(result)) {
					final Odds odds = fixture.getOdds();
					balance += prediction.getOutcome(odds) - 1000;
				} else {
					balance -= 1000;
				}
			}
		}
		percentage = decidedMatchCount == 0 ? 0 : 10 * balance / decidedMatchCount;
	}

}
