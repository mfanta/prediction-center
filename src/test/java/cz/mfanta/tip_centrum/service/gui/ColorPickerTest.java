package cz.mfanta.tip_centrum.service.gui;

import cz.mfanta.tip_centrum.entity.Result;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class ColorPickerTest {

    @Mock
    private Result resultMock;

    private ColorPicker colorPicker;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(resultMock.isHomeWin()).thenReturn(false);
        when(resultMock.isDraw()).thenReturn(false);
        when(resultMock.isAwayWin()).thenReturn(false);

        colorPicker = new ColorPicker();
    }

    @Test
    public void picksCorrectHomeWinColor() throws Exception {
        // Arrange
        when(resultMock.isHomeWin()).thenReturn(true);
        // Act
        Color pickedColor = colorPicker.pickResultColor(resultMock);
        // Assert
        assertThat(pickedColor, is(Colors.HOME_WIN_BG_COLOR));
    }

    @Test
    public void picksCorrectDrawColor() throws Exception {
        // Arrange
        when(resultMock.isDraw()).thenReturn(true);
        // Act
        Color pickedColor = colorPicker.pickResultColor(resultMock);
        // Assert
        assertThat(pickedColor, is(Colors.DRAW_BG_COLOR));
    }

    @Test
    public void picksCorrectAwayWinColor() throws Exception {
        // Arrange
        when(resultMock.isAwayWin()).thenReturn(true);
        // Act
        Color pickedColor = colorPicker.pickResultColor(resultMock);
        // Assert
        assertThat(pickedColor, is(Colors.AWAY_WIN_BG_COLOR));
    }
}