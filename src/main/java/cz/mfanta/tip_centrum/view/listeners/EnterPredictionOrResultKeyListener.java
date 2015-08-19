package cz.mfanta.tip_centrum.view.listeners;

import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.utils.SwingUtils;
import cz.mfanta.tip_centrum.view.action.ActionPerformer;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class EnterPredictionOrResultKeyListener extends KeyAdapter {

    private JTable fixtureTable;

    @Autowired
    private ActionPerformer actionPerformer;

    @Override
    public void keyPressed(KeyEvent e) {
        if (isEnterKey(e.getKeyCode())) {
            final Pair<Integer, Integer> selectedCell = getSingleSelectedModelCell();
            if (selectedCell != null) {
                if (FixtureTableModel.isResultCell(selectedCell)) {
                    actionPerformer.performEditResultAction(selectedCell);
                } else if (FixtureTableModel.isPredictionCell(selectedCell)) {
                    actionPerformer.performEditPredictionAction(selectedCell);
                }
            }
        }
    }

    public void setFixtureTable(JTable fixtureTable) {
        this.fixtureTable = fixtureTable;
    }

    private boolean isEnterKey(int keyCode) {
        return keyCode == KeyEvent.VK_ENTER;
    }

    private Pair<Integer, Integer> getSingleSelectedModelCell() {
        Pair<Integer, Integer> selectedModelCell = null;
        final int[] selectedColumns = fixtureTable.getSelectedColumns();
        final int[] selectedRows = fixtureTable.getSelectedRows();
        if (selectedColumns.length == 1 || selectedRows.length == 1) {
            final Pair<Integer, Integer> tableCell = new Pair<Integer, Integer>(selectedRows[0], selectedColumns[0]);
            selectedModelCell = SwingUtils.getModelCellFromTableCell(fixtureTable, tableCell);
        }
        return selectedModelCell;
    }
}
