package com.atom.remainder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static com.atom.remainder.NewEventAct.buttonChange;

/**
 * Created by user on 1/24/2017.
 */
public class MoreActionAct extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    ListView listview;
    ArrayList<ListOfData> eventList;
    CustomAdapter customAdapter;
    public static String count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_action);
        RelativeLayout settings = (RelativeLayout) findViewById(R.id.settings_layout);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActionAct.this, SettingsAct.class);
                startActivity(intent);
            }
        });

        setEventList();

    }
    public void setEventList(){

        listview = (ListView) findViewById(R.id.Event_ListView);

        eventList = new ArrayList<>();
        customAdapter = new CustomAdapter(this, R.layout.list_textviews, eventList);
        listview.setAdapter(customAdapter);

        dataBaseHelper = new DataBaseHelper(this);
        Cursor data = dataBaseHelper.getList();

        if (data.getCount() == 0) {
            Toast.makeText(MoreActionAct.this, "No event", Toast.LENGTH_SHORT).show();
        } else {

            while (data.moveToNext()) {
                String id = data.getString(0);
                String name = data.getString(1);

                eventList.add(new ListOfData(id,name));
            }
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long Id) {

                //     Log.i("more"," id "+eventList.get(position).getId());
                Cursor cursor = dataBaseHelper.getRow(eventList.get(position).getId());

                if (cursor.getCount() == 0){
                    Toast.makeText(MoreActionAct.this,"There is no event",Toast.LENGTH_SHORT).show();

                }else{
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext()){

                        buffer.append("Event Id    : " + String.valueOf(cursor.getString(0))+"\n\n");
                        buffer.append("Event Name  : " + String.valueOf(cursor.getString(1))+"\n\n");
                        buffer.append("Date        : " + String.valueOf(cursor.getString(2))+"\n\n");
                        buffer.append("Time        : " + String.valueOf(cursor.getString(3))+"\n\n");
                        buffer.append("Description : " + String.valueOf(cursor.getString(4))+"\n\n");
                        buffer.append("Location    : " + String.valueOf(cursor.getString(5)));

                        showmsg("",buffer.toString());

                    }
                }
            }
        });
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                count = eventList.get(position).getId();
//                int checkedCount = listview.getCheckedItemCount();
                mode.setTitle( count +"  "+"id selected");

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                actionMode.getMenuInflater().inflate(R.menu.menu_more_act,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.delete_menu:

                        Integer deleterows = dataBaseHelper.DeleteData(count);
                        if (deleterows > 0)
                            Toast.makeText(MoreActionAct.this, "Event Deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MoreActionAct.this, "Event Not Found", Toast.LENGTH_SHORT).show();

                        return true;

//                    case R.id.selectAll_menu:
//
//                        final int checkedCount  = eventList.size();
//                        customAdapter.RemoveSelection();
//                        for (int i = 0; i <  checkedCount; i++) {
//                            listview.setItemChecked(i,   true);
//                        }
//                        actionMode.setTitle(checkedCount  + "Selected");

                    case R.id.deleteAll_menu:

                        dataBaseHelper.DeleteAll();
                        Toast.makeText(getApplicationContext(), "All Event Deleted", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.edit_menu:

                        buttonChange  = false;
                        Intent a = new Intent(MoreActionAct.this,NewEventAct.class);
                        startActivity(a);

                    default:
                        return false;
                }
            }
            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

        customAdapter.notifyDataSetChanged();
    }
    public void showmsg(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
