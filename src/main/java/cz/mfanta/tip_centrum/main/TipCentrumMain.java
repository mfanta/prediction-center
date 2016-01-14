package cz.mfanta.tip_centrum.main;

import cz.mfanta.tip_centrum.service.gui.GuiService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TipCentrumMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(MainConfiguration.class);
		GuiService guiService = applicationContext.getBean(GuiService.class);
		guiService.showMainWindow();
	}

}
