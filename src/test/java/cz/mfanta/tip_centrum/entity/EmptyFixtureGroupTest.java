package cz.mfanta.tip_centrum.entity;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmptyFixtureGroupTest {

    private EmptyFixtureGroup emptyFixtureGroup;

    @Before
    public void setUp() throws Exception {
        emptyFixtureGroup = new EmptyFixtureGroup();
    }

    @Test
    public void getCountReturnsZero() throws Exception {
        // act
        int fixtureCount = emptyFixtureGroup.getCount();
        // assert
        assertThat(fixtureCount, is(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void mergeThrowingException() throws Exception {
        // act
        emptyFixtureGroup.merge(null);
        // assert - exception expected
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAtThrowingException() throws Exception {
        // act
        emptyFixtureGroup.getAt(0);
        // assert - exception expected
    }
}