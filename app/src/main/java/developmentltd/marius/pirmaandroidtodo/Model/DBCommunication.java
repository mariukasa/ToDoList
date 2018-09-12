package developmentltd.marius.pirmaandroidtodo.Model;

import java.util.ArrayList;

public interface DBCommunication {
    ArrayList<ItemVO> getAllItems();
    void addItem(String task);
    void deleteItem(ItemVO itemVO);
    void updateItem(ItemVO itemVO);
}
