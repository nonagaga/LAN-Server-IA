package v2;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class PeerClient extends WebSocketClient {

    /**
     * Constructs a WebSocketClient instance and sets it to the connect to the
     * specified URI. The channel does not attempt to connect automatically. The connection
     * will be established once you call <var>connect</var>.
     *
     * @param serverUri the server URI to connect to
     */
    public PeerClient(URI serverUri) {
        super(serverUri);
    }

    /**
     * Called after an opening handshake has been performed and the given websocket is ready to be written on.
     *
     * @param handshakedata The handshake of the websocket instance
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Client successfully connected to: " + this.getRemoteSocketAddress().getAddress());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        send(PeerConnectionList.getConnections().get(0).getPeerInfo().getUsername().getBytes());
    }

    /**
     * Callback for string messages received from the remote host
     *
     * @param message The UTF-8 decoded message that was received.
     **/
    @Override
    public void onMessage(String message) {

    }

    /**
     * Called after the websocket connection has been closed.
     *
     * @param reason Additional information string
     * @param remote
     **/
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Client closed connection with " + this.getRemoteSocketAddress().getAddress() + " for reason: " + reason);
        PeerConnectionHandler.handleRemoveConnection(this);
    }

    /**
     * Called when errors occurs. If an error causes the websocket connection to fail {@link #onClose(int, String, boolean)} will be called additionally.<br>
     * This method will be called primarily because of IO or protocol errors.<br>
     * If the given exception is an RuntimeException that probably means that you encountered a bug.<br>
     *
     * @param ex The exception causing this error
     **/
    @Override
    public void onError(Exception ex) {
        System.err.println("error in client: " + ex);
        ex.printStackTrace();
    }
}
