package example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static java.lang.System.err;
import static java.lang.System.out;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WebSocket2 {

    private static class WebScoketServerImpl extends WebSocketServer {

        private WebScoketServerImpl(int port) {
            super(new InetSocketAddress(port));
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            out.println("WebScoketServerImpl.onOpen(WebSocket, ClientHandshake) called");
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            out.println("WebScoketServerImpl.onClose(WebSocket, int, String, boolean) called: " + reason);
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            out.println("WebScoketServerImpl.onMessage(WebSocket, String) called: " + message);
            JsonObject root = new JsonParser().parse(message).getAsJsonObject();
            String recordId = root.get("accessRecordId").getAsString();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("recordId", recordId);
            conn.send(jsonObject.toString());
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            err.println("WebScoketServerImpl.onError(WebSocket, Exception) called: " + ex);
        }

        @Override
        public void onStart() {
            out.println("WebScoketServerImpl.onStart() called");
        }

    }

    public static void main(String[] args) {
        WebSocket2.WebScoketServerImpl websocketServer = new WebSocket2.WebScoketServerImpl(8091);
        websocketServer.start();
    }
}
