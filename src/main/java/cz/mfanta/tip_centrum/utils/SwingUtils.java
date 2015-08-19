package cz.mfanta.tip_centrum.utils;

import java.awt.Point;

import javax.swing.JTable;

import cz.mfanta.tip_centrum.entity.common.Pair;

public class SwingUtils {

	public static Pair<Integer, Integer> getTableCellFromCoordinates(JTable table, Point point) {
		int row = table.rowAtPoint(point);
		int column = table.columnAtPoint(point);
		Pair<Integer, Integer> result = new Pair<Integer, Integer>(row, column);
		return result;
	}

	public static Pair<Integer, Integer> getModelCellFromTableCell(JTable table, Pair<Integer, Integer> tableCell) {
		int modelRow = table.convertRowIndexToModel(tableCell.getFirst());
		int modelColumn = table.convertColumnIndexToModel(tableCell.getSecond());
		Pair<Integer, Integer> modelCell = new Pair<Integer, Integer>(modelRow, modelColumn);
		return modelCell;
	}

}
