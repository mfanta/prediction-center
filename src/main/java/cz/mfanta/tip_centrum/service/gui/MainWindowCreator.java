package cz.mfanta.tip_centrum.service.gui;

import cz.mfanta.tip_centrum.view.model.StatsTableDesign;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;
import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static cz.mfanta.tip_centrum.service.gui.GuiLabels.MAIN_WINDOW_TITLE;

public class MainWindowCreator implements Runnable {

	private final StatsTableModel statsTableModel;

	private final FixtureTableWrapper fixtureTableWrapper;

    @Builder
    private MainWindowCreator(
            StatsTableModel statsTableModel,
            FixtureTableWrapper fixtureTableWrapper
    ) {
        this.statsTableModel = statsTableModel;
        this.fixtureTableWrapper = fixtureTableWrapper;
    }

    @Getter
	private JFrame mainFrame;

    private List<MainFrameListener> mainFrameListeners = new ArrayList<>();

	@Getter
	private JMenuItem getResultsMenuItem;

    @Getter
    private JMenuItem addAliasMenuItem;

    public void run() {
		mainFrame = new JFrame(MAIN_WINDOW_TITLE);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		final JTable statsTable = createStatsTable();
		fixtureTableWrapper.create();
		createMenuBar();
		// using JScrollPane as table's parent makes sure the table header is properly shown
		final JScrollPane tablePane = new JScrollPane(fixtureTableWrapper.getFixtureTable());
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
		fireMainFrameCreatedEvent();
	}

	public void addMainFrameListener(MainFrameListener listener) {
        mainFrameListeners.add(listener);
    }

	public JTable getFixtureTable() {
        return fixtureTableWrapper.getFixtureTable();
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
		getResultsMenuItem = new JMenuItem(GuiLabels.GET_RESULTS_MENU_ITEM_LABEL);
		getResultsMenuItem.setMnemonic(KeyEvent.VK_G);
		actionMenu.add(getResultsMenuItem);
        addAliasMenuItem = new JMenuItem(GuiLabels.ADD_ALIAS_MENU_ITEM_LABEL);
		addAliasMenuItem.setMnemonic(KeyEvent.VK_A);
		actionMenu.add(addAliasMenuItem);
		menuBar.add(actionMenu);
		mainFrame.setJMenuBar(menuBar);
	}

    private void fireMainFrameCreatedEvent() {
        mainFrameListeners.forEach(MainFrameListener::mainFrameCreated);
    }
}
