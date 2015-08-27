package cz.mfanta.tip_centrum.view.render;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.service.gui.ColorPicker;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;
import lombok.RequiredArgsConstructor;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@RequiredArgsConstructor
public class ResultCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -7451218506658141398L;

    private final FixtureTableModel fixtureTableModel;
    private final ColorPicker colorPicker;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		final Component result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setHorizontalAlignment(SwingConstants.CENTER);
        // set the background color of the result cell depending on the result outcome (home/away win/draw)
        Fixture fixture = fixtureTableModel.getFixture(table.convertRowIndexToModel(row));
        Result fixtureResult = fixture.getResult();
        Color bgColor = colorPicker.pickResultColor(fixtureResult);
        result.setBackground(bgColor);
		return result;
	}

}
