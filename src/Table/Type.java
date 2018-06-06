package Table;

import Server.Main.ConnectionPool;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Type implements Table,Serializable {
    public String Id;
    public String GZMC;
    public String level;
    public double wage;
    @Override
    public String getKey() {
        return GZMC;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }


    public String getGZMC(){
        return GZMC;
    }

    public String getId(){
        return Id;
    }

    public String getLevel(){
        return level;
    }

    public String getWage(){
        return String.valueOf(wage);
    }

    public void insert(ObjectOutputStream os){
        Connection conn=ConnectionPool.getConnection();
        boolean state=false;
        if(conn!=null){
            try {
                PreparedStatement statement=conn.prepareStatement("insert into gz VALUES(?,?,?,?)");
                statement.setString(1,Id);
                statement.setString(2,GZMC);
                statement.setString(3,level);
                statement.setDouble(4,wage);
                statement.executeUpdate();
                statement.close();
                conn.commit();
                state=true;
            } catch (SQLException e) {
                System.out.println("TYPE:"+e.getMessage());
            }
            ConnectionPool.closeConnection(conn);
        }
    }

    public static void queryAll(ObjectOutputStream os){
        List<Type> list=query("");
        try {
            os.writeObject(Message.Type.TYPE_GET);
            os.writeObject(list);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Type> query(String regx){
        Connection conn=ConnectionPool.getConnection();
        List<Type> list=new ArrayList<>();
        if(conn!=null){
            try {
                Statement statement=conn.createStatement();
                ResultSet set=statement.executeQuery("select * from gz "+regx);
                while (set.next()){
                    Type type=new Type();
                    type.Id=set.getString(1);
                    type.GZMC=set.getString(2);
                    type.level= set.getString(3);
                    type.wage=set.getDouble(4);
                    list.add(type);
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ConnectionPool.closeConnection(conn);
        }
        return list;
    }


}
