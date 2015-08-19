package cz.mfanta.tip_centrum.view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import cz.mfanta.tip_centrum.view.handlers.IOkCancelHandler;

public class OkCancelHandlingActionListener implements ActionListener {

	private IOkCancelHandler handler;

	public OkCancelHandlingActionListener(IOkCancelHandler handler) {
		this.handler = handler;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eventSource = e.getSource();
		if (eventSource instanceof JButton) {
			JButton sourceButton = (JButton) eventSource;
			String buttonText = sourceButton.getText();
			if ("ok".equalsIgnoreCase(buttonText)) {
				handler.onOk();
			} else if ("cancel".equalsIgnoreCase(buttonText)) {
				handler.onCancel();
			}
		}
	}

}
