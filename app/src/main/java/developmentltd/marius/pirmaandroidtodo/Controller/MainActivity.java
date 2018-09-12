package developmentltd.marius.pirmaandroidtodo.Controller;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import developmentltd.marius.pirmaandroidtodo.Model.AppMokytis;
import developmentltd.marius.pirmaandroidtodo.Model.DBActions;
import developmentltd.marius.pirmaandroidtodo.Model.DBCommunication;
import developmentltd.marius.pirmaandroidtodo.Model.ItemVO;
import developmentltd.marius.pirmaandroidtodo.R;

public class MainActivity extends AppCompatActivity {

    //  Model
    private DBCommunication dbActions;

    //    Controller
    private ItemVOAdapter itemVOAdapter;

    //    View
    private ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        itemListView =(ListView) findViewById(R.id.list_todo) ;
        dbActions = new DBActions(this);
//        dbActions = new AppMokytis();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        updateView();
    }

    public void showDialog() {
        final EditText taskEditText = new EditText(this);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_prompt)
                .setView(taskEditText)
                .setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        dbActions.addItem(task);
                        updateView();

                    }
                })
                .setNegativeButton(R.string.alert_cancel, null)
                .create();
        dialog.show();
    }

    public void updateView() {
        if (itemVOAdapter==null){
            itemVOAdapter= new ItemVOAdapter(this, dbActions.getAllItems(),dbActions);
            itemListView.setAdapter(itemVOAdapter);
        } else{
            itemVOAdapter.clear();
            itemVOAdapter.addAll(dbActions.getAllItems());
            itemVOAdapter.notifyDataSetChanged();
        }
    }
}