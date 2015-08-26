package cz.mfanta.tip_centrum.entity;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResultTest {

    @Test
    public void isHomeWinWorksForHomeWin() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 0);
        // assert
        assertTrue(result.isHomeWin());
    }

    @Test
    public void isHomeWinWorksForDraw() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 1);
        // assert
        assertFalse(result.isHomeWin());
    }

    @Test
    public void isHomeWinWorksForAwayWin() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 2);
        // assert
        assertFalse(result.isHomeWin());
    }

    @Test
    public void isDrawWorksForHomeWin() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 0);
        // assert
        assertFalse(result.isDraw());
    }

    @Test
    public void isDrawWorksForDraw() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 1);
        // assert
        assertTrue(result.isDraw());
    }

    @Test
    public void isDrawWorksForAwayWin() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 2);
        // assert
        assertFalse(result.isDraw());
    }

    @Test
    public void isAwayWinWorksForHomeWin() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 0);
        // assert
        assertFalse(result.isAwayWin());
    }

    @Test
    public void isAwayWinWorksForDraw() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 1);
        // assert
        assertFalse(result.isAwayWin());
    }

    @Test
    public void isAwayWinWorksForAwayWin() throws Exception {
        // arrange
        Result result = new Result(0L, 1, 2);
        // assert
        assertTrue(result.isAwayWin());
    }
}