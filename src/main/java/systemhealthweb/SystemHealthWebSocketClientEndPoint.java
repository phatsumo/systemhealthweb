/**
 *
 */
package systemhealthweb;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 1062992
 *
 */
@ClientEndpoint
public class SystemHealthWebSocketClientEndPoint {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SystemHealthWebSocketClientEndPoint.class);

    String uriString = "ws://localhost:8080/systemhealthweb/shwebsocket";
    Session userSession = null;

    /**
     *
     */
    public SystemHealthWebSocketClientEndPoint() {
        try {
            WebSocketContainer container = ContainerProvider
                    .getWebSocketContainer();
            userSession = container
                    .connectToServer(this, URI.create(uriString));

            LOGGER.info("Connected to server, session id = "
                    + userSession.getId());
        } catch (DeploymentException | IOException e) {
            LOGGER.error("Failed to initialize websocket client.", e);
        }
    }

    // /**
    // *
    // * @param endpointURI
    // */
    // public SystemHealthWebSocketClientEndPoint(URI endpointURI) {
    // WebSocketContainer container = ContainerProvider
    // .getWebSocketContainer();
    // try {
    // container.connectToServer(
    // SystemHealthWebSocketClientEndPoint.class, endpointURI);
    // } catch (DeploymentException | IOException e) {
    // LOGGER.error("Failed to initialize websocket client.", e);
    // }
    // }

    /**
     * @param userSession
     */
    @OnOpen
    public void onOpen(Session userSession) {
        LOGGER.info("opening websocket. userSession Id = "
                + userSession.getId());
        this.userSession = userSession;
    }

    /**
     * @param userSession
     */
    @OnClose
    public void onClose(Session userSession) {
        LOGGER.info("closing websocket. userSession Id = "
                + userSession.getId());
        this.userSession = null;
    }

    /**
     * @param message
     */
    public void sendMessage(String message) {
        try {
            LOGGER.info("Sending message: " + message);
            this.userSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            LOGGER.error("Failed to send message.", e);
        }
    }
}
