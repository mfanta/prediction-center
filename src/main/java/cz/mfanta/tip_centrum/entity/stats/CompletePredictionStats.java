package cz.mfanta.tip_centrum.entity.stats;

import cz.mfanta.tip_centrum.entity.IFixtureGroup;

public class CompletePredictionStats extends AbstractPredictionStats {
	
	private final SimplePredictionStats simpleStats;
	
	private final OddsBasedPredictionStats oddsBasedStats;
	
	public CompletePredictionStats(IFixtureGroup fixtures) {
		super(fixtures);
		simpleStats = new SimplePredictionStats(fixtures);
		oddsBasedStats = new OddsBasedPredictionStats(fixtures);
	}

	@Override
	public int getPercentage() {
		return simpleStats.getPercentage();
	}
	
	public int getSimplePercentage() {
		return simpleStats.getPercentage();
	}
	
	public int getExactPredictionCount() {
		return simpleStats.getExactPredictionCount();
	}
	
	public int getExactPredictionPercentage() {
		return simpleStats.getExactPredictionPercentage();
	}
	
	public int getOddsBasedPercentage() {
		return oddsBasedStats.getPercentage();
	}
	
	public int getCorrectPredictionCount() {
		return simpleStats.getCorrectPredictionCount();
	}
	
	public int getBalance() {
		return oddsBasedStats.getBalance();
	}

}
