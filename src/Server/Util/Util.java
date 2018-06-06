package Server.Util;

import Table.Message;

import Table.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class Util {

    public static void sendACK(ObjectOutputStream os,boolean state, Message.Type suc, Message.Type fail){
        try {
            if(state){
                os.writeObject(suc);
            }else {
                os.writeObject(fail);
            }
        }catch (IOException e){
            System.out.println("send to client failed:"+e.getMessage());
        }
    }

}
