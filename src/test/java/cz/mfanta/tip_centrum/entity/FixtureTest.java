package cz.mfanta.tip_centrum.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FixtureTest {

    @Test
    public void emptyPredictionCantBeResolved() throws Exception {
        Fixture fixture = new Fixture();
        fixture.setPrediction(emptyPrediction());
        fixture.setResult(new Result());

        assertThat(fixture.canResolvePrediction(), is(false));
    }

    private Prediction emptyPrediction() {
        return new Prediction(1, -1, -1);
    }
}