package cz.mfanta.tip_centrum.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FixtureTest {

    @Test
    public void emptyPredictionCantBeResolved() throws Exception {
        Fixture fixture = new Fixture();
        fixture.setPrediction(new Prediction(1, -1, -1));
        fixture.setResult(new Result());

        assertThat(fixture.canResolvePrediction(), is(false));
    }
}