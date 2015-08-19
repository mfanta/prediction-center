package cz.mfanta.tip_centrum.service.result;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.entity.manager.IResultManager;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import cz.mfanta.tip_centrum.service.log.LogService;

@Component
public class ResultUpdater implements IResultUpdater {

	@Autowired
	private IFixtureMatcher fixtureMatcher;

	@Autowired
	private IFixtureManager fixtureManager;

	@Autowired
	private IResultManager resultManager;

	@Autowired
	private LogService logService;

	@Override
	public void storeResult(ResultFromReader result) {
		final Fixture fixture = fixtureMatcher.getFixtureForResult(result);
		if (fixture != null) {
			final Result storedResult = fixture.getResult();
			if (storedResult == null) {
				final Result newResult = createResult(result, fixture);
				saveChanges(fixture, newResult);
			} else if (!result.sameResult(storedResult)) {
				updateResult(storedResult, result);
				saveChanges(fixture, storedResult);
			}
		} else {
			logService.logInfo("No fixture found for result " + result.toString());
		}
	}

	@Override
	public void storeResults(Collection<ResultFromReader> results) {
		for (ResultFromReader result : results) {
			storeResult(result);
		}
	}

	private void saveChanges(Fixture fixture, Result result) {
		logService.logInfo("Storing result for fixture '" + fixture.toString() + "'; result = '" + result + "'");
		fixture.setResult(result);
		storeUpdatedResult(result);
		storeUpdatedFixture(fixture);
	}

	private void updateResult(Result persistentResult, ResultFromReader readerResult) {
		persistentResult.setNewScore(readerResult.getHomeGoals(), readerResult.getAwayGoals());
	}

	private Result createResult(ResultFromReader readerResult, Fixture fixture) {
		return new Result(fixture.getFixtureId(), readerResult.getHomeGoals(), readerResult.getAwayGoals());
	}

	private void storeUpdatedResult(Result result) {
		resultManager.storeResult(result);
	}

	private void storeUpdatedFixture(Fixture fixture) {
		fixtureManager.updateFixture(fixture);
	}

}
