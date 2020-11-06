package per.cocoadel.learing.jvm.gc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        ServerSocket serverSocket = new ServerSocket(8081);
        while(true){
            Socket socket = serverSocket.accept();
            //阻塞
//            accept(socket);
            //线程池
            executorService.execute(() -> accept(socket));
            //单独开线程
//            new Thread(() -> accept(socket)).start();
        }
    }

    private static void accept(Socket socket){
        try {
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("Http/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("hello nio!");
            printWriter.close();
            socket.close();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}

