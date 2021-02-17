package pers.cocoadel.learning.vote;

import pers.cocoadel.learning.vote.bean.VoteMsg;
import pers.cocoadel.learning.vote.codec.VoteMsgBinCoder;
import pers.cocoadel.learning.vote.codec.VoteMsgCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 多播发送客户端
 */
public class VoteMulticastSender {
    public static final int CANDIDATE_ID = 475;

    private static final int PORT = 8080;

    /**
     * 多播地址范围（224.0.0.0 - 239.255.255.255）
     * 其中224.0.0.0 - 224.0.0.255不需要路由控制，里面包含了很多既定的多播地址
     * 比如：224.0.0.1（子网内所有系统），224.0.0.2（子网内所有路由器）等等
     */
    private static final String ADDRESS = "225.255.255.255";

    private static final int TTL = 254;

    public static void main (String args[]) throws IOException {
        MulticastSocket socket = new MulticastSocket();
        socket.setTimeToLive(TTL);
        VoteMsgCoder voteMsgCoder = new VoteMsgBinCoder();
        VoteMsg voteMsg = new VoteMsg(true,false,CANDIDATE_ID,0);
        byte[] msg = voteMsgCoder.encode(voteMsg);
        InetAddress inetAddress = InetAddress.getByName(ADDRESS);
        DatagramPacket datagramPacket = new DatagramPacket(msg,msg.length,inetAddress,PORT);
        System.out.println("Sending Text-Encoded Request (" + msg.length + " bytes): ");
        System.out.println(voteMsg);
        socket.send(datagramPacket);
        socket.close();
    }
}
