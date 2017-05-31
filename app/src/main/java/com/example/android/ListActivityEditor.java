package com.example.android;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.notelist.R;

import java.util.ArrayList;

public class ListActivityEditor extends AppCompatActivity {
    private static final String TAG = "ListActivityEditor";
    private ListHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    public int position;
    String origin;
    String activityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_list);
        activityTitle = intent.getExtras().getString("title");
        position = intent.getExtras().getInt("position");

        this.setTitle(activityTitle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHelper = new ListHelper(this);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        origin = intent.getExtras().getString("state");

     /*if(origin == "create"){
         updateUI();

     }

     else if (origin == "click"){
         //getDataBase();

     }*/
        //updateUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_create) {
            switch (item.getItemId()) {
                case R.id.action_create:
                    final EditText taskEditText = new EditText(this);
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("New Task")
                            .setMessage("Add a new task")
                            .setView(taskEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    String listName = String.valueOf(taskEditText.getText());
                                    //nameOfList(listName);
                                    SQLiteDatabase sQLiteDatabase = mHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(Task.TaskEntry.COL_TASK_TITLE, listName);

                                    sQLiteDatabase.insertWithOnConflict(Task.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                    sQLiteDatabase.close();
                                    updateUI();
                                }
                            })

                            .setNegativeButton("Cancel", null)
                            .create();

                    dialog.show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);

            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateUI() {

        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase sQLiteDatabase = mHelper.getReadableDatabase();
        Cursor cursor = sQLiteDatabase.query(Task.TaskEntry.TABLE,
                new String[]{Task.TaskEntry._ID, Task.TaskEntry.COL_TASK_TITLE}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(Task.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(index));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.item_todo, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        sQLiteDatabase.close();

    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTExtView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTExtView.getText());
        SQLiteDatabase sQLiteDatabase = mHelper.getWritableDatabase();
        sQLiteDatabase.delete(Task.TaskEntry.TABLE, Task.TaskEntry.COL_TASK_TITLE + " = ?", new String[]{task});
        sQLiteDatabase.close();
        updateUI();
    }


}



