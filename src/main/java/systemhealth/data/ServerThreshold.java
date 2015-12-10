/**
 *
 */
package systemhealth.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 1062992
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Threshold")
public class ServerThreshold {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ServerThreshold.class);

    /**
     * Default constructor
     */
    public ServerThreshold() {

    }

    @XmlElement(name = "serverName", required = true)
    private String serverName;

    @XmlElement(name = "diskThreshold", required = false)
    private List<DiskThreshold> diskThreshold;

    @XmlElement(name = "warningCPUUsagePercent", required = true)
    private float warningCPUUsagePercent;

    @XmlElement(name = "criticalCPUUsagePercent", required = true)
    private float criticalCPUUsagePercent;

    @XmlElement(name = "warningMemFreePercent", required = true)
    private float warningMemFreePercent;

    @XmlElement(name = "criticalMemFreePercent", required = true)
    private float criticalMemFreePercent;

    /**
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param serverName
     *            the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * @return the warningCPUUsagePercent
     */
    public float getWarningCPUUsagePercent() {
        return warningCPUUsagePercent;
    }

    /**
     * @param warningCPUUsagePercent
     *            the warningCPUUsagePercent to set
     */
    public void setWarningCPUUsagePercent(float warningCPUUsagePercent) {
        this.warningCPUUsagePercent = warningCPUUsagePercent;
    }

    /**
     * @return the criticalCPUUsagePercent
     */
    public float getCriticalCPUUsagePercent() {
        return criticalCPUUsagePercent;
    }

    /**
     * @param criticalCPUUsagePercent
     *            the criticalCPUUsagePercent to set
     */
    public void setCriticalCPUUsagePercent(float criticalCPUUsagePercent) {
        this.criticalCPUUsagePercent = criticalCPUUsagePercent;
    }

    /**
     * Copy method. Returns a new instance that is a copy of this instance.
     *
     * @return copy of this instance
     */
    public ServerThreshold copy() {
        ServerThreshold st = new ServerThreshold();

        st.setCriticalCPUUsagePercent(this.getCriticalCPUUsagePercent());
        st.setWarningCPUUsagePercent(this.warningCPUUsagePercent);
        st.setWarningMemFreePercent(this.warningMemFreePercent);
        st.setCriticalMemFreePercent(this.criticalMemFreePercent);
        st.setServerName(this.serverName);

        // need to copy the list of disk threshold
        for (DiskThreshold dt : this.diskThreshold) {
            DiskThreshold copy = new DiskThreshold();
            copy.setDeviceId(dt.getDeviceId());
            copy.setDiskPercentFreeCriticalLevel(dt
                    .getDiskPercentFreeCriticalLevel());
            copy.setDiskPercentFreeWarningLevel(dt
                    .getDiskPercentFreeWarningLevel());

            st.getDiskThreshold().add(copy);
        }

        return st;
    }

    /**
     * Finds the {@link DiskThreshold} by deviceId
     *
     * @param deviceId
     *            The drive or device ID, this is the mount point on Linux or
     *            the drive name on Windows
     * @return the DiskThreshold that defines the warning and critical levels
     *         for disk free space
     */
    public DiskThreshold findDiskThresholdById(String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            LOGGER.warn("Parameter deviceId cannot be null or empty.");
            return null;
        }
        DiskThreshold dt = null;

        for (DiskThreshold item : this.diskThreshold) {
            if (item.getDeviceId().equals(deviceId)) { // found it
                dt = item;
                break;
            }
        }
        return dt;
    }

    /**
     * @return the diskThreshold
     */
    public List<DiskThreshold> getDiskThreshold() {
        if (diskThreshold == null) {
            diskThreshold = new ArrayList<DiskThreshold>();
        }

        return diskThreshold;
    }

    /**
     * @param diskThreshold
     *            the diskThreshold to set
     */
    public void setDiskThreshold(List<DiskThreshold> diskThreshold) {
        this.diskThreshold = diskThreshold;
    }

    /**
     * @return the warningMemFreePercent
     */
    public float getWarningMemFreePercent() {
        return warningMemFreePercent;
    }

    /**
     * @param warningMemFreePercent
     *            the warningMemFreePercent to set
     */
    public void setWarningMemFreePercent(float warningMemFreePercent) {
        this.warningMemFreePercent = warningMemFreePercent;
    }

    /**
     * @return the criticalMemFreePercent
     */
    public float getCriticalMemFreePercent() {
        return criticalMemFreePercent;
    }

    /**
     * @param criticalMemFreePercent
     *            the criticalMemFreePercent to set
     */
    public void setCriticalMemFreePercent(float criticalMemFreePercent) {
        this.criticalMemFreePercent = criticalMemFreePercent;
    }
}
