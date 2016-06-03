package cz.mfanta.tip_centrum.service.gui;

import cz.mfanta.tip_centrum.view.model.StatsTableDesign;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

@Builder
class StatsTableWrapper {

    private final StatsTableModel statsTableModel;

    @Getter
    private JTable statsTable;

    void create() {
        statsTable = new JTable(statsTableModel);
        statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = statsTable.getColumnModel();
        // set the preferred column widths
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(StatsTableDesign.COLUMN_WIDTHS[i]);
        }
    }
}
