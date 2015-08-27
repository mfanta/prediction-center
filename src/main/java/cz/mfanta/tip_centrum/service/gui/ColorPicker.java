package cz.mfanta.tip_centrum.service.gui;

import cz.mfanta.tip_centrum.entity.Result;

import java.awt.*;

public class ColorPicker {

    public Color pickResultColor(Result result) {
        if (result.isHomeWin()) {
            return Colors.HOME_WIN_BG_COLOR;
        } else if (result.isDraw()) {
            return Colors.DRAW_BG_COLOR;
        } else if (result.isAwayWin()) {
            return Colors.AWAY_WIN_BG_COLOR;
        } else {
            return Colors.DEFAULT_RESULT_COLOR;
        }
    }
}
