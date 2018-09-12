package developmentltd.marius.pirmaandroidtodo.Model;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AppMokytis implements DBCommunication {

    public static final String URL = "http://192.168.0.86:8080";
    public static final String ALL_ITEMS = "/items";
    public static final String DELETE_ITEM = "/items/delete/";
    public static final String UPDATE_ITEM = "/items/update/";
    public static final String ADD_ITEM = "/items/add";



    @Override
    public ArrayList<ItemVO> getAllItems() {
        ArrayList<ItemVO> result = new ArrayList<>();
        try {

            URL url = new URL(AppMokytis.URL + AppMokytis.ALL_ITEMS);
            Scanner sc = new Scanner(url.openStream());
            int i = 0;
            while (sc.hasNext()) {
                ItemVO item = new ItemVO();
                item.id = i++;
                item.done = sc.nextInt();
                item.title = sc.nextLine();
                result.add(item);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addItem(String task) {

    }

    @Override
    public void deleteItem(ItemVO itemVO) {

    }

    @Override
    public void updateItem(ItemVO itemVO) {

    }
}

