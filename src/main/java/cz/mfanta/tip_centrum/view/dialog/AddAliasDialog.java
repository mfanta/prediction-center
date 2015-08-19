package cz.mfanta.tip_centrum.view.dialog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import com.google.common.base.Strings;
import cz.mfanta.tip_centrum.view.listeners.OkCancelHandlingActionListener;

public class AddAliasDialog extends CommonDialog {

	private String name;

	private String alias;

	private JTextField nameField;

	private JTextField aliasField;

	private JButton okButton;

	public AddAliasDialog(Frame owner, String title) {
		super(owner, title);
		final Container contentPane = getContentPane();
		setupLayout(contentPane);
		addLabelToGridBagContainer(CommonDialogDesign.NAME_LABEL_LABEL, 0, 0, contentPane);
		final DocumentListener documentListener = new NameOrAliasTextChangedListener();
		createNameField(contentPane, documentListener);
		createAliasField(contentPane, documentListener);
		okButton = createOkButton(contentPane, 0, 2);
		enableOkButton(false);
		final JButton cancelButton = createCancelButton(contentPane, 1, 2);
		final OkCancelHandlingActionListener buttonListener = new OkCancelHandlingActionListener(this);
		okButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);
	}

	private void createAliasField(Container contentPane, DocumentListener documentListener) {
		aliasField = new JTextField(20);
		addTextToGridBagContainer(aliasField, 1, 1, contentPane);
		aliasField.getDocument().addDocumentListener(documentListener);
	}

	private void createNameField(Container contentPane, DocumentListener documentListener) {
		nameField = new JTextField(20);
		addTextToGridBagContainer(nameField, 1, 0, contentPane);
		addLabelToGridBagContainer(CommonDialogDesign.ALIAS_LABEL_LABEL, 0, 1, contentPane);
		nameField.getDocument().addDocumentListener(documentListener);
	}

	protected GridBagLayout createLayout() {
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 130, 130, 0};
		gridBagLayout.rowHeights = new int[] { 35, 35, 40, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE};
		return gridBagLayout;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	protected void copyDataFromGuiToFields() {
		name = nameField.getText();
		alias = aliasField.getText();
	}

	private boolean canCommit() {
		return !Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(alias);
	}

	private void enableOkButton(boolean enable) {
		okButton.setEnabled(enable);
	}

	private class NameOrAliasTextChangedListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateState();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateState();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			updateState();
		}

		private void updateState() {
			copyDataFromGuiToFields();
			enableOkButton(canCommit());
		}
	}
}
