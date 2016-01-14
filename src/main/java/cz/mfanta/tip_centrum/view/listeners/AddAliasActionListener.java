package cz.mfanta.tip_centrum.view.listeners;

import cz.mfanta.tip_centrum.service.gui.MainFrameListener;
import cz.mfanta.tip_centrum.service.gui.MainWindowCreator;
import cz.mfanta.tip_centrum.view.action.ActionPerformer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAliasActionListener implements ActionListener, MainFrameListener {

 	private final ActionPerformer actionPerformer;

    private final MainWindowCreator mainWindowCreator;

	public AddAliasActionListener(
			ActionPerformer actionPerformer,
			MainWindowCreator mainWindowCreator
	) {
		this.actionPerformer = actionPerformer;
        this.mainWindowCreator = mainWindowCreator;
        mainWindowCreator.addMainFrameListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		actionPerformer.performAddAliasAction();
	}

    @Override
    public void mainFrameCreated() {
        mainWindowCreator.getAddAliasMenuItem().addActionListener(this);
    }
}
