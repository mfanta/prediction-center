package cz.mfanta.tip_centrum.view.render;

import java.awt.Component;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.google.common.collect.Sets;

import cz.mfanta.tip_centrum.service.gui.Colors;

public class TeamCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 2112326023478010676L;
	
	private Set<String> highlightedTeams;

	public TeamCellRenderer() {
		super();
		this.highlightedTeams = Sets.newHashSet();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		final Component result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (!isSelected && !hasFocus) {
			if (isTeamHighlighted((String) value)) {
				result.setBackground(Colors.HIGHLIGHTED_TEAM_BG_COLOR);
			} else {
				result.setBackground(Colors.NORMAL_TEAM_BG_COLOR);
			}
		}
		return result;
	}
	
	public void setHighlightedTeam(String teamName) {
		highlightedTeams.add(teamName);
	}
	
	public void resetHighlightedTeams() {
		highlightedTeams.clear();
	}
	
	private boolean isTeamHighlighted(String teamName) {
		return highlightedTeams.contains(teamName);
	}

}
