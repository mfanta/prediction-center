package cz.mfanta.tip_centrum.view.listeners;

import com.google.common.eventbus.EventBus;
import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.FixtureGroup;
import cz.mfanta.tip_centrum.service.event.FixturesSelectedEvent;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import org.junit.Test;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventBusTableRowSelectionListenerTest {

    @Test
    public void eventGeneratedOnEvent() throws Exception {
        // prepare
        EventBus eventBus = mock(EventBus.class);
        JTable fixtureTableMock = mock(JTable.class);
        int[] selectedRows = new int[1];
        Arrays.fill(selectedRows, 5);
        when(fixtureTableMock.getSelectedRows()).thenReturn(selectedRows);
        when(fixtureTableMock.convertRowIndexToModel(5)).thenReturn(10);
        FixtureTableModel fixtureTableModelMock = mock(FixtureTableModel.class);
        Fixture fixtureMock = mock(Fixture.class);
        when(fixtureTableModelMock.getFixture(10)).thenReturn(fixtureMock);
        EventBusTableRowSelectionListener listenerUT =
                EventBusTableRowSelectionListener.builder()
                .eventBus(eventBus)
                .fixtureTable(fixtureTableMock)
                .fixtureTableModel(fixtureTableModelMock)
                .build();
        // act
        ListSelectionEvent selectionEventMock = mock(ListSelectionEvent.class);
        when(selectionEventMock.getValueIsAdjusting()).thenReturn(false);
        listenerUT.valueChanged(selectionEventMock);
        // assert
        FixtureGroup fixtureGroup = new FixtureGroup();
        fixtureGroup.addFixture(fixtureMock);
        FixturesSelectedEvent expectedEvent = FixturesSelectedEvent.builder()
                .selectedFixtures(fixtureGroup)
                .build();
        verify(eventBus).post(expectedEvent);
    }
}