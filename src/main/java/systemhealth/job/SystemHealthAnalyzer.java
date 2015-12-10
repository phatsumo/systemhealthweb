/**
 *
 */
package systemhealth.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import systemhealth.data.Disk;
import systemhealth.data.DiskThreshold;
import systemhealth.data.ServerHealthStat;
import systemhealth.data.ServerThreshold;
import systemhealth.data.ServerThresholdConfigData;

/**
 * Runnable that checks performance levels for disks and CPU in
 * {@link ServerHealthStat} against the configured thresholds in
 * {@link ServerThresholdConfigData}.
 *
 * @author 1062992
 *
 */
public class SystemHealthAnalyzer implements Runnable {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SystemHealthAnalyzer.class);

    private ServerHealthStat serverHealthStat;

    private ServerThresholdConfigData thresholdData;

    /**
     * Constructor
     *
     * @param stat
     * @param thresholdData
     */
    public SystemHealthAnalyzer(ServerHealthStat stat,
            ServerThresholdConfigData thresholdData) {
        this.serverHealthStat = stat;
        this.thresholdData = thresholdData;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        LOGGER.debug("SystemHealthAnalyzer.run()");

        // check each field
        ServerThreshold serverThreshold = thresholdData
                .findByServerName(serverHealthStat.getServerName());

        if (serverThreshold == null) {
            LOGGER.warn("Threshold configuration not defined for server name: "
                    + serverHealthStat.getServerName());
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hostname: " + serverHealthStat.getServerName()
                + "\n\n");

        boolean needToNotify = false;
        // check disk usage level
        for (Disk disk : serverHealthStat.getDisks()) {

            // need to find the DiskThreshold by deviceId
            DiskThreshold diskThreshold = serverThreshold
                    .findDiskThresholdById(disk.getDeviceID());
            if (diskThreshold != null) {

                if (diskThreshold.getDiskPercentFreeCriticalLevel() >= disk
                        .getPercentDiskFree()) {
                    // need to send critical or urgent email to SA, disk is
                    // almost full
                    LOGGER.info(String
                            .format("DiskId %s disk percent free (%f) is below critical level (%f)",
                                    disk.getDeviceID(), disk
                                    .getPercentDiskFree(),
                                    diskThreshold
                                    .getDiskPercentFreeCriticalLevel()));
                    needToNotify = true;

                    // We've breach critical disk usage mark. Just send the
                    // email notification.
                    stringBuilder.append("CRITICAL: Disk, ID = "
                            + disk.getDeviceID() + " percent free disk space ["
                            + disk.getPercentDiskFree()
                            + "%] is below the critical threshold ["
                            + diskThreshold.getDiskPercentFreeCriticalLevel()
                            + "%].\n\n");

                } else if (diskThreshold.getDiskPercentFreeWarningLevel() >= disk
                        .getPercentDiskFree()) {
                    // need to send warning email
                    LOGGER.info(String
                            .format("DiskId %s disk percent free (%f) is below warning level (%f)",
                                    disk.getDeviceID(), disk
                                    .getPercentDiskFree(),
                                    diskThreshold
                                    .getDiskPercentFreeWarningLevel()));

                    /*
                     * mark that warning email needs to be sent: indicate in
                     * email servername and the problem.
                     */
                    needToNotify = true;

                    // We've breach critical disk usage mark. Just send the
                    // email notification.
                    stringBuilder.append("WARNING: Disk, ID = "
                            + disk.getDeviceID() + " percent free disk space ["
                            + disk.getPercentDiskFree()
                            + "%] is below the warning threshold ["
                            + diskThreshold.getDiskPercentFreeWarningLevel()
                            + "%].\n\n");

                }
            }

        }

        // check CPU usage
        if (serverThreshold.getCriticalCPUUsagePercent() >= serverHealthStat
                .getPercentCPUUsage()) {

            needToNotify = true;

            stringBuilder.append("CRITICAL: CPU percent utilization ["
                    + serverHealthStat.getPercentCPUUsage()
                    + "%] has exceeded the critical threshold level ["
                    + serverThreshold.getCriticalCPUUsagePercent() + "%].\n\n");

        } else if (serverThreshold.getWarningCPUUsagePercent() >= serverHealthStat
                .getPercentCPUUsage()) {
            needToNotify = true;

            stringBuilder.append("WARNING: CPU percent utilization ["
                    + serverHealthStat.getPercentCPUUsage()
                    + "%] has exceeded the warning threshold level ["
                    + serverThreshold.getWarningCPUUsagePercent() + "%].\n\n");
        }

        // check physical memory free
        if (serverThreshold.getCriticalMemFreePercent() >= serverHealthStat
                .getPercentMemFree()) {

            needToNotify = true;
            stringBuilder.append("CRITICAL: Memory percent free ["
                    + serverHealthStat.getPercentMemFree()
                    + "%] is below the critical threshold level ["
                    + serverThreshold.getCriticalMemFreePercent() + "%].\n\n");
        } else if (serverThreshold.getWarningMemFreePercent() >= serverHealthStat
                .getPercentMemFree()) {
            needToNotify = true;
            stringBuilder.append("WARNING: Memory percent free ["
                    + serverHealthStat.getPercentMemFree()
                    + "%] is below the warning threshold level ["
                    + serverThreshold.getWarningMemFreePercent() + "%].\n\n");
        }

        // send email to notifier admins
        // if (needToNotify) {
        // EmailNotifier emailNotifier = new EmailNotifier();
        // String subject = "Attention! " + serverHealthStat.getServerName()
        // + " system performance is in degraded mode.";
        // String msg = stringBuilder.toString();
        // String from = "phatsumo@gmail.com";
        // String recipients = "dragons236@gmail.com";
        //
        // emailNotifier.sendMail(msg, from, recipients, subject);
        //
        // }

    }

}
