package pers.cocoade.learning.socket.comm;

import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class InetAddressExample {
    public static void main(String[] args) {
        listAllInetAdressInfo();
        System.out.println("*******************************************************");
        List<String> hosts = Arrays.asList("www.baidu.com","bank.bak","192.168.3.1");
        for(String host : hosts){
            findInetAddressInfo(host);
        }
    }

    private static void findInetAddressInfo(String host){
        try {
            System.out.println("host: ");
            InetAddress[] addressList = InetAddress.getAllByName(host);
            for(InetAddress address : addressList){
                System.out.println("\t" + address.getHostName() + "/" + address.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("\tunable to find address for " + host);
        }
    }

    private static void listAllInetAdressInfo() {
        try {
            Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
            if (interfaceList == null) {
                System.out.println("--NO interface found--");
            } else {
                while (interfaceList.hasMoreElements()) {
                    NetworkInterface iface = interfaceList.nextElement();
                    System.out.println("Interface " + iface.getName() + ":");
                    Enumeration<InetAddress> addrList = iface.getInetAddresses();
                    if (!addrList.hasMoreElements()) {
                        System.out.println("\t(No address for this interface)");
                    }
                    while (addrList.hasMoreElements()) {
                        InetAddress inetAddress = addrList.nextElement();
                        String ipType = inetAddress instanceof Inet4Address ? "(v4)" :
                                (inetAddress instanceof Inet6Address ? "(v6)" : "(?)");
                        System.out.println("\t Address " + ipType + ": " + inetAddress.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting network interfaces: " + e.getMessage());
        }
    }
}
