package cz.mfanta.tip_centrum.entity.stats;

import cz.mfanta.tip_centrum.entity.Fixture;
import cz.mfanta.tip_centrum.entity.FixtureGroup;
import cz.mfanta.tip_centrum.entity.Prediction;
import cz.mfanta.tip_centrum.entity.Result;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimplePredictionStatsTest {

    @Test
    public void emptyPredictionsNotCounted() throws Exception {
        // prepare data
        FixtureGroup fixtures = new FixtureGroup();
        fixtures.addFixture(fixtureWithoutPrediction());
        // create stats
        SimplePredictionStats stats = new SimplePredictionStats(fixtures);
        // test
        assertThat(stats.getMatchCount(), is(0));

    }

    private Fixture fixtureWithoutPrediction() {
        return new Fixture(
                    45L,
                    "",
                    null,
                    null,
                    new Date(),
                    null,
                    new Prediction(45L, -1, -1),
                    new Result(45L, 1, 2)
            );
    }
}