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

/**
 * @author 1062992
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ServerThresholdConfigData {

    /**
     * Default constructor.
     */
    public ServerThresholdConfigData() {

    }

    /**
     * Copy method to return a copy of this instance.
     *
     * @return a copy of this instance
     * @throws CloneNotSupportedException
     */
    public ServerThresholdConfigData copy() {
        ServerThresholdConfigData c = new ServerThresholdConfigData();

        for (ServerThreshold st : thresholds) {
            c.getThresholds().add(st.copy());
        }

        return c;
    }

    // @XmlElementWrapper(name = "thresholds")
    @XmlElement(name = "serverThreshold")
    private List<ServerThreshold> thresholds = new ArrayList<ServerThreshold>();

    /**
     * @return the thresholds
     */
    public List<ServerThreshold> getThresholds() {
        return thresholds;
    }

    /**
     * @param thresholds
     *            the thresholds to set
     */
    public void setThresholds(List<ServerThreshold> thresholds) {
        this.thresholds = thresholds;
    }

    /**
     * Find the {@link ServerThreshold} given the server name.
     * 
     * @param serverName
     * @return the ServerThreshold
     */
    public ServerThreshold findByServerName(String serverName) {
        if (serverName == null || serverName.isEmpty()) {
            return null;
        }

        ServerThreshold serverThreshold = null;
        for (ServerThreshold st : thresholds) {
            if (serverName.equalsIgnoreCase(st.getServerName())) {
                serverThreshold = st;
                break;
            }
        }

        return serverThreshold;
    }

}
