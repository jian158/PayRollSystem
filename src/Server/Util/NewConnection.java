package Server.Util;

import Table.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NewConnection implements Runnable {
    private Socket socket;
    private ObjectOutputStream os;
    public NewConnection(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        ObjectInputStream is= null;
        try {
            os=new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            while (!socket.isClosed()){
                Message.Type type= (Message.Type) is.readObject();
                if(type==Message.Type.LOGIN){
                    User user=(User) is.readObject();
                    user.login(os);
                }else if(type==Message.Type.SUB_INFO_INSERT){
                    SubsidyInfo subsidyInfo= (SubsidyInfo) is.readObject();
                    subsidyInfo.insert(os);
                }else if(type==Message.Type.SUB_INFO_GET){
                    SubsidyInfo.queryAll(os);
                }else if(type==Message.Type.TYPE_INSERT){
                    Type t= (Type) is.readObject();
                    t.insert(os);
                }else if(type==Message.Type.TYPE_GET){
                    Type.queryAll(os);
                }else if(type==Message.Type.EMPLOYEE_INSERT){
                    Employee employee= (Employee) is.readObject();
                    employee.insert(os);
                }else if(type==Message.Type.EMPLOYEE_GET){
                    Employee.queryAll(os);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("***********End************");
    }
}
