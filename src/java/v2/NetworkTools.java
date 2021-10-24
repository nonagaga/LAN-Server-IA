package v2;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkTools {

    public static InetAddress getPrivateIP() throws UnknownHostException {
        Enumeration e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.out.println("Couldn't find a network!");
        }
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if (i.isSiteLocalAddress()){
                    return i;
                }
            }
        }
        System.out.println("Couldn't find a local address!");
        return InetAddress.getLocalHost();
    }
}
