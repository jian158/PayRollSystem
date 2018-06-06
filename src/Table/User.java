package Table;

import Server.Main.ConnectionPool;
import Server.Util.Util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Serializable {
    public String user;
    public String passd;
    public User(String user,String passd){
        this.user=user;
        this.passd=passd;
    }

    public void login(ObjectOutputStream os){
        Connection conn=ConnectionPool.getConnection();
        boolean state=false;
        if(conn!=null){
            try {
                PreparedStatement statement=conn.prepareStatement("select count(*) as SIZE from user where USER=? and PSW=?");
                statement.setString(1,user);
                statement.setString(2,passd);
                ResultSet resultSet=statement.executeQuery();
                if(resultSet.next()&&resultSet.getInt("SIZE")>0) {
                    state=true;
                }else {
                    System.out.println("ResultSet NULL");
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println("Util Login"+e.getMessage());
            }
            ConnectionPool.closeConnection(conn);
        }

        Util.sendACK(os,state,Message.Type.SUC_LOGIN,Message.Type.ERR_LOGIN);

    }
}
