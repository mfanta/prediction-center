package cz.mfanta.tip_centrum.view.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.entity.stats.CompletePredictionStats;
import cz.mfanta.tip_centrum.service.event.FixturesSelectedEvent;
import cz.mfanta.tip_centrum.service.event.StatsModelRefreshedEvent;
import cz.mfanta.tip_centrum.service.stats.StatsService;
import lombok.Builder;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;

public class StatsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6902519743018556408L;
	
	private final StatsService statsService;
	
	private final IFixtureManager fixtureManager;

    private final EventBus eventBus;

    @Builder
    private StatsTableModel(
            StatsService statsService,
            IFixtureManager fixtureManager,
            EventBus eventBus
    ) {
        this.statsService = statsService;
        this.fixtureManager = fixtureManager;
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    private CompletePredictionStats stats;

    @Subscribe
    public void handleFixtureSelection(FixturesSelectedEvent fixturesSelectedEvent) {
        setSelectedFixtures(fixturesSelectedEvent.getSelectedFixtures());
        eventBus.post(new StatsModelRefreshedEvent());
    }

	@Override
	public int getRowCount() {
		return StatsTableDesign.ROW_COUNT;
	}

	@Override
	public int getColumnCount() {
		return StatsTableDesign.COLUMN_COUNT;
	}
	
	private void setSelectedFixtures(IFixtureGroup selectedFixtures) {
		IFixtureGroup fixturesForStats = selectedFixtures;
		if (selectedFixtures == null) {
			fixturesForStats = fixtureManager.getAllFixtures();
		}
		updateStats(fixturesForStats);
	}
	
	private void updateStats(IFixtureGroup fixtures) {
		stats = statsService.getStats(fixtures);
	}

	@Override
	public Object getValueAt(int row, int column) {
		// initial update
		if (stats == null) {
			updateStats(fixtureManager.getAllFixtures());
		}
		Object value = null;
		switch (column) {
			case StatsTableDesign.HEADER_COLUMN_INDEX: {
				value = StatsTableDesign.HEADER_NAMES[row];
				break;
			}
			case StatsTableDesign.DATA_COLUMN_INDEX: {
				switch (row) {
					case StatsTableDesign.RESOLVED_MATCHES_ROW_INDEX: {
						value = stats.getMatchCount();
						break;
					}
					case StatsTableDesign.EXACT_PREDICTION_COUNT_ROW_INDEX:
						value = stats.getExactPredictionCount();
						break;
					case StatsTableDesign.EXACT_PREDICTION_PERCENTAGE_ROW_INDEX: {
						value = getDisplayedPercentage(stats.getExactPredictionPercentage());
						break;
					}
					case StatsTableDesign.CORRECT_PREDICTION_COUNT_ROW_INDEX: {
						value = stats.getCorrectPredictionCount();
						break;
					}
					case StatsTableDesign.CORRECT_PREDICTION_PERCENTAGE_ROW_INDEX: {
						value = getDisplayedPercentage(stats.getPercentage());
						break;
					}
					case StatsTableDesign.BALANCE_ROW_INDEX: {
						value = (double) stats.getBalance() / 1000;
						break;
					}
					case StatsTableDesign.BALANCE_PERCENTAGE_ROW_INDEX: {
						value = getDisplayedPercentage(stats.getOddsBasedPercentage());
						break;
					}
					default: {
						throw new RuntimeException("Invalid column index.");
					}
				}
			}
		}
		return value;
	}

    private String getDisplayedPercentage(int percentage) {
        return new DecimalFormat(StatsTableDesign.PERCENTAGE_NUMBER_FORMAT).format(percentage / 100.0);
    }

}
