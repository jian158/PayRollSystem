package Server.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static LinkedBlockingQueue<Connection> queue=new LinkedBlockingQueue<>();
    private static int count=0;
    private static ConnectionPool pool;
    private final static int MAX_CONNECTION=16;

    private static final Object wait=new Object();


    public static String __USER="root";  //用户名
    public static String __PASSD="1234"; //密码
    public static String DRIVER = "com.mysql.jdbc.Driver"; //驱动
    public static String URL = "jdbc:mysql://localhost:3306/lab"; //url

    //连接数据库
    private static Connection newConnection(){
        Connection conn=null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,__USER, __PASSD);
            if(conn.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
                return null;
            }
            conn.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("登录失败");
            e.printStackTrace();
        }
        // 连续数据库
        return conn;
    }


    public static void close() {
        Iterator<Connection> iterator=queue.iterator();
        while (iterator.hasNext()){
            Connection connection=iterator.next();
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ConnectionPool(){

    }

    public static synchronized Connection getConnection(){

        if(queue.size()>0) {
            System.out.println("valid Pool");
            return queue.poll();
        }

        else if(count<MAX_CONNECTION){
            Connection connection=newConnection();
            if(connection!=null){
                count++;
                return connection;
            }
            return null;
        }

        else if(count==MAX_CONNECTION&&queue.size()==0){
            try {
                synchronized (wait){
                    wait.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.poll();
    }

    public static synchronized void  closeConnection(Connection connection){
        queue.add(connection);
        synchronized (wait){
            wait.notifyAll();
        }
    }

}
