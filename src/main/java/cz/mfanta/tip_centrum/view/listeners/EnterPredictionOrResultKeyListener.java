package cz.mfanta.tip_centrum.view.listeners;

import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.service.gui.MainFrameListener;
import cz.mfanta.tip_centrum.service.gui.MainWindowCreator;
import cz.mfanta.tip_centrum.utils.SwingUtils;
import cz.mfanta.tip_centrum.view.action.ActionPerformer;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterPredictionOrResultKeyListener extends KeyAdapter implements MainFrameListener {

    private final ActionPerformer actionPerformer;

    private final MainWindowCreator mainWindowCreator;

    public EnterPredictionOrResultKeyListener(
            ActionPerformer actionPerformer,
            MainWindowCreator mainWindowCreator
    ) {
        this.actionPerformer = actionPerformer;
        this.mainWindowCreator = mainWindowCreator;
        mainWindowCreator.addMainFrameListener(this);
    }

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

    @Override
    public void mainFrameCreated() {
        mainWindowCreator.getFixtureTable().addKeyListener(this);
    }

    private boolean isEnterKey(int keyCode) {
        return keyCode == KeyEvent.VK_ENTER;
    }

    private Pair<Integer, Integer> getSingleSelectedModelCell() {
        Pair<Integer, Integer> selectedModelCell = null;
        final int[] selectedColumns = getFixtureTable().getSelectedColumns();
        final int[] selectedRows = getFixtureTable().getSelectedRows();
        if (selectedColumns.length == 1 || selectedRows.length == 1) {
            final Pair<Integer, Integer> tableCell = new Pair<>(selectedRows[0], selectedColumns[0]);
            selectedModelCell = SwingUtils.getModelCellFromTableCell(getFixtureTable(), tableCell);
        }
        return selectedModelCell;
    }

    private JTable getFixtureTable() {
        return mainWindowCreator.getFixtureTable();
    }
}
