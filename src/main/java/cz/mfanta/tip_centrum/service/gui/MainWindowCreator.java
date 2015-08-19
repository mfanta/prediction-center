package cz.mfanta.tip_centrum.service.gui;

import static cz.mfanta.tip_centrum.service.gui.GuiLabels.MAIN_WINDOW_TITLE;
import static cz.mfanta.tip_centrum.service.gui.GuiObjects.FIXTURE_TABLE_BODY_FONT;
import static cz.mfanta.tip_centrum.service.gui.GuiObjects.FIXTURE_TABLE_HEADER_FONT;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import cz.mfanta.tip_centrum.service.resource.ResourceManager;
import cz.mfanta.tip_centrum.view.listeners.*;
import cz.mfanta.tip_centrum.view.model.FixtureTableDesign;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.StatsTableDesign;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import cz.mfanta.tip_centrum.view.render.PredictionCellRenderer;
import cz.mfanta.tip_centrum.view.render.ResultCellRenderer;
import cz.mfanta.tip_centrum.view.render.TeamCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainWindowCreator implements Runnable {

	@Autowired
	private FixtureTableModel fixtureTableModel;

	@Autowired
	private StatsTableModel statsTableModel;

	@Autowired
	private ResourceManager resourceManager;

	@Autowired
	private GuiService guiService;

	@Autowired
	private FixtureTableResultEditor fixtureTableResultEditor;

	@Autowired
	private FixtureTablePredictionUpdater fixtureTablePredictionUpdater;

	@Autowired
	private EnterPredictionOrResultKeyListener enterPredictionOrResultKeyListener;

	@Autowired
	private GetResultsActionListener getResultsActionListener;

	@Autowired
	private AddAliasActionListener addAliasActionListener;

	private JFrame mainFrame;

	@Override
	public void run() {
		mainFrame = new JFrame(MAIN_WINDOW_TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JTable statsTable = createStatsTable();
		final JTable fixtureTable = createFixtureTable(statsTable);
		createMenuBar();
		// using JScrollPane as table's parent makes sure the table header is properly shown
		final JScrollPane tablePane = new JScrollPane(fixtureTable);
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 15;
		layout.setConstraints(tablePane, constraints);
		mainFrame.getContentPane().setLayout(layout);
		mainFrame.getContentPane().add(tablePane);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weighty = 1;
		constraints.weightx = 1;
		layout.setConstraints(statsTable, constraints);
		mainFrame.getContentPane().add(statsTable);
		mainFrame.pack();
		// must be set only after packing the frame
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
		// scroll to the end of fixture table
		fixtureTable.scrollRectToVisible(fixtureTable.getCellRect(fixtureTableModel.getRowCount() - 1, 0, true));
		guiService.setMainFrame(mainFrame);
	}

	private JTable createFixtureTable(JTable statsTable) {
		JTable fixtureTable = new JTable(fixtureTableModel);
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
		final TeamCellRenderer teamCellRenderer = new TeamCellRenderer();
		fixtureTable.getColumn(FixtureTableDesign.COLUMN_HOME_TEAM).setCellRenderer(teamCellRenderer);
		fixtureTable.getColumn(FixtureTableDesign.COLUMN_AWAY_TEAM).setCellRenderer(teamCellRenderer);
		final TableColumn predictionColumn = fixtureTable.getColumn(FixtureTableDesign.COLUMN_PREDICTION);
		predictionColumn.setCellRenderer(new PredictionCellRenderer(fixtureTableModel));
		// predictionColumn.setCellEditor(null);
		final TableColumn resultColumn = fixtureTable.getColumn(FixtureTableDesign.COLUMN_RESULT);
		resultColumn.setCellRenderer(new ResultCellRenderer());
		// resultColumn.setCellEditor(null);
		fixtureTablePredictionUpdater.setFixtureTable(fixtureTable);
		fixtureTable.addMouseListener(fixtureTablePredictionUpdater);
		fixtureTableResultEditor.setFixtureTable(fixtureTable);
		fixtureTable.addMouseListener(fixtureTableResultEditor);
		enterPredictionOrResultKeyListener.setFixtureTable(fixtureTable);
		fixtureTable.addKeyListener(enterPredictionOrResultKeyListener);
		final ListSelectionModel fixtureTableSelectionModel = fixtureTable.getSelectionModel();
		fixtureTableSelectionModel.addListSelectionListener(new HighlightingTableRowSelectionListener(fixtureTable, teamCellRenderer));
		fixtureTableSelectionModel.addListSelectionListener(new StatsUpdatingTableRowSelectionListener(fixtureTable, statsTable));
		final TableColumnModel columnModel = fixtureTable.getColumnModel();
		// set the preferred column widths
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setPreferredWidth(FixtureTableDesign.COLUMN_WIDTHS[i]);
		}
		return fixtureTable;
	}

	private JTable createStatsTable() {
		final JTable statsTable = new JTable(statsTableModel);
		statsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final TableColumnModel columnModel = statsTable.getColumnModel();
		// set the preferred column widths
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setPreferredWidth(StatsTableDesign.COLUMN_WIDTHS[i]);
		}
		return statsTable;
	}

	private void createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		final JMenu actionMenu = new JMenu(GuiLabels.ACTION_MENU_LABEL);
		final JMenuItem getResultsItem = new JMenuItem(GuiLabels.GET_RESULTS_MENU_ITEM_LABEL);
		getResultsItem.setMnemonic(KeyEvent.VK_G);
		getResultsItem.addActionListener(getResultsActionListener);
		actionMenu.add(getResultsItem);
		final JMenuItem addAliasItem = new JMenuItem(GuiLabels.ADD_ALIAS_MENU_ITEM_LABEL);
		addAliasItem.setMnemonic(KeyEvent.VK_A);
		addAliasItem.addActionListener(addAliasActionListener);
		actionMenu.add(addAliasItem);
		menuBar.add(actionMenu);
		mainFrame.setJMenuBar(menuBar);
	}

}
