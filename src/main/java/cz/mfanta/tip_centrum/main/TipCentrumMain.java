package cz.mfanta.tip_centrum.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.mfanta.tip_centrum.service.gui.GuiService;

public class TipCentrumMain {

	private static final String APP_CONTEXT_FILE_NAME = "application.xml";

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE_NAME);
		GuiService guiService = applicationContext.getBean("guiService", GuiService.class);
		guiService.showMainWindow();
	}

}
