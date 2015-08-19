package cz.mfanta.tip_centrum.view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cz.mfanta.tip_centrum.view.action.ActionPerformer;
import cz.mfanta.tip_centrum.view.dialog.AddAliasDialog;

@Component
public class AddAliasActionListener implements ActionListener {

	@Autowired
 	private ActionPerformer actionPerformer;

	@Override
	public void actionPerformed(ActionEvent e) {
		actionPerformer.performAddAliasAction();
	}
}
