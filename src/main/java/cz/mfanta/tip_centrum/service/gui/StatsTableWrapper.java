package cz.mfanta.tip_centrum.service.gui;

import com.google.common.eventbus.Subscribe;
import cz.mfanta.tip_centrum.service.event.StatsModelRefreshedEvent;
import cz.mfanta.tip_centrum.view.model.StatsTableDesign;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumnModel;

@Builder
class StatsTableWrapper {

    private final StatsTableModel statsTableModel;

    @Getter
    private JTable statsTable;

    @Subscribe
    public void handleModelRefresh(StatsModelRefreshedEvent event) {
        statsTable.tableChanged(new TableModelEvent(
                statsTableModel,
                0,
                statsTableModel.getRowCount() - 1
        ));
    }

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
