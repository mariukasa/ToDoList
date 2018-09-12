package developmentltd.marius.pirmaandroidtodo.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DBActions extends SQLiteOpenHelper implements DBCommunication {
    public static final String DB_Name = "BootcampToDo";
    public static final int DB_Version = 1;

    public DBActions(Context context) {
        super(context, DB_Name, null, DB_Version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TasksTable.Table + "(" +
                TasksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TasksTable.Col_Task_Done + " INTEGER DEFAULt 0," +
                TasksTable.Col_Task_Title + " TEXT NOT NULL" +
                ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TasksTable.Table);
        onCreate(db);

    }

    public ArrayList<ItemVO> getAllItems() {
        ArrayList<ItemVO> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TasksTable.Table + ";", null);

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(TasksTable._ID);
            int doneIndex = cursor.getColumnIndex(TasksTable.Col_Task_Done);
            int titleIndex = cursor.getColumnIndex(TasksTable.Col_Task_Title);

            ItemVO itemVO = new ItemVO();
            itemVO.id = cursor.getInt(idIndex);
            itemVO.done = cursor.getInt(doneIndex);
            itemVO.title = cursor.getString(titleIndex);
            System.out.println(itemVO.id + ";" + itemVO.done + ":" + itemVO.title);
            result.add(itemVO);
        }
        cursor.close();
        db.close();

        return result;
    }

    public void addItem(String task) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TasksTable.Col_Task_Title, task);
        db.insertWithOnConflict(TasksTable.Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteItem(ItemVO itemVO) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TasksTable.Table,
                TasksTable._ID + " = ?",
                new String[]{Integer.toString(itemVO.id)});
        db.close();

    }

    public void updateItem(ItemVO itemVO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TasksTable.Col_Task_Done, itemVO.done);
        SQLiteDatabase db = this.getWritableDatabase();
        db.updateWithOnConflict(TasksTable.Table,
                contentValues,
                TasksTable._ID + " = ?",
                new String[]{Integer.toString(itemVO.id)},
                SQLiteDatabase.CONFLICT_REPLACE
        );
        db.close();
    }

    class TasksTable implements BaseColumns {
        public static final String Table = "tasks";
        public static final String Col_Task_Done = "done";
        public static final String Col_Task_Title = "title";
    }
}