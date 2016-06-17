package cz.mfanta.tip_centrum.service.gui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cz.mfanta.tip_centrum.service.event.FixtureModelRefreshedEvent;
import cz.mfanta.tip_centrum.service.resource.ResourceManager;
import cz.mfanta.tip_centrum.view.listeners.EventBusTableRowSelectionListener;
import cz.mfanta.tip_centrum.view.listeners.HighlightingTableRowSelectionListener;
import cz.mfanta.tip_centrum.view.model.FixtureTableDesign;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.render.PredictionCellRenderer;
import cz.mfanta.tip_centrum.view.render.ResultCellRenderer;
import cz.mfanta.tip_centrum.view.render.TeamCellRenderer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static cz.mfanta.tip_centrum.service.gui.GuiObjects.FIXTURE_TABLE_BODY_FONT;
import static cz.mfanta.tip_centrum.service.gui.GuiObjects.FIXTURE_TABLE_HEADER_FONT;

public class FixtureTableWrapper {

    private final EventBus eventBus;

    private final FixtureTableModel fixtureTableModel;

    private final ResourceManager resourceManager;

    private final ResultCellRenderer resultCellRenderer;

    private final TeamCellRenderer teamCellRenderer;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private JTable fixtureTable;

    @Builder
    private FixtureTableWrapper(
            EventBus eventBus,
            FixtureTableModel fixtureTableModel,
            ResourceManager resourceManager,
            ResultCellRenderer resultCellRenderer,
            TeamCellRenderer teamCellRenderer
    ) {
        this.eventBus = eventBus;
        this.fixtureTableModel = fixtureTableModel;
        this.resourceManager = resourceManager;
        this.resultCellRenderer = resultCellRenderer;
        this.teamCellRenderer = teamCellRenderer;
        eventBus.register(this);
    }

    @Subscribe
    public void handleFixtureModelRefresh(FixtureModelRefreshedEvent event) {
        scrollToEnd();
    }

    void create() {
        fixtureTable = new JTable(fixtureTableModel);
        fixtureTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fixtureTable.setAutoCreateRowSorter(true);
        // sort the table ascending by date and secondary ascending by time
        fixtureTable.getRowSorter().setSortKeys(
                Arrays.asList(new RowSorter.SortKey(FixtureTableDesign.DATE_COLUMN_INDEX, SortOrder.ASCENDING),
                        new RowSorter.SortKey(FixtureTableDesign.TIME_COLUMN_INDEX, SortOrder.ASCENDING),
                        new RowSorter.SortKey(FixtureTableDesign.HOME_TEAM_COLUMN_INDEX, SortOrder.ASCENDING)));
        final Font tableHeaderFont = resourceManager.getFont(FIXTURE_TABLE_HEADER_FONT);
        final JTableHeader header = fixtureTable.getTableHeader();
        header.setFont(tableHeaderFont);
        final Font tableFont = resourceManager.getFont(FIXTURE_TABLE_BODY_FONT);
        fixtureTable.setFont(tableFont);
        // disable default Enter behavior which is move cell focus one position down
        fixtureTable.getInputMap(JInternalFrame.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent
                .VK_ENTER, 0), "none");
        fixtureTable.getInputMap(JInternalFrame.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                "none");
        fixtureTable.getColumn(FixtureTableDesign.COLUMN_HOME_TEAM).setCellRenderer(teamCellRenderer);
        fixtureTable.getColumn(FixtureTableDesign.COLUMN_AWAY_TEAM).setCellRenderer(teamCellRenderer);
        final TableColumn predictionColumn = fixtureTable.getColumn(FixtureTableDesign.COLUMN_PREDICTION);
        predictionColumn.setCellRenderer(new PredictionCellRenderer(fixtureTableModel));
        final TableColumn resultColumn = fixtureTable.getColumn(FixtureTableDesign.COLUMN_RESULT);
        resultColumn.setCellRenderer(resultCellRenderer);
        final ListSelectionModel fixtureTableSelectionModel = fixtureTable.getSelectionModel();
        fixtureTableSelectionModel.addListSelectionListener(new HighlightingTableRowSelectionListener(fixtureTable, teamCellRenderer));
        addEventGeneratingSelectionListener();
        final TableColumnModel columnModel = fixtureTable.getColumnModel();
        // set the preferred column widths
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(FixtureTableDesign.COLUMN_WIDTHS[i]);
        }
        fixtureTableModel.reload();
    }

    private void scrollToEnd() {
        fixtureTable.scrollRectToVisible(fixtureTable.getCellRect(fixtureTableModel.getRowCount() - 1, 0, true));
    }

    private void addEventGeneratingSelectionListener() {
        fixtureTable
                .getSelectionModel()
                .addListSelectionListener(
                    EventBusTableRowSelectionListener.builder()
                            .fixtureTable(fixtureTable)
                            .fixtureTableModel(fixtureTableModel)
                            .eventBus(eventBus)
                            .build()
                );
    }
}
