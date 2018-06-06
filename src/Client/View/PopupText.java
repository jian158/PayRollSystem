package Client.View;

import Table.Table;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 2018/4/11.
 */
/*
* ������ؼ�
* */
public class PopupText<T extends Table> extends TextField{
    protected TableView<T> tableView;                       //��ʾ��������
    protected VBox vBox=new VBox();
    protected List<T> list=new ArrayList<>(1);  //���ݼ���
    protected OnSelectItemClick<T> onSelectItemClick;       //ѡ�д����¼��ӿ�
    protected Popup popup=new Popup();                      //������

    public PopupText(){
        this("");
    }

    public PopupText(String text) {
        super(text);
        tableView = new TableView<>();
        tableView.getStyleClass().addAll("combo-box-popup");
        tableView.setStyle("-fx-max-height:240px;");
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        vBox.getChildren().add(tableView);
        vBox.setMaxWidth(getPrefWidth());
        popup.getContent().add(vBox);
        addListener();
    }


    /*
    * ����ѡ�м����ӿڣ���ѡ��ʱִ��ʲô����
    * */
    public void setOnSelectItemClick(OnSelectItemClick<T> onSelectItemClick){
        this.onSelectItemClick=onSelectItemClick;
    }


    /*
    * ���У�������ӳ��ؼ���
    * */
    public void addColumn(String colName,String PropertyValue){
        TableColumn column=new TableColumn(colName);
        column.setCellValueFactory(new PropertyValueFactory<>(PropertyValue));
        tableView.getColumns().add(column);
        //�����п�
        for(TableColumn col:tableView.getColumns()){
            col.setPrefWidth(getPrefWidth()/tableView.getColumns().size());
        }
    }


    private boolean isSelected=false;

    void updateList(String newValue){
//        if(isSelected){
//            isSelected=false;
//            return;
//        }
//        tableView.getItems().clear();
//        for (T t:list){
//            if(t.contains(newValue))
//                add(t);
//        }
    }

    private void addListener(){
        textProperty().addListener((observable, oldValue, newValue) -> {
            updateList(newValue);
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(onSelectItemClick==null||newValue==null)
                return;
            popup.hide();
            isSelected=true;
            onSelectItemClick.onSelectItemClick(newValue);
        });

        addEventFilter(KeyEvent.KEY_RELEASED,event -> {
            if(!popup.isShowing())
                show(getParent().getScene().getWindow(),event);
        });

        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            requestFocus();
            updateList(getText());
            show(getParent().getScene().getWindow(),event);
        });

    }

    public List<T> getElements(){
        return tableView.getItems();
    }

    public T getSelectItem(){
        return tableView.getSelectionModel().getSelectedItem();
    }

    public void add(T t){
        tableView.getItems().add(t);
    }

    public void setAll(List<T> col){
        list=col;
        tableView.getItems().setAll(col);
    }

    /*
    * �ж��������Ϣ�Ƿ���Ч�����ݰ󶨵�list������
    * */
    public boolean exits(){
        for(T t:list){
            if(getText().equals(t.getKey()))
                return true;
        }
        return false;
    }

    public void clear(){
        super.clear();
        tableView.getItems().clear();
    }

    /*
    * �������б���ʾ�ڵ���·�
    * */
    protected void show(Window window, Event event){
        Node eventSource = (Node) event.getSource();
        Bounds sourceNodeBounds = eventSource.localToScreen(eventSource.getBoundsInLocal());
        popup.setX(sourceNodeBounds.getMinX());
        popup.setY(sourceNodeBounds.getMaxY() + 5.0);
        vBox.setMaxWidth(getWidth());
        popup.show(window);
    }


    //ѡ�д����ӿ�
    public interface OnSelectItemClick<T>{
        void onSelectItemClick(T t);
    }

}
