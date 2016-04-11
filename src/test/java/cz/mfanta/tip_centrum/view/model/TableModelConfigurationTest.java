package cz.mfanta.tip_centrum.view.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@ContextConfiguration(
        classes = {
                TableModelConfiguration.class
        })
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TableModelConfigurationTest {

    @Autowired
    private AsyncTaskExecutor taskScheduler;

    @Test
    public void taskSchedulerIsDefined() throws Exception {
        assertThat(taskScheduler, notNullValue());
    }
}