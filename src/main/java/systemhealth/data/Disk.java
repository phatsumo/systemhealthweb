package systemhealth.data;

/**
 * Simple POJO that contains information pertaining to a disk drive.
 * 
 * @author 1062992
 *
 */
public class Disk {
    private String deviceID;
    private long totalSizeKB;
    private long freeSizeKB;
    private float percentDiskFree;

    /**
     * Default constructor.
     */
    public Disk() {
    }

    /**
     * @return the deviceID
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * @param deviceID
     *            the deviceID to set
     */
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    /**
     * @return the totalSizeKB
     */
    public long getTotalSizeKB() {
        return totalSizeKB;
    }

    /**
     * @param totalSizeKB
     *            the totalSizeKB to set
     */
    public void setTotalSizeKB(long totalSizeKB) {
        this.totalSizeKB = totalSizeKB;
    }

    /**
     * @return the freeSizeKB
     */
    public long getFreeSizeKB() {
        return freeSizeKB;
    }

    /**
     * @param freeSizeKB
     *            the freeSizeKB to set
     */
    public void setFreeSizeKB(long freeSizeKB) {
        this.freeSizeKB = freeSizeKB;
    }

    /**
     * @return the percentDiskFree
     */
    public float getPercentDiskFree() {
        return percentDiskFree;
    }

    /**
     * @param percentDiskFree
     *            the percentDiskFree to set
     */
    public void setPercentDiskFree(float percentDiskFree) {
        this.percentDiskFree = percentDiskFree;
    }

}