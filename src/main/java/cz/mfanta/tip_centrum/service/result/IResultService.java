package cz.mfanta.tip_centrum.service.result;

public interface IResultService {

	/**
	 * Updates the database with latest results.
	 */
	void updateAllResults();

	/**
	 * Updates the database with latest results for a particular competition.
	 *
	 * @param competitionName The competition whose results should be updated.
	 */
	void updateResultsForCompetition(String competitionName);

}
