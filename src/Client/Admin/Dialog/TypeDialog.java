package Client.Admin.Dialog;

import Interface.Handler;
import Table.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class TypeDialog {
    @FXML public TextField name,level,wage;


    private Handler handler;

    private Stage stage;

    private void setHandler(Handler handler,Stage stage) {
        this.stage=stage;
        this.handler=handler;
    }

    public void show(Handler handler){
        Stage primaryStage=new Stage();
        URL url=getClass().getResource("type.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Pane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("工种更新");

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        TypeDialog dialog=fxmlLoader.getController();
        dialog.setHandler(handler,stage);
    }

    public void sure(){
        stage.close();
        System.out.println("SURE");
        if(handler==null){
            System.out.println("NULLPOTOR");
        }
        if(handler!=null){
            System.out.println("SET");
            Type type=new Type();
            type.GZMC=name.getText();
            type.level=level.getText();
            type.wage= Double.parseDouble(wage.getText());
            handler.setData(type);
        }
    }
}
