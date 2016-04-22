package cz.mfanta.tip_centrum.view.model;

import cz.mfanta.tip_centrum.entity.EmptyFixtureGroup;
import cz.mfanta.tip_centrum.entity.IFixtureGroup;
import cz.mfanta.tip_centrum.entity.manager.IFixtureManager;
import cz.mfanta.tip_centrum.infrastructure.ThreadPoolConfiguration;
import cz.mfanta.tip_centrum.service.format.FormatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(
        classes = {
                ThreadPoolConfiguration.class
        })
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FixtureTableModelTest {

    @Autowired
    private AsyncTaskExecutor executor;

    @Test
    public void fixtureRetrievalRunsInSeparateThread() throws Exception {
        // prepare
        Thread mainThread = Thread.currentThread();
        final Thread[] fixtureThread = new Thread[1];
        IFixtureManager fixtureManagerMock = mock(IFixtureManager.class);
        when(fixtureManagerMock.getAllFixtures()).then((Answer<IFixtureGroup>) invocation -> {
            try {
                fixtureThread[0] = Thread.currentThread();
                return new EmptyFixtureGroup();
            } finally {
                synchronized (mainThread) {
                    mainThread.notify();
                }
            }
        });
        FixtureTableModel model = new FixtureTableModel(
                mock(FormatService.class),
                fixtureManagerMock,
                mock(PredictionRenderer.class),
                mock(ResultRenderer.class),
                executor
        );

        // run
        model.reload();
        synchronized (mainThread) {
            mainThread.wait();
        }

        // assert
        assertThat(fixtureThread[0], not(is(mainThread)));
    }
}
