package cz.mfanta.tip_centrum.service.result;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.reader.ResultFromReader;

/**
 * The interface for fixture matcher capable of finding a corresponding fixture object for a particular match result.
 */
public interface IFixtureMatcher {

	/**
	 * Returns a fixture object that represents a match whose result is passed as a parameter.
	 *
	 * @param result The result for which the fixture should be found.
	 * @return A fixture object if a corresponding fixture exists. Null otherwise.
	 */
	Fixture getFixtureForResult(ResultFromReader result);
}
