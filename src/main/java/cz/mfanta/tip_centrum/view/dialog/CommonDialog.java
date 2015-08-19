package cz.mfanta.tip_centrum.view.dialog;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import cz.mfanta.tip_centrum.view.handlers.IOkCancelHandler;

public abstract class CommonDialog extends JDialog implements IOkCancelHandler {

	private boolean okPressed;

	protected CommonDialog(Frame owner, String title) {
		super(owner, title, true);
		this.okPressed = false;
	}

	public boolean wasOkPressed() {
		return okPressed;
	}

	@Override
	public void onOk() {
		okPressed = true;
		copyDataFromGuiToFields();
		dispose();
	}

	@Override
	public void onCancel() {
		okPressed = false;
		dispose();
	}

	@Override
	protected JRootPane createRootPane() {
		final JRootPane rootPane = super.createRootPane();
		final KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		final CancelDialogAction cancelDialogAction = new CancelDialogAction(this);
		rootPane.registerKeyboardAction(cancelDialogAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

	abstract protected void copyDataFromGuiToFields();

	abstract protected GridBagLayout createLayout();

	protected void setupLayout(Container contentPane) {
		final GridBagLayout gridBagLayout = createLayout();
		contentPane.setLayout(gridBagLayout);
	}

	protected GridBagConstraints addLabelToGridBagContainer(String label, int xPosition, int yPosition, Container container) {
		final JLabel guiLabel = new JLabel(label);
		final GridBagConstraints constraints = new GridBagConstraints();
		setGridConstraintsPosition(xPosition, yPosition, constraints);
		container.add(guiLabel, constraints);
		return constraints;
	}

	protected void setGridConstraintsPosition(int xPosition, int yPosition, GridBagConstraints constraints) {
		constraints.gridx = xPosition;
		constraints.gridy = yPosition;
	}

	protected GridBagConstraints addTextToGridBagContainer(JTextField guiText, int xPosition, int yPosition, Container container) {
		guiText.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints constraints = new GridBagConstraints();
		setGridConstraintsPosition(xPosition, yPosition, constraints);
		constraints.insets = new Insets(0, 0, 0, 10);
		constraints.ipady = 5;
		constraints.ipadx = 10;
		container.add(guiText, constraints);
		return constraints;
	}

	protected JButton createOkButton(Container contentPane, int xPosition, int yPosition) {
		final JButton okButton = createButton(contentPane, xPosition, yPosition, CommonDialogDesign.OK_BUTTON_LABEL);
		// make ok button the default one (consuming Enter keystroke)
		getRootPane().setDefaultButton(okButton);
		return okButton;
	}

	protected JButton createCancelButton(Container contentPane, int xPosition, int yPosition) {
		return createButton(contentPane, xPosition, yPosition, CommonDialogDesign.CANCEL_BUTTON_LABEL);
	}

	private JButton createButton(Container contentPane, int xPosition, int yPosition, String label) {
		final JButton button = new JButton(label);
		button.setBorder(UIManager.getBorder("Button.border"));
		final GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		setGridConstraintsPosition(xPosition, yPosition, gbc_button);
		contentPane.add(button, gbc_button);
		return button;
	}
}
