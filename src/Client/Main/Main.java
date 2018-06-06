package Client.Main;

import Server.Util.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application implements Interface.Application {
    private Stage stage;
    public static void main(String[] args) {
        launch(args);
        SocketService socketService=SocketService.getSocketService();
        socketService.close();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url=getClass().getResource("Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Pane root = fxmlLoader.load();
        primaryStage.setTitle("登录");

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        stage=primaryStage;
        primaryStage.show();
        Controller controller=fxmlLoader.getController();
        controller.setParent(this);
    }

    public void close(){
        stage.close();
    }


}
