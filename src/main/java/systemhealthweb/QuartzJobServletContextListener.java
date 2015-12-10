/**
 *
 */
package systemhealthweb;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import systemhealth.job.ScannerJob;

/**
 * @author 1062992
 *
 */
public class QuartzJobServletContextListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(QuartzJobServletContextListener.class);

    Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {

            // Grab the Scheduler instance from the Factory
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            Date startDate = new Date(System.currentTimeMillis() + (60 * 1000));

            JobDetail job = newJob(ScannerJob.class).withIdentity("scannerjob",
                    "group1").build();

            // trigger every 1 minutes and repeat indefinitely
            SimpleTrigger trigger = newTrigger()
                    .withIdentity("trigger", "group1")
                    .startAt(startDate)
                    .withSchedule(
                            simpleSchedule().withIntervalInMinutes(1)
                                    .repeatForever()).build();

            Date scheduleDate = scheduler.scheduleJob(job, trigger);
            LOGGER.info(job.getKey() + " will run at: " + scheduleDate
                    + " and repeat: " + trigger.getRepeatCount()
                    + " times, every " + trigger.getRepeatInterval() / 1000
                    + " seconds");

            LOGGER.info("------- Starting Scheduler ----------------");

            // All of the jobs have been added to the scheduler, but none of the
            // jobs will run until the scheduler has been started

            // and start it off
            scheduler.start();

            LOGGER.info("------- Started Scheduler -----------------");

        } catch (SchedulerException se) {
            LOGGER.error("Failed to schedule job.", se);

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Context Destroyed");
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOGGER.error("Failed to shutdown scheduler.", e);
        }
    }

}
