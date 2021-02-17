package pers.cocoadel.learning.vote;

import pers.cocoadel.learning.vote.bean.VoteMsg;
import pers.cocoadel.learning.vote.codec.VoteMsgBinCoder;
import pers.cocoadel.learning.vote.codec.VoteMsgCoder;
import pers.cocoadel.learning.vote.framer.Framer;
import pers.cocoadel.learning.vote.framer.LengthFramer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class VoteClientTCP {
    private static final int PORT = 8080;

    private static final String SERVER_IP = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream()) {
//            Framer framer = new DelimFramer(is);
//            VoteMsgCoder voteMsgCoder = new VoteMsgTextCoder();
            Framer framer = new LengthFramer(is);
            VoteMsgCoder voteMsgCoder = new VoteMsgBinCoder();

            int candidateId = 1;
            //发送查询请求
            VoteMsg inquiryRequest = new VoteMsg(true, false, candidateId, 0);
            byte[] reqBuffer = voteMsgCoder.encode(inquiryRequest);
            framer.frameMsg(reqBuffer, os);

            //发送投票请求
            VoteMsg voteRequest = new VoteMsg(false, false, candidateId, 0);
            reqBuffer = voteMsgCoder.encode(voteRequest);
            framer.frameMsg(reqBuffer, os);

            //读取查询响应
            VoteMsg response = voteMsgCoder.decode(framer.nextMsg());
            System.out.println("the response for inquiry : " + response);

            //读取投票响应
            response = voteMsgCoder.decode(framer.nextMsg());
            System.out.println("the response for vote : " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
