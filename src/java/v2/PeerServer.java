package v2;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class PeerServer extends WebSocketServer {
    PeerConnectionHandler handler;

    /**
     * Creates a WebSocketServer that will attempt to bind/listen on the given <var>address</var>.
     *
     */
    public PeerServer(PeerConnectionHandler handler) throws UnknownHostException {
        super(new InetSocketAddress(NetworkTools.getPrivateIP(), 8887));
        this.handler = handler;
    }

    /**
     * Called after an opening handshake has been performed and the given websocket is ready to be written on.
     *
     * @param conn      The <tt>WebSocket</tt> instance this event is occuring on.
     * @param handshake The handshake of the websocket instance
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }

    /**
     * Called after the websocket connection has been closed.
     *
     * @param conn   The <tt>WebSocket</tt> instance this event is occuring on.
     * @param reason Additional information string
     * @param remote
     **/
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    /**
     * Callback for string messages received from the remote host
     *
     * @param conn    The <tt>WebSocket</tt> instance this event is occuring on.
     * @param message The UTF-8 decoded message that was received.
     **/
    @Override
    public void onMessage(WebSocket conn, String message) {
        handler.handleReceive(conn, message);
    }


    @Override
    public void onMessage(WebSocket conn, ByteBuffer message){
        handler.handleAddUsername(conn, StandardCharsets.UTF_8.decode(message).toString());
    }

    /**
     * Called when errors occurs. If an error causes the websocket connection to fail {@link #onClose(WebSocket, int, String, boolean)} will be called additionally.<br>
     * This method will be called primarily because of IO or protocol errors.<br>
     * If the given exception is an RuntimeException that probably means that you encountered a bug.<br>
     *
     * @param conn Can be null if there error does not belong to one specific websocket. For example if the servers port could not be bound.
     * @param ex   The exception causing this error
     **/
    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Peer Server Errored with Exception " + ex);
    }

    /**
     * Called when the server started up successfully.
     * <p>
     * If any error occured, onError is called instead.
     */
    @Override
    public void onStart() {
        System.out.println("Peer Server Successfully Started on address: " + this.getAddress());
    }

}
