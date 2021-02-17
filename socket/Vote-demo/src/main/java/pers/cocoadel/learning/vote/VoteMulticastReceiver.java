package pers.cocoadel.learning.vote;

import pers.cocoadel.learning.vote.bean.VoteMsg;
import pers.cocoadel.learning.vote.codec.VoteMsgBinCoder;
import pers.cocoadel.learning.vote.codec.VoteMsgCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class VoteMulticastReceiver {
    private static final int PORT = 8080;

    private static final String MULTICAST_ADDRESS = "225.255.255.255";


    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(MULTICAST_ADDRESS);
        if (!inetAddress.isMulticastAddress()) {
            throw new IllegalArgumentException("Not a multicast address");
        }

        MulticastSocket socket = new MulticastSocket(PORT);
        //多播的接收者需要假如希望接收多播的地址组
        socket.joinGroup(inetAddress);

        VoteMsgCoder voteMsgCoder = new VoteMsgBinCoder();

        //receive msg
        byte[] inputBUf = new byte[VoteMsgBinCoder.MAX_WIRE_LENGTH];
        DatagramPacket packet = new DatagramPacket(inputBUf, inputBUf.length);
        socket.receive(packet);

        VoteMsg voteMsg = voteMsgCoder.decode(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));
        System.out.println("Received Text-Encoded Request ( " + packet.getLength() + " bytes): ");
        System.out.println(voteMsg);

        socket.close();
    }
}
