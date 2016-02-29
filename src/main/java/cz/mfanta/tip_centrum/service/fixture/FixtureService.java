package cz.mfanta.tip_centrum.service.fixture;

import cz.mfanta.tip_centrum.exception.ConversionException;

public class FixtureService {

	/**
	 * Converts the moneyline presented by the bookmaker to the odds (multiplied
	 * by 1000).
	 * 
	 * @param moneyline
	 *            The moneyline to convert
	 * @return The resulting odds.
	 * @throws ConversionException
	 *             Thrown when the conversion has not been possible
	 */
	public int moneylineToOdds(String moneyline) throws ConversionException {
		int result;
		// first, convert the moneyline to an integer
		int moneylineInt = Integer.parseInt(moneyline);
		// treat positive and negative moneyline separately
		// (see http://en.wikipedia.org/wiki/Moneyline_odds#Moneyline_odds)
		if (moneylineInt >= 100) {
			result = 1000 + moneylineInt * 10;
		} else if (moneylineInt <= 0) {
			result = 1000 - 100000 / moneylineInt;
		} else {
			// the number between 0 and 100 is not allowed
			throw new ConversionException("Invalid moneyline: " + moneylineInt);
		}
		return result;
	}

}
