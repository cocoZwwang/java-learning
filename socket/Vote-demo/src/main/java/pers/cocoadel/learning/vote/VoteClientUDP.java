package pers.cocoadel.learning.vote;

import pers.cocoadel.learning.vote.bean.VoteMsg;
import pers.cocoadel.learning.vote.codec.VoteMsgCoder;
import pers.cocoadel.learning.vote.codec.VoteMsgTextCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class VoteClientUDP {
    private final static int PORT = 9090;

    private final static String SERVER_IP = "localhost";

    private final VoteMsgCoder voteMsgCoder = new VoteMsgTextCoder();

    public static void main(String[] args) {
        VoteClientUDP voteClientUDP = new VoteClientUDP();
        voteClientUDP.start();
    }

    //UDP不需要对数据进行封装成帧，UDP每次数据都是通过以数据包进行发送，不需要考虑数据边界问题。
    public void start() {
        try {
            InetAddress inetAddress = InetAddress.getByName(SERVER_IP);
            DatagramSocket socketClient = new DatagramSocket();
            socketClient.connect(inetAddress,PORT);
            byte[] outBuffer = new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH];
            DatagramPacket sendPack = new DatagramPacket(outBuffer, VoteMsgTextCoder.MAX_WIRE_LENGTH);
            int candidateId = 1;
            //发送查询请求
            VoteMsg inquiryReq = new VoteMsg(true,false,candidateId,0);
            sendPack.setData(voteMsgCoder.encode(inquiryReq));
            socketClient.send(sendPack);
            //发送投票请求
            VoteMsg voteMsg = new VoteMsg(false,false,candidateId,0);
            sendPack.setData(voteMsgCoder.encode(voteMsg));
            socketClient.send(sendPack);
            //接收查询请求
            byte[] inBuffer = new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH];
            DatagramPacket receivePacket = new DatagramPacket(inBuffer,VoteMsgTextCoder.MAX_WIRE_LENGTH);
            socketClient.receive(receivePacket);
            byte[] receiveBytes = Arrays.copyOfRange(receivePacket.getData(), 0, receivePacket.getLength());
            VoteMsg responseMsg = voteMsgCoder.decode(receiveBytes);
            System.out.println("receive response: " + responseMsg);
            //接收投票请求
            socketClient.receive(receivePacket);
            receiveBytes = Arrays.copyOfRange(receivePacket.getData(), 0, receivePacket.getLength());
            responseMsg = voteMsgCoder.decode(receiveBytes);
            System.out.println("receive response: " + responseMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
