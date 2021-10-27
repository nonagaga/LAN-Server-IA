package v2;

import org.java_websocket.WebSocket;

import java.util.ArrayList;

public class PeerConnectionList {

    public static ArrayList<PeerConnection> connections = new ArrayList<>();

    public static boolean contains(WebSocket conn) {
        for (PeerConnection connection: connections
             ) {
            if(connection.getClient().getURI().getHost().equals(conn.getRemoteSocketAddress().getHostString())){
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String IP) {
        for (PeerConnection connection: connections
        ) {
            if(connection.getPeerInfo().getHostAddress().toString().equals("/" + IP)){
                return true;
            }
        }
        return false;
    }

    public static PeerConnection getPeerConnectionFromWebSocket(WebSocket conn){
        for (PeerConnection connection: connections
             ) {
            if(connection.getClient().getURI().getHost().equals(conn.getRemoteSocketAddress().getHostString())){
                return connection;
            }
        }
        System.out.println("Can't find PeerConnection from WebSocket!");
        return null;
    }

    public static PeerConnection getPeerConnectionFromPeerClient(PeerClient client){
        for (PeerConnection connection: connections
             ) {
            if (connection.getClient().equals(client)){
                return connection;
            }
        }
        return null;
    }

    public static ArrayList<PeerConnection> getConnections() {
        return connections;
    }

    public static void add(PeerConnection connection){
        connections.add(connection);
    }

    public static void remove(PeerClient client){
        for (PeerConnection connection: connections
        ) {
            if(connection.getClient().getURI().getHost().equals(client.getSocket().getRemoteSocketAddress().toString())){
                connections.remove(connection);
            }
        }
    }
}
