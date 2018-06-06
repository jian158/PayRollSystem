package Table;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.Serializable;

public class Message {
    public enum Type{LOGIN,SUC_LOGIN,ERR_LOGIN,
        SUB_INFO_INSERT,SUB_INFO_GET,
        TYPE_INSERT,TYPE_GET,
        EMPLOYEE_INSERT,EMPLOYEE_GET
    };

    public Type type;


    //显示提示框
    public static Alert ShowInfo(String s){
        Alert alert=new Alert(Alert.AlertType.NONE,s, ButtonType.CLOSE);
        alert.setTitle("提示");
        alert.show();

        return alert;
    }

    public static void postInfo(String s){
        Platform.runLater(() -> ShowInfo(s));
    }
}
