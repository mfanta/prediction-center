package cz.mfanta.tip_centrum.utils;

import java.awt.event.MouseEvent;

public class AwtUtils {

	public static boolean isValidDoubleClick(MouseEvent e) {
		return e.getComponent().isEnabled() && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2;
	}

}
