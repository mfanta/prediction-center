package cz.mfanta.tip_centrum.view.model;

import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.*;

import java.util.Date;

import javax.swing.table.AbstractTableModel;

import cz.mfanta.tip_centrum.service.log.LogService;
import cz.mfanta.tip_centrum.service.log.Severity;
import cz.mfanta.tip_centrum.view.render.PredictionRenderer;
import cz.mfanta.tip_centrum.view.render.ResultRenderer;
import org.springframework.beans.factory.annotation.Autowired;

import cz.mfanta.tip_centrum.entity.*;
import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.service.IService;
import cz.mfanta.tip_centrum.service.ServiceException;
import cz.mfanta.tip_centrum.service.format.FormatService;
import org.springframework.stereotype.Component;

@Component
public class FixtureTableModel extends AbstractTableModel implements IService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private LogService logService;

	@Autowired
	private FormatService formatService;
	
	@Autowired
	private IFixtureManager fixtureManager;
	
	@Autowired
	private PredictionRenderer predictionRenderer;
	
	@Autowired
	private ResultRenderer resultRenderer;

	private IFixtureGroup fixtures;

	public void reload() {
		try {
			start();
			fireTableDataChanged();
		} catch (ServiceException se) {
			logService.logException(se, Severity.WRN, "Failed to reload fixtures.");
		}
	}

	@Override
	public void start() throws ServiceException {
		fixtures = fixtureManager.getAllFixtures();
	}

	@Override
	public void stop() throws ServiceException {
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	@Override
	public int getRowCount() {
		return fixtures.getCount();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Fixture fixture = getFixture(row);
		Object result = null;
		switch (column) {
        case COMPETITION_COLUMN_INDEX: {
            result  = fixture.getCompetitionName();
            if (result == null) {
                result = "";
            }
            break;
        }
		case DATE_COLUMN_INDEX: {
			Date date = fixture.getDate();
			String dateStr = formatService.formatDate(date, DATE_COLUMN_FORMAT);
			result = dateStr;
			break;
		}
		case TIME_COLUMN_INDEX: {
			Date date = fixture.getDate();
			String timeStr = formatService.formatDate(date, TIME_COLUMN_FORMAT);
			result = timeStr;
			break;
		}
		case HOME_TEAM_COLUMN_INDEX: {
			Team homeTeam = fixture.getHomeTeam();
			String homeTeamName = homeTeam.getName();
			result = homeTeamName;
			break;
		}
		case AWAY_TEAM_COLUMN_INDEX: {
			Team awayTeam = fixture.getAwayTeam();
			String awayTeamName = awayTeam.getName();
			result = awayTeamName;
			break;
		}
		case HOME_ODDS_COLUMN_INDEX: {
			Odds odds = fixture.getOdds();
			double homeOdds = odds.getHomeOdds() / 1000d;
			String homeOddsStr = formatService.formatDouble(homeOdds, ODDS_COLUMN_FORMAT);
			result = homeOddsStr;
			break;
		}
		case DRAW_ODDS_COLUMN_INDEX: {
			Odds odds = fixture.getOdds();
			double drawOdds = odds.getDrawOdds() / 1000d;
			String drawOddsStr = formatService.formatDouble(drawOdds, ODDS_COLUMN_FORMAT);
			result = drawOddsStr;
			break;
		}
		case AWAY_ODDS_COLUMN_INDEX: {
			Odds odds = fixture.getOdds();
			double awayOdds = odds.getAwayOdds() / 1000d;
			String awayOddsStr = formatService.formatDouble(awayOdds, ODDS_COLUMN_FORMAT);
			result = awayOddsStr;
			break;
		}
		case PREDICTION_COLUMN_INDEX: {
			result = "";
			Prediction prediction = fixture.getPrediction();
			if (prediction != null) {
				result = predictionRenderer.renderPrediction(prediction);
			}
			break;
		}
		case RESULT_COLUMN_INDEX: {
			result = resultRenderer.renderResult(fixture.getResult());
			break;
		}
		default: {
			throw new RuntimeException("Invalid column index.");
		}
		}
		return result;
	}

	/**
	 * @param row
	 * @return
	 */
	public Fixture getFixture(int row) {
		return fixtures.getAt(row);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public static boolean isPredictionCell(Pair<Integer, Integer> cellPosition) {
		return cellPosition.getSecond() == PREDICTION_COLUMN_INDEX;
	}
	
	public static boolean isResultCell(Pair<Integer, Integer> cellPosition) {
		return cellPosition.getSecond() == RESULT_COLUMN_INDEX;
	}

}
