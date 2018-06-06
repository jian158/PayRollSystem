package Server.Util;

import Server.Main.ConnectionPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread{
    private final static int PORT=1234;
    private ServerSocket serverSocket;
    public Server(){
    }

    public synchronized void close(){
        if(serverSocket!=null&&!serverSocket.isClosed()){
            try {
                serverSocket.close();
                serverSocket=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ConnectionPool.close();
    }

    public void startServer(){
        try {
            System.out.println("Server start");
            serverSocket=new ServerSocket(PORT);
            while (true){
                Socket socket=serverSocket.accept();
                if(serverSocket.isClosed())
                    break;
                System.out.println("New Connection!");
                new Thread(new NewConnection(socket)).start();
            }
        } catch (IOException ignored) {}
        System.out.println("Server close");
    }

}
