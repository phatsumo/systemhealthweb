/**
 *
 */
package systemhealth.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 1062992
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "diskThreshold")
public class DiskThreshold {

    @XmlAttribute(name = "deviceId", required = true)
    private String deviceId;

    @XmlAttribute(name = "diskPercentFreeWarningLevel", required = true)
    private float diskPercentFreeWarningLevel;

    @XmlAttribute(name = "diskPercentFreeCriticalLevel", required = true)
    private float diskPercentFreeCriticalLevel;

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     *            the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the diskPercentFreeWarningLevel
     */
    public float getDiskPercentFreeWarningLevel() {
        return diskPercentFreeWarningLevel;
    }

    /**
     * @param diskPercentFreeWarningLevel
     *            the diskPercentFreeWarningLevel to set
     */
    public void setDiskPercentFreeWarningLevel(float diskPercentFreeWarningLevel) {
        this.diskPercentFreeWarningLevel = diskPercentFreeWarningLevel;
    }

    /**
     * @return the diskPercentFreeCriticalLevel
     */
    public float getDiskPercentFreeCriticalLevel() {
        return diskPercentFreeCriticalLevel;
    }

    /**
     * @param diskPercentFreeCriticalLevel
     *            the diskPercentFreeCriticalLevel to set
     */
    public void setDiskPercentFreeCriticalLevel(
            float diskPercentFreeCriticalLevel) {
        this.diskPercentFreeCriticalLevel = diskPercentFreeCriticalLevel;
    }

}
