package v2;

import org.java_websocket.WebSocket;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class PeerConnection {
    private PeerClient client;
    private PeerInfo peerInfo;

    public PeerConnection(PeerClient client, PeerInfo peerInfo) {
        this.client = client;
        this.peerInfo = peerInfo;
    }

    public PeerConnection(PeerInfo peerInfo) {
        this.peerInfo = peerInfo;
    }

    public PeerConnection(PeerClient client) throws UnknownHostException {
        this.client = client;
        peerInfo = new PeerInfo(8887, InetAddress.getByName(client.getURI().getHost()));
    }

    public PeerConnection(WebSocket conn){
        peerInfo = new PeerInfo(conn);
        try {
            client = new PeerClient(new URI("ws://" + conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":8887"));
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public PeerClient getClient() {
        return client;
    }

    public PeerInfo getPeerInfo() {
        return peerInfo;
    }

    public void setPeerInfo(PeerInfo peerInfo) {
        this.peerInfo = peerInfo;
    }

    public void send(String msg){
        client.send(msg);
    }
}
