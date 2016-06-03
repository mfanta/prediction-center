package cz.mfanta.tip_centrum.view.listeners;

import com.google.common.eventbus.EventBus;
import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.FixtureGroup;
import cz.mfanta.tip_centrum.service.event.FixturesSelectedEvent;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import lombok.Builder;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Builder
public class EventBusTableRowSelectionListener implements ListSelectionListener {

    private final JTable fixtureTable;

    private final FixtureTableModel fixtureTableModel;

    private final EventBus eventBus;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            FixtureGroup selectedFixtures = getSelectedFixtures();
            FixturesSelectedEvent event = FixturesSelectedEvent.builder()
                    .selectedFixtures(selectedFixtures)
                    .build();
            eventBus.post(event);
        }
    }

    private FixtureGroup getSelectedFixtures() {
        FixtureGroup selectedFixtures = null;
        int[] selectedRows = fixtureTable.getSelectedRows();
        if (selectedRows != null) {
            selectedFixtures = new FixtureGroup();
            for (int selectedRow : selectedRows) {
                int modelIndex = fixtureTable.convertRowIndexToModel(selectedRow);
                Fixture fixture = fixtureTableModel.getFixture(modelIndex);
                selectedFixtures.addFixture(fixture);
            }
        }
        return selectedFixtures;
    }
}
