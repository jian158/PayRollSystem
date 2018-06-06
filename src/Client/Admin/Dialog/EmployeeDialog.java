package Client.Admin.Dialog;

import Client.Admin.Controller;
import Interface.Application;
import Interface.Handler;
import Client.View.PopupText;
import Interface.SimpleController;
import Table.Employee;
import Table.Message;
import Table.Type;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;

public class EmployeeDialog{
    public TextField name,level,tel,info;
    public PopupText<Type> type;
    public ComboBox<String> sex;
    private Handler handler;

    private Stage stage;

    public EmployeeDialog(){}

    public void setHandler(Handler handler,Stage stage){
        this.handler=handler;
        this.stage=stage;
        sex.getItems().addAll("男","女");
        type.addColumn("编号","Id");
        type.addColumn("名称","GZMC");
        type.setOnSelectItemClick(t->{
            type.setText(t.getGZMC());
        });
        Controller controller=(Controller)handler;
        List<Type> list=controller.typeTable.getItems();
        for (int i = 0; i < list.size(); i++) {
            type.add(list.get(i));
        }
        System.out.println("SIZE:"+type.getElements().size());
    }

    public void show(Handler handler){
        Stage primaryStage=new Stage();
        URL url=getClass().getResource("employee.fxml");
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

        EmployeeDialog dialog=fxmlLoader.getController();
        dialog.setHandler(handler,primaryStage);
    }

    public void sure(){
        stage.close();
        if(handler!=null){
            Employee employee=new Employee();
            employee.Name=name.getText();
            employee.Tel=tel.getText();
            employee.Info=info.getText();
            employee.EmployeeType=type.getSelectItem();
            employee.sex=sex.getValue();
            handler.setData(employee);
        }
    }

}
