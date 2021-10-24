package v2;

import org.java_websocket.WebSocket;

import java.net.InetAddress;

public class PeerInfo {
    private int port = 8887;
    private InetAddress hostAddress;
    private String username;

    public PeerInfo(int port, InetAddress hostAddress, String username) {
        this.port = port;
        this.hostAddress = hostAddress;
        this.username = username;
    }

    public PeerInfo(int port, InetAddress hostAddress) {
        this.port = port;
        this.hostAddress = hostAddress;
        UsernameGenerator generator = new UsernameGenerator();
        username = generator.generateUsername();
    }

    public PeerInfo(WebSocket conn){
        hostAddress = conn.getRemoteSocketAddress().getAddress();
        UsernameGenerator generator = new UsernameGenerator();
        username = generator.generateUsername();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(InetAddress hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Peer with \n username: " + username + "\n" + "hostAddress: " + hostAddress + "\n" + "on port: " + port;
    }
}
