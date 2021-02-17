package pers.cocoadel.learning.vote;

import pers.cocoadel.learning.vote.bean.VoteMsg;
import pers.cocoadel.learning.vote.codec.VoteMsgBinCoder;
import pers.cocoadel.learning.vote.codec.VoteMsgCoder;
import pers.cocoadel.learning.vote.framer.Framer;
import pers.cocoadel.learning.vote.framer.LengthFramer;
import pers.cocoadel.learning.vote.service.VoteMsgService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VoteServerTCP {

    private static final int port = 8080;

    private final VoteMsgCoder voteMsgCoder = new VoteMsgBinCoder();
//    private VoteMsgCoder voteMsgCoder = new VoteMsgTextCoder();

    private final VoteMsgService voteMsgService = new VoteMsgService();

    public static void main(String[] args) {
        VoteServerTCP serverTCP = new VoteServerTCP();
        serverTCP.start();
    }

    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                doSocket(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSocket(Socket socket) {
        try (InputStream ins = socket.getInputStream();
             OutputStream ops = socket.getOutputStream();
        ) {
            Framer framer = new LengthFramer(ins);
//            Framer framer = new DelimFramer(ins);
            byte[] reqDate ;
            while((reqDate = framer.nextMsg()) != null){//这一步判断很重要，不能没有
                VoteMsg req = voteMsgCoder.decode(reqDate);
                System.out.println("req: " + req);
                VoteMsg responseMsg = voteMsgService.handleRequest(req);
                framer.frameMsg(voteMsgCoder.encode(responseMsg), ops);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
