package cz.mfanta.tip_centrum.view.model;

import com.google.common.eventbus.EventBus;
import cz.mfanta.tip_centrum.entity.*;
import cz.mfanta.tip_centrum.entity.common.Pair;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.service.format.FormatService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;

import javax.swing.table.AbstractTableModel;
import java.util.Date;

import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.AWAY_ODDS_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.AWAY_TEAM_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.COLUMNS;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.COLUMN_COUNT;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.COMPETITION_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.DATE_COLUMN_FORMAT;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.DATE_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.DRAW_ODDS_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.HOME_ODDS_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.HOME_TEAM_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.ODDS_COLUMN_FORMAT;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.PREDICTION_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.RESULT_COLUMN_INDEX;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.TIME_COLUMN_FORMAT;
import static cz.mfanta.tip_centrum.view.model.FixtureTableDesign.TIME_COLUMN_INDEX;

@Slf4j
@Builder
public class FixtureTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final FormatService formatService;
	
	private final IFixtureManager fixtureManager;
	
	private final PredictionRenderer predictionRenderer;
	
	private final ResultRenderer resultRenderer;

	private final AsyncTaskExecutor taskScheduler;

	private final EventBus eventBus;

	private IFixtureGroup fixtures = new EmptyFixtureGroup();

	public void reload() {
        taskScheduler.submit(reloadRunnable());
	}

	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	public int getRowCount() {
		return fixtures.getCount();
	}

	public Object getValueAt(int row, int column) {
		Fixture fixture = getFixture(row);
		Object result;
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
			result = formatService.formatDate(date, DATE_COLUMN_FORMAT);
			break;
		}
		case TIME_COLUMN_INDEX: {
			Date date = fixture.getDate();
			result = formatService.formatDate(date, TIME_COLUMN_FORMAT);
			break;
		}
		case HOME_TEAM_COLUMN_INDEX: {
			Team homeTeam = fixture.getHomeTeam();
            result = homeTeam.getName();
			break;
		}
		case AWAY_TEAM_COLUMN_INDEX: {
			Team awayTeam = fixture.getAwayTeam();
			result = awayTeam.getName();
			break;
		}
		case HOME_ODDS_COLUMN_INDEX: {
			Odds odds = fixture.getOdds();
			double homeOdds = odds.getHomeOdds() / 1000d;
			result = formatService.formatDouble(homeOdds, ODDS_COLUMN_FORMAT);
			break;
		}
		case DRAW_ODDS_COLUMN_INDEX: {
			Odds odds = fixture.getOdds();
			double drawOdds = odds.getDrawOdds() / 1000d;
			result = formatService.formatDouble(drawOdds, ODDS_COLUMN_FORMAT);
			break;
		}
		case AWAY_ODDS_COLUMN_INDEX: {
			Odds odds = fixture.getOdds();
			double awayOdds = odds.getAwayOdds() / 1000d;
			result = formatService.formatDouble(awayOdds, ODDS_COLUMN_FORMAT);
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
	 * Retrieve the fixture corresponding to the given row in the fixture table.
	 *
	 * @param row The index of the row to retrieve fixtures for.
	 * @return The fixture corresponding to the given row.
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

    private Runnable reloadRunnable() {
        return () -> {
            fixtures = fixtureManager.getAllFixtures();
            fireTableDataChanged();
        };
    }
}
