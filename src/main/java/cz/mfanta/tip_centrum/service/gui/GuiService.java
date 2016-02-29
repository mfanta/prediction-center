package cz.mfanta.tip_centrum.service.gui;

import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@RequiredArgsConstructor
public class GuiService {

	private final FixtureTableModel fixtureTableModel;

    private final MainWindowCreator mainWindowCreator;

	public void showMainWindow() {
		SwingUtilities.invokeLater(mainWindowCreator);
	}

	public void updateFixtures() {
		fixtureTableModel.reload();
	}

}
