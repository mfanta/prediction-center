package cz.mfanta.tip_centrum.view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cz.mfanta.tip_centrum.service.gui.GuiService;
import cz.mfanta.tip_centrum.service.result.ResultService;

@Component
public class GetResultsActionListener implements ActionListener {

	@Autowired
	private ResultService resultService;

	@Autowired
	private GuiService guiService;

	@Override
	public void actionPerformed(ActionEvent e) {
		resultService.updateAllResults();
		guiService.updateFixtures();
	}
}
