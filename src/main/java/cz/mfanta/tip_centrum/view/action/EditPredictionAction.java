package cz.mfanta.tip_centrum.view.action;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.entity.manager.IPredictionManager;
import cz.mfanta.tip_centrum.view.dialog.EditMatchDialog;
import cz.mfanta.tip_centrum.view.dialog.EditPredictionDialogDesign;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;

import javax.swing.*;

public class EditPredictionAction implements TipCentrumAction {

	private final JFrame mainFrame;
	private final FixtureTableModel fixtureTableModel;
	private final Pair<Integer, Integer> modelCell;
	private final IPredictionManager predictionManager;

	public EditPredictionAction(JFrame mainFrame, FixtureTableModel fixtureTableModel, Pair<Integer,
        Integer> modelCell, IPredictionManager predictionManager) {
		this.mainFrame = mainFrame;
		this.fixtureTableModel = fixtureTableModel;
		this.modelCell = modelCell;
		this.predictionManager = predictionManager;
	}

	@Override
	public void performAction() {
		// extract existing data
		final Fixture fixture = fixtureTableModel.getFixture(modelCell.getFirst());
		final String homeTeamName = fixture.getHomeTeam().getName();
		final String awayTeamName = fixture.getAwayTeam().getName();
		final Prediction prediction = getOrCreatePrediction(fixture);
		int homeGoals = prediction.getHomeGoals();
		int awayGoals = prediction.getAwayGoals();
		// create and display dialog
		final EditMatchDialog editPredictionDialog = createEditDialog(homeTeamName, awayTeamName, homeGoals, awayGoals);
		if (editPredictionDialog.wasOkPressed()) {
			storePredictionFromEditDialog(fixture, prediction, editPredictionDialog);
		}
	}

	private void storePredictionFromEditDialog(Fixture fixture, Prediction prediction, EditMatchDialog editPredictionDialog) {
		final int homeGoals = editPredictionDialog.getHomeGoals();
		final int awayGoals = editPredictionDialog.getAwayGoals();
		prediction.setNewScore(homeGoals, awayGoals);
		predictionManager.storePrediction(prediction);
		fixture.setPrediction(prediction);
		fixtureTableModel.fireTableRowsUpdated(modelCell.getFirst(), modelCell.getFirst());
	}

	private EditMatchDialog createEditDialog(String homeTeamName, String awayTeamName, int homeGoals, int awayGoals) {
		final EditMatchDialog editPredictionDialog = new EditMatchDialog(EditPredictionDialogDesign.DIALOG_TITLE, mainFrame, homeTeamName, awayTeamName,
			homeGoals, awayGoals);
		editPredictionDialog.pack();
		// center the dialog within the main frame
		editPredictionDialog.setLocationRelativeTo(mainFrame);
		editPredictionDialog.setVisible(true);
		return editPredictionDialog;
	}

	private Prediction getOrCreatePrediction(Fixture fixture) {
		Prediction prediction = fixture.getPrediction();
		if (prediction == null) {
			prediction = new Prediction(fixture.getFixtureId(), 0, 0);
		}
		return prediction;
	}

}
