package cz.mfanta.tip_centrum.view.render;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.mysql.jdbc.StringUtils;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.Result;
import cz.mfanta.tip_centrum.service.gui.Colors;
import cz.mfanta.tip_centrum.view.model.FixtureTableModel;

public class PredictionCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -1148377533635315699L;
	
	private FixtureTableModel fixtureTableModel;

	public PredictionCellRenderer(FixtureTableModel fixtureTableModel) {
		super();
		this.fixtureTableModel = fixtureTableModel;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		final Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		final Fixture fixture = fixtureTableModel.getFixture(table.convertRowIndexToModel(row));
		Color bgColor;
		if (StringUtils.isNullOrEmpty((String) value)) {
			bgColor = Colors.EMPTY_PREDICTION_BG_COLOR;
		} else {
			final Result result = fixture.getResult();
			if (result == null) {
				bgColor = Colors.NO_RESULT_PREDICTION_BG_COLOR;
			} else {
				final Prediction prediction = fixture.getPrediction();
				if (prediction.isExact(result)){
					bgColor = Colors.EXACT_PREDICTION_BG_COLOR;
				} else if (prediction.isAccurate(result)) {
					bgColor = Colors.ACCURATE_PREDICTION_BG_COLOR;
				} else {
					bgColor = Colors.INACCURATE_PREDICTION_COLOR;
				}
			}
		}
		setHorizontalAlignment(SwingConstants.CENTER);
		setForeground(Colors.PREDICTION_FG_COLOR);
		setBackground(bgColor);
		return comp;
	}

}
