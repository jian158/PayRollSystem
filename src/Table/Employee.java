package Table;


import Server.Main.ConnectionPool;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Employee implements Serializable {
    public String Id,Name;
    public Type EmployeeType;
    public String sex;
    public String Tel,Info="";

    public String getId(){
        return Id;
    }

    public String getName(){
        return Name;
    }


    public String getType(){
        return EmployeeType.GZMC;
    }

    public String getSex(){
        return sex;
    }

    public String getInfo(){
        return Info;
    }


    public void insert(ObjectOutputStream os){
        Connection conn=ConnectionPool.getConnection();
        if(conn!=null){
            try {
                PreparedStatement statement=conn.prepareStatement("insert into yg(YGBH, YGMC, GZBH, SEX, TEL, INFO) VALUES(?,?,?,?,?,?)");
                statement.setString(1,Id);
                statement.setString(2,Name);
                statement.setString(3,EmployeeType.getId());
                statement.setString(4,sex);
                statement.setString(5,Tel);
                statement.setString(6,Info);
                statement.executeUpdate();
                statement.close();
                conn.commit();
            } catch (SQLException e) {
                System.out.println("Employee:"+e.getMessage());
            }
            ConnectionPool.closeConnection(conn);
        }
    }

    public static void queryAll(ObjectOutputStream os){
        Connection conn=ConnectionPool.getConnection();
        List<Employee> list=new ArrayList<>();
        if(conn!=null){
            try {
                Statement statement=conn.createStatement();
                ResultSet set=statement.executeQuery("select * from yg");
                while (set.next()){
                    Employee employee=new Employee();
                    employee.Id=set.getString(1);
                    employee.Name=set.getString(2);
                    String typeId=set.getString(3);
                    employee.EmployeeType=Type.query("where GZBH='"+typeId+"'").get(0);
                    employee.sex=set.getString(4);
                    employee.Tel=set.getString(5);
                    employee.Info=set.getString(6);
                    list.add(employee);
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ConnectionPool.closeConnection(conn);
        }
        try {
            os.writeObject(Message.Type.EMPLOYEE_GET);
            os.writeObject(list);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
