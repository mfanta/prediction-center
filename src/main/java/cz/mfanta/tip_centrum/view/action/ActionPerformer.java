package cz.mfanta.tip_centrum.view.action;

import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.entity.manager.*;
import cz.mfanta.tip_centrum.service.gui.GuiService;
import cz.mfanta.tip_centrum.service.gui.MainWindowCreator;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionPerformer {

    private final MainWindowCreator mainWindowCreator;

    private final GuiService guiService;

    private final FixtureTableModel fixtureTableModel;

    private final IResultManager resultManager;

    private final IPredictionManager predictionManager;

	private final ITeamManager teamManager;

	private final IFixtureManager fixtureManager;

    public void performEditResultAction(Pair<Integer, Integer> modelCell) {
        final EditResultAction action =
                new EditResultAction(
                        mainWindowCreator.getMainFrame(),
                        fixtureTableModel,
                        modelCell,
                        resultManager
                );
        action.performAction();
    }

    public void performEditPredictionAction(Pair<Integer, Integer> modelCell) {
        final EditPredictionAction action =
                new EditPredictionAction(
                        mainWindowCreator.getMainFrame(),
                        fixtureTableModel,
                        modelCell,
                        predictionManager
                );
        action.performAction();
    }

	public void performAddAliasAction() {
		final AddAliasAction action =
                new AddAliasAction(
                        mainWindowCreator.getMainFrame(),
                        teamManager,
                        fixtureManager,
                        guiService
                );
		action.performAction();
	}
}
