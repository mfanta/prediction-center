package cz.mfanta.tip_centrum.entity.stats;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;

public abstract class AbstractPredictionStats {
	
	private int matchCount;

	AbstractPredictionStats(IFixtureGroup fixtures) {
		super();
		countMatchCount(fixtures);
	}

	public int getMatchCount() {
		return matchCount;
	}
	
	public abstract int getPercentage();
	
	private void countMatchCount(IFixtureGroup fixtures) {
		// add up all the matches with both prediction and result set
		final int allMatchCount = fixtures.getCount();
		matchCount = 0;
		for (int index = 0; index < allMatchCount; index++) {
			final Fixture fixture = fixtures.getAt(index);
			if (fixture.isDecided() && !fixture.getPrediction().isEmpty()) {
				matchCount++;
			}
		}
	}

}
