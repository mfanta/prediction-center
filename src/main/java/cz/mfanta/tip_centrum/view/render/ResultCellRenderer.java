package cz.mfanta.tip_centrum.view.render;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ResultCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -7451218506658141398L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		final Component result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setHorizontalAlignment(SwingConstants.CENTER);
		return result;
	}

}
