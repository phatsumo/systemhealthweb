/**
 *
 */
package systemhealth.job;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import systemhealth.data.ServerHealthStat;
import systemhealth.data.ServerThresholdConfigData;
import systemhealth.util.JSONHelper;
import systemhealth.util.JaxbHelper;
import systemhealth.util.SystemHealthMgr;
import systemhealthweb.SystemHealthWebSocketClientEndPoint;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Quartz job that scans a configured directory and processes server system
 * health.
 *
 * @author 1062992
 *
 */
@DisallowConcurrentExecution
public class ScannerJob implements Job {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ScannerJob.class);

    private String directoryToScan = "";

    private String fileExtensionToFilter = "";

    private ServerThresholdConfigData thresholdConfigData = null;

    private static SystemHealthWebSocketClientEndPoint websocketClient = new SystemHealthWebSocketClientEndPoint();

    /**
     * Default constructor.
     */
    public ScannerJob() {

        Properties scannerProperties = new Properties();
        try {
            scannerProperties.load(ScannerJob.class
                    .getResourceAsStream("/scanner.properties"));
            directoryToScan = scannerProperties.getProperty("directoryToScan");
            fileExtensionToFilter = scannerProperties
                    .getProperty("fileExtensionToFilter");

            LOGGER.debug(this + " Directory to scan: " + directoryToScan);
            LOGGER.debug(this + " File extension filter: "
                    + fileExtensionToFilter);

            // Lets read the threshold config file each time this gets execute
            // (so that
            // users can change threshold levels on the fly.)

            thresholdConfigData = JaxbHelper.unmarshal(
                    ScannerJob.class.getResourceAsStream("/thresholds.xml"),
                    ServerThresholdConfigData.class);

        } catch (JAXBException | IOException e) {
            LOGGER.error("Failed to initialize ScannerJob", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.debug("executing scanner job...");
        // 1. get list of files to process.
        DirectoryStream<Path> ds = scan(directoryToScan);

        // 2. foreach $file in list of files: parse and do something
        List<ServerHealthStat> stats = new ArrayList<ServerHealthStat>();

        for (Path p : ds) {
            File fileToProcess = p.toFile();
            LOGGER.info("File to process: " + fileToProcess.getAbsolutePath());

            ServerHealthStat serverHealthStat = JSONHelper
                    .toServerHealthStat(fileToProcess);

            stats.add(serverHealthStat);

            // create system healh analyzer
            SystemHealthAnalyzer shAnalyzer = new SystemHealthAnalyzer(
                    serverHealthStat, thresholdConfigData.copy());

            // execute job on thread pool
            SystemHealthMgr.getInstance().submitJob(shAnalyzer);

        }

        // send update to websocket
        if (!stats.isEmpty()) {
            try {
                String serverHealthStatJSONString = JSONHelper.toJSON(stats);
                LOGGER.debug("ServerHealthStat: \n"
                        + serverHealthStatJSONString);

                // send to web socket server
                if (websocketClient != null) {
                    websocketClient.sendMessage(serverHealthStatJSONString);
                } else {
                    LOGGER.warn("Web socket client is null!!!!");
                }

            } catch (JsonProcessingException e) {
                LOGGER.error("Failed to serialize JSON object.", e);
            }
        }

    }

    private DirectoryStream<Path> scan(String directory) {
        if (directory == null || directory.isEmpty()) {
            LOGGER.warn("Directory to scan not initialized.");
            return null;
        }

        DirectoryStream<Path> ds = null;
        try {
            ds = Files.newDirectoryStream(Paths.get(directory),
                    new Filter<Path>() {

                        @Override
                        public boolean accept(Path arg0) throws IOException {
                            LOGGER.debug("Path = " + arg0.toString());
                            // accept if file ends with the correct file
                            // extension
                            return arg0.toString().endsWith(
                                    fileExtensionToFilter);
                        }
                    });

        } catch (IOException e) {
            LOGGER.error("Failed to scan directory, " + directory);
        }

        return ds;
    }

}
