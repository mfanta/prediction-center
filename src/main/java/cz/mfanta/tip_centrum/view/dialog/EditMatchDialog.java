package cz.mfanta.tip_centrum.view.dialog;

import java.awt.*;

import javax.swing.*;

import cz.mfanta.tip_centrum.view.handlers.IOkCancelHandler;
import cz.mfanta.tip_centrum.view.listeners.OkCancelHandlingActionListener;
import cz.mfanta.tip_centrum.view.listeners.SelectingFocusListener;

public class EditMatchDialog extends CommonDialog implements IOkCancelHandler {

	private static final long serialVersionUID = 1L;

	private JTextField homeGoalsField;
	private JTextField awayGoalsField;
	private int homeGoals;
	private int awayGoals;

	public EditMatchDialog(String title, Frame mainFrame, String homeTeamName, String awayTeamName, int homeGoals, int awayGoals) {
		super(mainFrame, title);
		this.homeGoals = homeGoals;
		this.awayGoals = awayGoals;
		final Container contentPane = getContentPane();
		setupLayout(contentPane);
		final SelectingFocusListener selectingFocusListener = new SelectingFocusListener();
		addLabelToGridBagContainer(homeTeamName, 0, 0, contentPane);
		addLabelToGridBagContainer(awayTeamName, 1, 0, contentPane);
		createHomeGoalsField(homeGoals, contentPane, selectingFocusListener);
		createAwayGoalsField(awayGoals, contentPane, selectingFocusListener);
		final JButton okButton = createOkButton(contentPane, 0, 2);
		final JButton cancelButton = createCancelButton(contentPane, 1, 2);
		OkCancelHandlingActionListener buttonListener = new OkCancelHandlingActionListener(this);
		okButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);
	}

	private void createAwayGoalsField(int awayGoals, Container contentPane, SelectingFocusListener selectingFocusListener) {
		awayGoalsField = new JTextField(getDisplayedGoals(awayGoals));
		awayGoalsField.addFocusListener(selectingFocusListener);
		addTextToGridBagContainer(awayGoalsField, 1, 1, contentPane);
	}

	private void createHomeGoalsField(int homeGoals, Container contentPane, SelectingFocusListener selectingFocusListener) {
		homeGoalsField = new JTextField(getDisplayedGoals(homeGoals));
		homeGoalsField.addFocusListener(selectingFocusListener);
		addTextToGridBagContainer(homeGoalsField, 0, 1, contentPane);
	}

	protected GridBagLayout createLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 130, 130, 0 };
		gridBagLayout.rowHeights = new int[] { 25, 35, 40, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		return gridBagLayout;
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public int getAwayGoals() {
		return awayGoals;
	}

	protected void copyDataFromGuiToFields() {
		String homeGoalsStr = homeGoalsField.getText();
		String awayGoalsStr = awayGoalsField.getText();
		homeGoals = getStoredGoals(homeGoalsStr);
		awayGoals = getStoredGoals(awayGoalsStr);
	}

    private static String getDisplayedGoals(int storedGoals) {
		return storedGoals == -1 ? "" : Integer.toString(storedGoals);
	}

	private static int getStoredGoals(String displayedGoals) {
		int result = -1;
		try {
			result = Integer.parseInt(displayedGoals);
		} catch (NumberFormatException nfe) {
			// intentionally ignore this error
		}
		return result;
	}

}
