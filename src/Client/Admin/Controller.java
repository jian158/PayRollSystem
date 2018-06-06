package Client.Admin;

import Client.Admin.Dialog.EmployeeDialog;
import Client.Admin.Dialog.SubsidyDialog;
import Client.Admin.Dialog.TypeDialog;
import Interface.Application;
import Interface.Handler;
import Interface.SimpleController;
import Client.Main.SocketService;
import Table.Employee;
import Table.Message;
import Table.SubsidyInfo;
import Table.Type;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements SimpleController,Handler,Initializable {
    private SocketService socketService;

    @FXML public TableView<Employee> EmployeeTable;
    @FXML public TableView<Type> typeTable;
    @FXML public TableView<SubsidyInfo> subsidyTable;

    @FXML public TabPane tabPane;

    @Override
    public void Receive(Message.Type type, ObjectInputStream is) {
        try {
            if(type==Message.Type.TYPE_GET){
                final List<Type> list= (List<Type>) is.readObject();
                Platform.runLater(()->{
                    typeTable.getItems().clear();
                    typeTable.getItems().addAll(list);
                });
            }else if(type==Message.Type.EMPLOYEE_GET){
                final List<Employee> list= (List<Employee>) is.readObject();
                Platform.runLater(()->{
                    EmployeeTable.getItems().clear();
                    EmployeeTable.getItems().addAll(list);
                });
            }else if(type==Message.Type.SUB_INFO_GET){
                final List<SubsidyInfo> list= (List<SubsidyInfo>) is.readObject();
                Platform.runLater(()->{
                    subsidyTable.getItems().clear();
                    subsidyTable.getItems().addAll(list);
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setParent(Application parent) {

    }


    public void addEmployee(){
        EmployeeDialog dialog=new EmployeeDialog();
        dialog.show(this);
    }

    public void addType(){
        TypeDialog dialog=new TypeDialog();
        dialog.show(this);
    }

    public void addSubsidy(){
        SubsidyDialog dialog=new SubsidyDialog();
        dialog.show(this);
    }

    @Override
    public void setData(Object object) {
        System.out.println(object.getClass());
        if(object instanceof Employee){
            addEmployeeToTable((Employee) object);
        }else if(object instanceof Type){
            addEmployeeTypeToTable((Type) object);
        }else if(object instanceof SubsidyInfo){
            addSubsidyToTable((SubsidyInfo) object);
        }
    }



    private void addEmployeeToTable(Employee employee){
        employee.Id=generator("YG",EmployeeTable.getItems().size());
        EmployeeTable.getItems().add(employee);
        socketService.addMessage(Message.Type.EMPLOYEE_INSERT);
        socketService.addMessage(employee);
        socketService.send();
    }

    private void addEmployeeTypeToTable(Type type){
        type.Id=generator("GZ",typeTable.getItems().size());
        typeTable.getItems().add(type);
        socketService.addMessage(Message.Type.TYPE_INSERT);
        socketService.addMessage(type);
        socketService.send();
    }


    public void addSubsidyToTable(SubsidyInfo subsidyInfo){
        subsidyInfo.JTBH=generator("JT",subsidyTable.getItems().size());
        subsidyTable.getItems().add(subsidyInfo);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        socketService=SocketService.getSocketService();
        socketService.setSimpleController(this);

        for(TableColumn column:typeTable.getColumns()){
            column.setCellValueFactory(new PropertyValueFactory((String) column.getUserData()));
        }

        for(TableColumn column:EmployeeTable.getColumns()){
            column.setCellValueFactory(new PropertyValueFactory((String) column.getUserData()));
        }

        for (TableColumn column:subsidyTable.getColumns()){
            column.setCellValueFactory(new PropertyValueFactory((String) column.getUserData()));
        }

        pullTypes();
        pullEmployees();
        pullSubsidys();

//        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            String userdata= (String) newValue.getUserData();
//            if("type".equals(userdata)){
//                pullTypes();
//            }
//        });

    }

    private void pullTypes(){
        socketService.addMessage(Message.Type.TYPE_GET);
        socketService.send();
    }

    private void pullSubsidys(){
        socketService.addMessage(Message.Type.SUB_INFO_GET);
        socketService.send();
    }

    private void pullEmployees(){
        socketService.addMessage(Message.Type.EMPLOYEE_GET);
        socketService.send();
    }


    private String generator(String label,int size){
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < 4-String.valueOf(size).length(); i++) {
            builder.append('0');
        }
        builder.append(size+1);
        return label+builder.toString();
    }
}
