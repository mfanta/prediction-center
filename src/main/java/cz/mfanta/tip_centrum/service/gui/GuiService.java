package cz.mfanta.tip_centrum.service.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.entity.manager.PredictionManager;
import cz.mfanta.tip_centrum.entity.manager.ResultManager;
import cz.mfanta.tip_centrum.service.AbstractService;
import cz.mfanta.tip_centrum.service.resource.ResourceManager;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import org.springframework.stereotype.Component;

@Component
public class GuiService extends AbstractService {

	@Autowired
	private ResourceManager resourceManager;
	
	@Autowired
	private PredictionManager predictionManager;
	
	@Autowired
	private FixtureTableModel fixtureTableModel;
	
	@Autowired
	private StatsTableModel statsTableModel;
	
	@Autowired
	private ResultManager resultManager;

    @Autowired
    private MainWindowCreator mainWindowCreator;

	private JFrame mainFrame;

	public void showMainWindow() {
		SwingUtilities.invokeLater(mainWindowCreator);
	}

	/**
	 * @return the mainFrame
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void updateFixtures() {
		fixtureTableModel.reload();
	}

}
