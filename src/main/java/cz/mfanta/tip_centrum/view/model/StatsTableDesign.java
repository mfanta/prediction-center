package cz.mfanta.tip_centrum.view.model;

public interface StatsTableDesign {
	
	public static final int COLUMN_COUNT = 2;
	
	public static final int ROW_COUNT = 7;
	
	public static final int HEADER_COLUMN_INDEX = 0;
	public static final int DATA_COLUMN_INDEX = 1;
	
	public static final int RESOLVED_MATCHES_ROW_INDEX = 0;
	public static final int EXACT_PREDICTION_COUNT_ROW_INDEX = 1;
	public static final int EXACT_PREDICTION_PERCENTAGE_ROW_INDEX = 2;
	public static final int CORRECT_PREDICTION_COUNT_ROW_INDEX = 3;
	public static final int CORRECT_PREDICTION_PERCENTAGE_ROW_INDEX = 4;
	public static final int BALANCE_ROW_INDEX = 5;
	public static final int BALANCE_PERCENTAGE_ROW_INDEX = 6;
	
	public static final String ROW_RESOLVED_MATCHES = "Resolved Matches:";
	public static final String ROW_EXACT_PREDICTION_COUNT = "Exact Predictions:";
	public static final String ROW_EXACT_PREDICTION_PERCENTAGE = "Exact Predictions(%):";
	public static final String ROW_CORRECT_PREDICTION_COUNT = "Correct Predictions:";
	public static final String ROW_CORRECT_PREDICTION_PERCENTAGE = "Correct Predictions(%):";
	public static final String ROW_BALANCE = "Balance (Odds-Based):";
	public static final String ROW_BALANCE_PERCENTAGE = "Balance (Odds-Based)(%):";
	
	public static final String[] HEADER_NAMES = { ROW_RESOLVED_MATCHES, ROW_EXACT_PREDICTION_COUNT, 
		ROW_EXACT_PREDICTION_PERCENTAGE, ROW_CORRECT_PREDICTION_COUNT, ROW_CORRECT_PREDICTION_PERCENTAGE,
		ROW_BALANCE, ROW_BALANCE_PERCENTAGE};
	
	public static final int[] COLUMN_WIDTHS = { 200, 50 };

    public static final String PERCENTAGE_NUMBER_FORMAT = "#0.00";

}
