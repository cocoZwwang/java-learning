package pers.cocoadel.learning.vote;

import com.sun.org.apache.xpath.internal.operations.String;
import pers.cocoadel.learning.vote.bean.VoteMsg;
import pers.cocoadel.learning.vote.codec.VoteMsgCoder;
import pers.cocoadel.learning.vote.codec.VoteMsgTextCoder;
import pers.cocoadel.learning.vote.service.VoteMsgService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class VoteServerUDP {
    private static final int port = 9090;

    private final VoteMsgCoder voteMsgCoder = new VoteMsgTextCoder();

    private final VoteMsgService voteMsgService = new VoteMsgService();

    public static void main(String[] args) {
        VoteServerUDP voteServerUDP = new VoteServerUDP();
        voteServerUDP.start();
    }

    private void start() {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            byte[] byteBuffer = new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH];
            DatagramPacket datagramPacket = new DatagramPacket(byteBuffer, byteBuffer.length);
            while (true) {
                socket.receive(datagramPacket);
                //因为我们只使用报文中实际数据的长度的字节，因此需要把它们拷贝出来处理。
                byte[] reqBytes = Arrays.copyOfRange(datagramPacket.getData(), 0, datagramPacket.getLength());
                try {
                    VoteMsg req = voteMsgCoder.decode(reqBytes);
                    if (req != null) {
                        System.out.println("receive req msg: " + req.toString());
                        VoteMsg response = voteMsgService.handleRequest(req);
                        byte[] encodeMsg = voteMsgCoder.encode(response);
                        System.out.println("response length: " + encodeMsg.length);
                        datagramPacket.setData(encodeMsg);
                        socket.send(datagramPacket);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
