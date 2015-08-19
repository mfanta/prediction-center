package cz.mfanta.tip_centrum.service.result;

import cz.mfanta.tip_centrum.service.IService;

public interface IResultService extends IService {

	/**
	 * Updates the database with latest results.
	 */
	public void updateAllResults();

	/**
	 * Updates the database with latest results for a particular competition.
	 *
	 * @param competitionName The competition whose results should be updated.
	 */
	public void updateResultsForCompetition(String competitionName);

}
