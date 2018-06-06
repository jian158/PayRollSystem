package Client.Conf;

import Client.Main.Controller;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Conf {
    public Conf() throws IOException {
        Stage primaryStage=new Stage();
        URL url=getClass().getResource("conf.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Pane root = fxmlLoader.load();
        primaryStage.setTitle("登录");

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
        Controller controller=fxmlLoader.getController();
    }
}
