package Server.Main;

import Server.Util.Server;
import Server.Util.Util;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Scanner;

public class ServerMain {

    private static volatile Server server;
    public static void main(String[] args) throws SQLException {
        new Thread(()->{
            Scanner scanner=new Scanner(System.in);
            while (true){
              if(scanner.nextInt()==100){
                  server.close();
                  break;
              }
            }
        }).start();
         server=new Server();
         server.startServer();
    }

}
