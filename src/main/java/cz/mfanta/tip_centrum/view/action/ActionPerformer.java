package cz.mfanta.tip_centrum.view.action;

import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.entity.manager.FixtureManager;
import cz.mfanta.tip_centrum.entity.manager.PredictionManager;
import cz.mfanta.tip_centrum.entity.manager.ResultManager;
import cz.mfanta.tip_centrum.entity.manager.TeamManager;
import cz.mfanta.tip_centrum.service.gui.GuiService;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionPerformer {

    @Autowired
    private GuiService guiService;

    @Autowired
    private FixtureTableModel fixtureTableModel;

    @Autowired
    private ResultManager resultManager;

    @Autowired
    private PredictionManager predictionManager;

	@Autowired
	private TeamManager teamManager;

	@Autowired
	private FixtureManager fixtureManager;

    public void performEditResultAction(Pair<Integer, Integer> modelCell) {
        final EditResultAction action = new EditResultAction(guiService.getMainFrame(), fixtureTableModel, modelCell, resultManager);
        action.performAction();
    }

    public void performEditPredictionAction(Pair<Integer, Integer> modelCell) {
        final EditPredictionAction action = new EditPredictionAction(guiService.getMainFrame(), fixtureTableModel, modelCell, predictionManager);
        action.performAction();
    }

	public void performAddAliasAction() {
		final AddAliasAction action = new AddAliasAction(guiService.getMainFrame(), teamManager, fixtureManager, guiService);
		action.performAction();
	}
}
