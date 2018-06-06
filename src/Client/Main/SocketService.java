package Client.Main;

import Interface.SimpleController;
import Table.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketService extends Thread{
    private volatile Socket socket;

    private  ObjectOutputStream os;

    private List<Object> sendList=new ArrayList<>();

    private final Object lockObj=new Object();

    private SimpleController simpleController;

    private static SocketService socketService;

    private SocketService(){
        socket=new Socket();
        this.start();
    }

    public static SocketService getSocketService(){
        if(socketService==null){
            socketService=new SocketService();
        }
        return socketService;
    }

    public void setSimpleController(SimpleController simpleController){
        this.simpleController=simpleController;
    }

    public void addMessage(Object object){
        sendList.add(object);
    }

    public void send() {
        if(os==null) {
            try {
                os=new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Thread(() -> {
            try {
                synchronized (lockObj){
                    for (Object o:sendList){
                        os.writeObject(o);
                    }
                    sendList.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress("127.0.0.1",1234));
            ObjectInputStream is=new ObjectInputStream(socket.getInputStream());
            while (!socket.isClosed()){
                Message.Type type= (Message.Type) is.readObject();
//                Object object=is.readObject();

                if(simpleController!=null){
                    simpleController.Receive(type,is);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("close");
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
