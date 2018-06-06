package Client.Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Admin {

    public Admin(){
        show();
    }

    private void show() {
        Stage primaryStage=new Stage();
        URL url=getClass().getResource("admin.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Pane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("π‹¿Ì‘±");

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
