/**
 * 
 */
package systemhealth.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import systemhealth.job.SystemHealthAnalyzer;

/**
 * Singleton class that manages the execution of SystemHealthAnalyzer jobs.
 * 
 * @author 1062992
 *
 */
public class SystemHealthMgr {

    private ExecutorService executor;

    private static int numThreads = 5; // TODO - externalize this (put in
                                       // properties file)

    private static SystemHealthMgr instance;

    private SystemHealthMgr() {
        executor = Executors.newFixedThreadPool(numThreads);
    }

    /**
     * Get the singleton instance of SystemHealthMgr.
     * 
     * @return the SystemHealthMgr instance.
     */
    public synchronized static SystemHealthMgr getInstance() {
        if (instance == null) {
            instance = new SystemHealthMgr();
        }

        return instance;
    }

    /**
     * Submit a runnable job, {@link SystemHealthAnalyzer} job to be executed by
     * thread pool.
     * 
     * @param job
     *            the Runnable to execute.
     */
    public void submitJob(SystemHealthAnalyzer job) {
        executor.execute(job);
    }
}
