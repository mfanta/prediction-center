package cz.mfanta.tip_centrum.service.gui;

import cz.mfanta.tip_centrum.entity.manager.IPredictionManager;
import cz.mfanta.tip_centrum.entity.manager.IResultManager;
import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.resource.ResourceManager;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@RequiredArgsConstructor
public class GuiService extends AbstractService {

	private final ResourceManager resourceManager;
	
	private final IPredictionManager predictionManager;

	private final IResultManager resultManager;

	private final FixtureTableModel fixtureTableModel;
	
	private final StatsTableModel statsTableModel;

    private final MainWindowCreator mainWindowCreator;

	private JFrame mainFrame;

	public void showMainWindow() {
		SwingUtilities.invokeLater(mainWindowCreator);
	}

	public void updateFixtures() {
		fixtureTableModel.reload();
	}

}
