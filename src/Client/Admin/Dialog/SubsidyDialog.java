package Client.Admin.Dialog;

import Interface.Handler;
import Table.SubsidyInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SubsidyDialog {
    private Handler  handler;
    @FXML
    TextField type,money,jtmc;

    private Stage stage;

    private void setHandler(Handler handler,Stage stage){
        this.handler=handler;
        this.stage=stage;
    }

    public void show(Handler handler){
        Stage primaryStage=new Stage();
        URL url=getClass().getResource("subsidy.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Pane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("员工更新");

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        SubsidyDialog dialog=fxmlLoader.getController();
        dialog.setHandler(handler,primaryStage);
    }


    public void sure(){
        stage.close();
        if(handler!=null){
            SubsidyInfo subsidyInfo=new SubsidyInfo();
            subsidyInfo.type=type.getText();
            subsidyInfo.JTMC=jtmc.getText();
            subsidyInfo.subsidy=Double.parseDouble(money.getText());
            handler.setData(subsidyInfo);
        }
    }
}
