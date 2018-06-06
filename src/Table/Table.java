package Table;
/*
* 用于自定义下拉列表PopupText方便筛选数据
* */
public interface Table {
    String getKey();  //获取关键字段
    boolean contains(String key);  //判断是否包含key
}
