package cz.mfanta.tip_centrum.view.listeners;

import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.service.gui.MainFrameListener;
import cz.mfanta.tip_centrum.service.gui.MainWindowCreator;
import cz.mfanta.tip_centrum.utils.AwtUtils;
import cz.mfanta.tip_centrum.utils.SwingUtils;
import cz.mfanta.tip_centrum.view.action.ActionPerformer;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FixtureTablePredictionUpdater extends MouseAdapter implements MainFrameListener {

    private final ActionPerformer actionPerformer;

	private final MainWindowCreator mainWindowCreator;

    public FixtureTablePredictionUpdater(
            ActionPerformer actionPerformer,
            MainWindowCreator mainWindowCreator
    ) {
        this.actionPerformer = actionPerformer;
        this.mainWindowCreator = mainWindowCreator;
        mainWindowCreator.addMainFrameListener(this);
    }

    @Override
	public void mouseClicked(MouseEvent e) {
		if (AwtUtils.isValidDoubleClick(e)) {
			final Pair<Integer, Integer> tableCell =
                    SwingUtils.getTableCellFromCoordinates(getFixtureTable(), e.getPoint());
			final Pair<Integer, Integer> modelCell =
                    SwingUtils.getModelCellFromTableCell(getFixtureTable(), tableCell);
			if (FixtureTableModel.isPredictionCell(modelCell)) {
                actionPerformer.performEditPredictionAction(modelCell);
			}
		}
	}

	@Override
	public void mainFrameCreated() {
        getFixtureTable().addMouseListener(this);
	}

    private JTable getFixtureTable() {
        return mainWindowCreator.getFixtureTable();
    }
}
