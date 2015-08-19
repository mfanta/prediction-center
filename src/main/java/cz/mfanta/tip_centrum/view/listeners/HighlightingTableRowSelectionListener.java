package cz.mfanta.tip_centrum.view.listeners;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import cz.mfanta.tip_centrum.view.model.FixtureTableDesign;
import cz.mfanta.tip_centrum.view.render.TeamCellRenderer;

public class HighlightingTableRowSelectionListener implements ListSelectionListener {
	
	private TeamCellRenderer teamCellRenderer;
	private JTable fixtureTable;

	public HighlightingTableRowSelectionListener(JTable fixtureTable, TeamCellRenderer teamCellRenderer) {
		super();
		this.fixtureTable = fixtureTable;
		this.teamCellRenderer = teamCellRenderer;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			teamCellRenderer.resetHighlightedTeams();
			final int[] selectedRows = fixtureTable.getSelectedRows();
			if (selectedRows != null) {
				for (int index = 0; index < selectedRows.length; index++) {
					teamCellRenderer.setHighlightedTeam((String) fixtureTable.getValueAt(selectedRows[index], FixtureTableDesign.HOME_TEAM_COLUMN_INDEX));
					teamCellRenderer.setHighlightedTeam((String) fixtureTable.getValueAt(selectedRows[index], FixtureTableDesign.AWAY_TEAM_COLUMN_INDEX));
				}
			}
			final TableModel model = fixtureTable.getModel();
			fixtureTable.tableChanged(new TableModelEvent(model, 0, model.getRowCount() - 1));
		}
	}

}
