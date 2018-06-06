package Interface;

import Table.Message;

import java.io.ObjectInputStream;

public interface SimpleController {
    void Receive(Message.Type type, ObjectInputStream is);
    void setParent(Application parent);
}
