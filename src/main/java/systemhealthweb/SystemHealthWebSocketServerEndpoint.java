/**
 *
 */
package systemhealthweb;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 1062992
 *
 */
@ServerEndpoint(value = "/shwebsocket")
public class SystemHealthWebSocketServerEndpoint {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SystemHealthWebSocketServerEndpoint.class);

    private static Set<Session> clients = Collections
            .synchronizedSet(new HashSet<>());

    /**
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("New websocket session opened: " + session.getId());
        clients.add(session);

        LOGGER.info("Clients set size: " + clients.size());
    }

    /**
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        LOGGER.info("Websocket session closed: " + session.getId());
        clients.remove(session);

        LOGGER.info("Clients set size: " + clients.size());
    }

    /**
     * @param session
     * @param message
     * @param client
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session client) throws IOException {

        LOGGER.info("Websocket server received message: " + message);

        // broadcast the message to all connected sessions
        notifyAllSessions(message);
    }

    /**
     * @param session
     * @param t
     */
    @OnError
    public void onError(Session session, Throwable t) {
        LOGGER.error("Session id = " + session.getId(), t);
    }

    private void notifyAllSessions(String message) throws IOException {

        LOGGER.info("notifyAllSessions()");
        for (Session session : clients) {
            session.getBasicRemote().sendText(message); // synchronous send
            // session.getAsyncRemote().sendText(message);
        }
    }

}
