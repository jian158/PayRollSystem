package Client.Main;

import Client.Admin.Admin;
import Interface.Application;
import Interface.SimpleController;
import Table.Message;
import Table.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;

public class Controller implements SimpleController {
    @FXML private TextField user,passd;
    private Application application;

    private SocketService socketService=SocketService.getSocketService();


    public Controller(){
        socketService.setSimpleController(this);
    }



    public void ConfLink(){

    }

    public void Login(){
        socketService.addMessage(Message.Type.LOGIN);
        socketService.addMessage(new User(user.getText(),passd.getText()));
        socketService.send();
    }


    @Override
    public void Receive(Message.Type type, ObjectInputStream is) {
        System.out.println("YES");
        if(type==Message.Type.SUC_LOGIN){
            Platform.runLater(()->{
                Admin admin=new Admin();
                application.close();
            });
        }
    }

    @Override
    public void setParent(Application parent) {
        this.application=parent;
    }
}
