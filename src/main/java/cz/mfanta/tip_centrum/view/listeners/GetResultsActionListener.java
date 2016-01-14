package cz.mfanta.tip_centrum.view.listeners;

import cz.mfanta.tip_centrum.service.gui.GuiService;
import cz.mfanta.tip_centrum.service.gui.MainFrameListener;
import cz.mfanta.tip_centrum.service.gui.MainWindowCreator;
import cz.mfanta.tip_centrum.service.result.IResultService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetResultsActionListener implements ActionListener, MainFrameListener {

	private final IResultService resultService;

	private final GuiService guiService;

    private final MainWindowCreator mainWindowCreator;

    public GetResultsActionListener(
            IResultService resultService,
            GuiService guiService,
            MainWindowCreator mainWindowCreator
    ) {
        this.resultService = resultService;
        this.guiService = guiService;
        this.mainWindowCreator = mainWindowCreator;
        mainWindowCreator.addMainFrameListener(this);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		resultService.updateAllResults();
		guiService.updateFixtures();
	}

    @Override
    public void mainFrameCreated() {
        mainWindowCreator.getGetResultsMenuItem().addActionListener(this);
    }
}
