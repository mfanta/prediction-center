package cz.mfanta.tip_centrum.entity.reader;

import cz.mfanta.tip_centrum.entity.Competition;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;

public interface IFixtureReader {

	/**
	 * Returns all the upcoming (future) fixtures for the given competition.
	 * 
	 * @param competition
	 *            The competition whose fixtures should be returned.
	 * @return The group of the upcoming fixtures in the given competition.
	 */
	public IFixtureGroup getFixturesForCompetition(Competition competition);

}
