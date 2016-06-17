package cz.mfanta.tip_centrum.service.gui;

import com.google.common.eventbus.EventBus;
import cz.mfanta.tip_centrum.service.event.FixtureModelRefreshedEvent;
import cz.mfanta.tip_centrum.service.resource.ResourceManager;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.render.ResultCellRenderer;
import cz.mfanta.tip_centrum.view.render.TeamCellRenderer;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FixtureTableWrapperTest {

    @Test
    public void tableScrolledToEndOnModelRefresh() throws Exception {
        EventBus eventBus = new EventBus();
        FixtureTableModel modelMock = mock(FixtureTableModel.class);
        when(modelMock.getRowCount()).thenReturn(5);
        // prepare
        FixtureTableWrapper wrapper = FixtureTableWrapper.builder()
                .eventBus(eventBus)
                .fixtureTableModel(modelMock)
                .resourceManager(mock(ResourceManager.class))
                .resultCellRenderer(mock(ResultCellRenderer.class))
                .teamCellRenderer(mock(TeamCellRenderer.class))
                .build();
        JTable fixtureTableMock = mock(JTable.class);
        Rectangle rect = new Rectangle(0, 0, 120, 125);
        when(fixtureTableMock.getCellRect(4, 0, true)).thenReturn(rect);
        wrapper.setFixtureTable(fixtureTableMock);
        // run
        eventBus.post(new FixtureModelRefreshedEvent());
        // test
        verify(fixtureTableMock).scrollRectToVisible(rect);
    }
}