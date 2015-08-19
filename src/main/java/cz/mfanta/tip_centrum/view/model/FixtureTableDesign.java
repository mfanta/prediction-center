package cz.mfanta.tip_centrum.view.model;

public interface FixtureTableDesign {

	public static final int COLUMN_COUNT = 10;

    public static final int COMPETITION_COLUMN_INDEX = 0;
	public static final int DATE_COLUMN_INDEX = 1;
	public static final int TIME_COLUMN_INDEX = 2;
	public static final int HOME_TEAM_COLUMN_INDEX = 3;
	public static final int AWAY_TEAM_COLUMN_INDEX = 4;
	public static final int HOME_ODDS_COLUMN_INDEX = 5;
	public static final int DRAW_ODDS_COLUMN_INDEX = 6;
	public static final int AWAY_ODDS_COLUMN_INDEX = 7;
	public static final int PREDICTION_COLUMN_INDEX = 8;
	public static final int RESULT_COLUMN_INDEX = 9;

    public static final String COLUMN_COMPETITION = "Competition";
	public static final String COLUMN_DATE = "Date";
	public static final String COLUMN_TIME = "Time";
	public static final String COLUMN_HOME_TEAM = "Home Team";
	public static final String COLUMN_AWAY_TEAM = "Away Team";
	public static final String COLUMN_HOME_ODDS = "Home Odds";
	public static final String COLUMN_DRAW_ODDS = "Draw Odds";
	public static final String COLUMN_AWAY_ODDS = "Away Odds";
	public static final String COLUMN_PREDICTION = "Prediction";
	public static final String COLUMN_RESULT = "Result";

	public static final String[] COLUMNS = { COLUMN_COMPETITION, COLUMN_DATE, COLUMN_TIME, COLUMN_HOME_TEAM,
        COLUMN_AWAY_TEAM, COLUMN_HOME_ODDS, COLUMN_DRAW_ODDS, COLUMN_AWAY_ODDS, COLUMN_PREDICTION, COLUMN_RESULT };

	public static final int[] COLUMN_WIDTHS = { 50, 100, 100, 300, 300, 100, 100, 100, 100, 100 };

	// public static final String DATE_COLUMN_FORMAT = "d.M.yyyy";
	public static final String DATE_COLUMN_FORMAT = "yyyy-MM-dd";
	public static final String TIME_COLUMN_FORMAT = "HH:mm";
	public static final String ODDS_COLUMN_FORMAT = "0.000";

}
