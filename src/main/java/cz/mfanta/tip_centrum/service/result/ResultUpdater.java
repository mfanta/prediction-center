package cz.mfanta.tip_centrum.service.result;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.entity.manager.IResultManager;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class ResultUpdater implements IResultUpdater {

	private final IFixtureMatcher fixtureMatcher;

	private final IFixtureManager fixtureManager;

	private final IResultManager resultManager;

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
			log.info("No fixture found for result {}", result);
		}
	}

	@Override
	public void storeResults(Collection<ResultFromReader> results) {
        results.forEach(this::storeResult);
	}

	private void saveChanges(Fixture fixture, Result result) {
		log.info("Storing result for fixture '{}'; result = '{}'", fixture, result);
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
