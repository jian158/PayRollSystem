package Table;

import Server.Main.ConnectionPool;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubsidyInfo implements Serializable {
    public String JTBH;
    public String JTMC;
    public String type;
    public double subsidy;

    public String getJTBH() {
        return JTBH;
    }

    public double getSubsidy() {
        return subsidy;
    }

    public String getJTMC() {
        return JTMC;
    }

    public String getType() {
        return type;
    }


    public void insert(ObjectOutputStream os){
        Connection conn=ConnectionPool.getConnection();
        boolean state=false;
        if(conn!=null){
            try {
                PreparedStatement statement=conn.prepareStatement("insert into jtinfo(JTBH, JTMC, TYPE, JTJE) VALUES(?,?,?,?)");
                statement.setString(1,JTBH);
                statement.setString(2,JTMC);
                statement.setString(3,type);
                statement.setDouble(4,subsidy);
                statement.executeUpdate();
                statement.close();
                conn.commit();
                state=true;
            } catch (SQLException e) {
                System.out.println("SUBINFO:"+e.getMessage());
            }
            ConnectionPool.closeConnection(conn);
        }
//        Util.sendACK(os,state,Message.Type.SUC_LOGIN,Message.Type.ERR_LOGIN);
    }

    public static void queryAll(ObjectOutputStream os){
        Connection conn=ConnectionPool.getConnection();
        List<SubsidyInfo> list=new ArrayList<>();
        if(conn!=null){
            try {
                Statement statement=conn.createStatement();
                ResultSet set=statement.executeQuery("select * from jtinfo");
                while (set.next()){
                    SubsidyInfo info=new SubsidyInfo();
                    info.JTBH=set.getString(1);
                    info.JTMC=set.getString(2);
                    info.type=set.getString(3);
                    info.subsidy=set.getDouble(4);
                    list.add(info);
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ConnectionPool.closeConnection(conn);
        }
        try {
            os.writeObject(Message.Type.SUB_INFO_GET);
            os.writeObject(list);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
