package pers.cocoade.learning.socket.compress;

import java.io.*;
import java.net.Socket;

public class CompressClient {

    private static final int port = 8080;

    private static final String ip = "localhost";

    public static void main(String[] args) {
        CompressClient compressClient = new CompressClient();
        compressClient.start();
    }

    private void start() {
        String usrDir = System.getProperty("user.dir");
        String sourceFile = usrDir + "/Compress-test1.txt";
        String destFile = usrDir + "/Compress-test2.txt";
        try (
                Socket socket = new Socket(ip, port);
                FileInputStream fis = new FileInputStream(sourceFile);
                FileOutputStream fos = new FileOutputStream(destFile);
                InputStream ins = socket.getInputStream();
                OutputStream ops = socket.getOutputStream();
        ) {
            doSend(ops, fis);
            socket.shutdownOutput();
            doReceive(ins, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doSend(OutputStream socketOut, FileInputStream fis) {
        try {
            byte[] inputBuf = new byte[256];
            int len = 0;
            while ((len = fis.read(inputBuf)) != -1) {
                socketOut.write(inputBuf, 0, len);
                System.out.print("R");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doReceive(InputStream ins, FileOutputStream fos) {
        try {
            byte[] inBUf = new byte[256];
            int len = 0;
            while ((len = ins.read(inBUf)) != -1) {
                fos.write(inBUf, 0, len);
                System.out.print("W");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
