package cz.mfanta.tip_centrum.service.gui;

import com.google.common.eventbus.EventBus;
import cz.mfanta.tip_centrum.service.event.StatsModelRefreshedEvent;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatsTableWrapperTest {

    @Test
    public void statsAreUpdatedOnModelChange() throws Exception {
        // prepare
        EventBus eventBus = new EventBus();
        StatsTableModel tableModel = mock(StatsTableModel.class);
        when(tableModel.getRowCount()).thenReturn(5);
        JTable statsTable = mock(JTable.class);
        StatsTableWrapper wrapperUT = StatsTableWrapper.builder()
                .statsTableModel(tableModel)
                .eventBus(eventBus)
                .build();
        wrapperUT.setStatsTable(statsTable);
        // execute
        eventBus.post(new StatsModelRefreshedEvent());
        // verify
        // Must use ArgumentCaptor, as TableModelEvent does not implement equals()
        ArgumentCaptor<TableModelEvent> captor = ArgumentCaptor.forClass(TableModelEvent.class);
        verify(statsTable).tableChanged(captor.capture());
        TableModelEvent capturedEvent = captor.getValue();
        assertThat(capturedEvent.getSource(), is(tableModel));
        assertThat(capturedEvent.getFirstRow(), is(0));
        assertThat(capturedEvent.getLastRow(), is(4));
    }
}