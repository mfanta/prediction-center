package cz.mfanta.tip_centrum.view.listeners;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.FixtureGroup;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import cz.mfanta.tip_centrum.view.model.StatsTableModel;

public class StatsUpdatingTableRowSelectionListener implements ListSelectionListener {
	
	private final JTable fixtureTable;
	
	private final JTable statsTable;
	
	private final StatsTableModel statsTableModel;
	
	private final FixtureTableModel fixtureTableModel;
	
	public StatsUpdatingTableRowSelectionListener(JTable fixtureTable, JTable statsTable) {
		this.fixtureTable = fixtureTable;
		this.statsTable = statsTable;
		this.fixtureTableModel = (FixtureTableModel) fixtureTable.getModel();
		this.statsTableModel = (StatsTableModel) statsTable.getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			FixtureGroup selectedFixtures = null;
			final int[] selectedRows = fixtureTable.getSelectedRows();
			if (selectedRows != null) {
				selectedFixtures = new FixtureGroup();
				for (int index = 0; index < selectedRows.length; index++) {
					final Fixture fixture = 
							fixtureTableModel.getFixture(fixtureTable.convertRowIndexToModel(selectedRows[index]));
					selectedFixtures.addFixture(fixture);
				}
			}
			statsTableModel.setSelectedFixtures(selectedFixtures);
			statsTable.tableChanged(new TableModelEvent(statsTableModel, 0, statsTableModel.getRowCount() - 1));
		}
	}

}
