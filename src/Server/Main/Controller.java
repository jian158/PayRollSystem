package Server.Main;

import Server.Util.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
    private Server server;
    @FXML private Button switchServer;
    public void init(){

    }

    public void close(){
        if(server!=null)
            server.close();
    }

    public void Switch(){
        if(switchServer.getText().equals("打开")){
            server=new Server();
            switchServer.setText("关闭");
        }else {
            server.close();
            switchServer.setText("打开");
            server=null;
        }
    }
}
