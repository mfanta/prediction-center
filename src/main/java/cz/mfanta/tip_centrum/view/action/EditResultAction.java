package cz.mfanta.tip_centrum.view.action;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.entity.manager.IResultManager;
import cz.mfanta.tip_centrum.view.dialog.EditMatchDialog;
import cz.mfanta.tip_centrum.view.dialog.EditResultDialogDesign;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;

import javax.swing.*;

public class EditResultAction implements TipCentrumAction {

	private final JFrame mainFrame;
	private final Pair<Integer, Integer> modelCell;
	private final IResultManager resultManager;

	public EditResultAction(JFrame mainFrame, FixtureTableModel fixtureTableModel, Pair<Integer, Integer> modelCell,
							IResultManager resultManager) {
		super();
		this.mainFrame = mainFrame;
		this.fixtureTableModel = fixtureTableModel;
		this.modelCell = modelCell;
		this.resultManager = resultManager;
	}
	private final FixtureTableModel fixtureTableModel;

	@Override
	public void performAction() {
		final Fixture fixture = fixtureTableModel.getFixture(modelCell.getFirst());
		final String homeTeamName = fixture.getHomeTeam().getName();
		final String awayTeamName = fixture.getAwayTeam().getName();
		final Result result = getOrCreateResult(fixture);
		int homeGoals = result.getHomeGoals();
		int awayGoals = result.getAwayGoals();
		final EditMatchDialog editResultDialog = createEditDialog(homeTeamName, awayTeamName, homeGoals, awayGoals);
		if (editResultDialog.wasOkPressed()) {
			storeResultFromEditDialog(fixture, result, editResultDialog);
		}

	}

	private void storeResultFromEditDialog(Fixture fixture, Result result, EditMatchDialog editResultDialog) {
		final int homeGoals = editResultDialog.getHomeGoals();
		final int awayGoals = editResultDialog.getAwayGoals();
		result.setNewScore(homeGoals, awayGoals);
		resultManager.storeResult(result);
		fixture.setResult(result);
		fixtureTableModel.fireTableRowsUpdated(modelCell.getFirst(), modelCell.getFirst());
	}

	private EditMatchDialog createEditDialog(String homeTeamName, String awayTeamName, int homeGoals, int awayGoals) {
		// create and display dialog
		final EditMatchDialog editResultDialog = new EditMatchDialog(EditResultDialogDesign.DIALOG_TITLE, mainFrame, homeTeamName, awayTeamName, homeGoals,
			awayGoals);
		editResultDialog.pack();
		// center the dialog within the main frame
		editResultDialog.setLocationRelativeTo(mainFrame);
		editResultDialog.setVisible(true);
		return editResultDialog;
	}

	private Result getOrCreateResult(Fixture fixture) {
		Result result = fixture.getResult();
		if (result == null) {
			result = new Result(fixture.getFixtureId(), 1, 0);
		}
		return result;
	}

}
