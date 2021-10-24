package v2;

import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PeerConnectionHandler {
    private static GUI gui;
    private PeerServer peerServer;

    public PeerConnectionHandler(GUI gui){
        PeerConnectionHandler.gui = gui;
        try {
            peerServer = new PeerServer(this);
            peerServer.start();
        } catch (UnknownHostException e) {
            System.out.println("Server failed to start!");
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Automatically self-connecting to: " + "ws://" + NetworkTools.getPrivateIP().getHostAddress() + ":8887");
            PeerConnection selfConnection = new PeerConnection(new PeerClient(new URI("ws://" + NetworkTools.getPrivateIP().getHostAddress() + ":8887")));
            selfConnection.getClient().connect();
            PeerConnectionList.add(selfConnection);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void handleSend(String data){
        for (PeerConnection connection: PeerConnectionList.getConnections()
             ) {
            connection.send(data);
        }
    }

    public void handleAddConnection(String IP){
        try {
            if (PeerConnectionList.contains(IP)){
                System.out.println("Trying to connect to existing connection! handleAddConnection aborted");
                throw new Error();
            }
            PeerClient client = new PeerClient(URI.create("ws://" + IP + ":8887"));
            System.out.println("client is trying to connect to: " + client.getURI());
            client.connect();
            PeerConnectionList.add(new PeerConnection(client));
        } catch (UnknownHostException e) {
            System.out.println("Connection couldn't be added!");
            e.printStackTrace();
        }
    }

    public static void handleRemoveConnection(PeerClient client){
        PeerConnectionList.remove(client);
        System.out.println("Connection with: " + client.getRemoteSocketAddress().getAddress() + " Removed");
        gui.write(PeerConnectionList.getPeerConnectionFromWebSocket(client.getConnection()).getPeerInfo().getUsername()  + " left the chatroom.");
    }

    public void handleReceive(WebSocket conn, String data){
        if (!PeerConnectionList.contains(conn)) {
            PeerConnectionList.add(new PeerConnection(conn));
        }
        PeerInfo info = PeerConnectionList.getPeerConnectionFromWebSocket(conn).getPeerInfo();
        gui.write(info.getUsername() + " said: " + data);
    }

    public void handleShutdown(){
        try {
            peerServer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (PeerConnection connection:PeerConnectionList.getConnections()
             ) {
            connection.getClient().close();
        }
    }
}
