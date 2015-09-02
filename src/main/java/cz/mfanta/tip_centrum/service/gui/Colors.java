package cz.mfanta.tip_centrum.service.gui;

import java.awt.Color;

public interface Colors {
	
	Color PREDICTION_FG_COLOR = Color.MAGENTA;
	// light blue
	Color EMPTY_PREDICTION_BG_COLOR = new Color(135, 206, 250);
	Color NO_RESULT_PREDICTION_BG_COLOR = Color.WHITE;
	// light bright green
	Color EXACT_PREDICTION_BG_COLOR = new Color(127, 255, 0);
	// light pale green
	Color ACCURATE_PREDICTION_BG_COLOR = new Color(152, 251, 152);
	// pink
	Color INACCURATE_PREDICTION_COLOR = new Color(255, 192, 203);
	Color HIGHLIGHTED_TEAM_BG_COLOR = Color.GREEN;
	Color SELECTED_TEAM_BG_COLOR = Color.GRAY;
	Color NORMAL_TEAM_BG_COLOR = Color.WHITE;

	// result colors
    Color HOME_WIN_BG_COLOR = new Color(51, 204, 51);
    Color DRAW_BG_COLOR = new Color(255, 153, 0);
    Color AWAY_WIN_BG_COLOR = new Color(255, 102, 102);
    Color DEFAULT_RESULT_COLOR = Color.WHITE;
}
