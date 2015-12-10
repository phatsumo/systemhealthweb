package systemhealth.util;


import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import systemhealth.data.DiskThreshold;
import systemhealth.data.ServerThreshold;
import systemhealth.data.ServerThresholdConfigData;

/**
 *
 * @author 1062992
 *
 */
public class JaxbHelperTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(JaxbHelperTest.class);

    private static InputStream inputStream = null;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        inputStream = JaxbHelperTest.class
                .getResourceAsStream("/thresholds.xml");
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
    }

    /**
     *
     * @throws JAXBException
     */
    @Test
    public void testUnmarshal() throws JAXBException {

        ServerThresholdConfigData serverThresholdConfigData = JaxbHelper
                .unmarshal(inputStream, ServerThresholdConfigData.class);

        Assert.assertNotNull(serverThresholdConfigData);

    }

    /**
     *
     * @throws JAXBException
     */
    @Test
    public void testMarshal() throws JAXBException {
        ServerThresholdConfigData tcd = new ServerThresholdConfigData();

        ServerThreshold threshold1 = new ServerThreshold();
        threshold1.setServerName("AURD-AI168889");

        threshold1.setCriticalCPUUsagePercent(95.0f);
        threshold1.setWarningCPUUsagePercent(80.0f);
        threshold1.setCriticalMemFreePercent(5.0f);
        threshold1.setWarningMemFreePercent(10.0f);

        DiskThreshold dt = new DiskThreshold();
        dt.setDeviceId("C:");
        dt.setDiskPercentFreeWarningLevel(10.0f);
        dt.setDiskPercentFreeCriticalLevel(5.0f);

        threshold1.getDiskThreshold().add(dt);
        dt = new DiskThreshold();
        dt.setDeviceId("G:");
        dt.setDiskPercentFreeWarningLevel(10.0f);
        dt.setDiskPercentFreeCriticalLevel(5.0f);
        threshold1.getDiskThreshold().add(dt);

        ServerThreshold threshold2 = new ServerThreshold();
        threshold2.setServerName("ABC");
        threshold2.setCriticalCPUUsagePercent(95f);
        threshold2.setWarningCPUUsagePercent(80f);
        threshold2.setCriticalMemFreePercent(5.0f);
        threshold2.setWarningMemFreePercent(10.0f);
        dt = new DiskThreshold();
        dt.setDeviceId("DEF");
        dt.setDiskPercentFreeWarningLevel(10f);
        dt.setDiskPercentFreeCriticalLevel(5f);

        threshold2.getDiskThreshold().add(dt);
        dt = new DiskThreshold();
        dt.setDeviceId("XYZ");
        dt.setDiskPercentFreeWarningLevel(10f);
        dt.setDiskPercentFreeCriticalLevel(5f);
        threshold2.getDiskThreshold().add(dt);

        tcd.getThresholds().add(threshold1);
        tcd.getThresholds().add(threshold2);

        String marshalledString = JaxbHelper.marshal(tcd,
                ServerThresholdConfigData.class);

        LOGGER.info("Threshold object: " + marshalledString);
    }

}
