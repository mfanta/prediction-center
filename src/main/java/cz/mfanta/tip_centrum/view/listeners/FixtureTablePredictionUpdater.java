package cz.mfanta.tip_centrum.view.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.utils.AwtUtils;
import cz.mfanta.tip_centrum.utils.SwingUtils;
import cz.mfanta.tip_centrum.view.action.ActionPerformer;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FixtureTablePredictionUpdater extends MouseAdapter {

	private JTable fixtureTable;

    @Autowired
    private ActionPerformer actionPerformer;

    public void setFixtureTable(JTable fixtureTable) {
        this.fixtureTable = fixtureTable;
    }

    @Override
	public void mouseClicked(MouseEvent e) {
		if (AwtUtils.isValidDoubleClick(e)) {
			final Pair<Integer, Integer> tableCell = SwingUtils.getTableCellFromCoordinates(fixtureTable, e.getPoint());
			final Pair<Integer, Integer> modelCell = SwingUtils.getModelCellFromTableCell(fixtureTable, tableCell);
			if (FixtureTableModel.isPredictionCell(modelCell)) {
                actionPerformer.performEditPredictionAction(modelCell);
			}
		}
	}

}
