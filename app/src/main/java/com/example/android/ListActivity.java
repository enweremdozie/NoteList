package com.example.android;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.notelist.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private ListHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHelper = new ListHelper(this);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        //updateUI();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.list_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.notes) {
                    callNotesActivity();
                }

                return true;
            }
        });

        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Log.d("CREATION","click works");

                Object o = mTaskListView.getItemAtPosition(position);

                //Log.d("CREATION", "position is: " + position);
                //createNote();
                //itClicked(position);
            }
        });

        updateUI();

    }
    private void callNotesActivity(){
        Intent i = new Intent(this, GridActivity.class);
        startActivity(i);

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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.action_create) {
            switch (item.getItemId()) {
                case R.id.action_create:
                    final EditText taskEditText = new EditText(this);
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("New List")
                            .setMessage("Name of list:")
                            .setView(taskEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    String task = String.valueOf(taskEditText.getText());
                                    //Log.d("CREATION", "List name is " + task);
                                    SQLiteDatabase sQLiteDatabase = mHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(ListNames.ListEntry.COL_TASK_TITLE, task);
                                    sQLiteDatabase.insertWithOnConflict(ListNames.ListEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
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



    public void updateUI(){
        ArrayList<String> taskList = new ArrayList<>();
        Log.d("CREATION", "reaches 1");
        SQLiteDatabase sQLiteDatabase = mHelper.getReadableDatabase();
        Log.d("CREATION", "reaches 2");
        Cursor cursor = sQLiteDatabase.query(ListNames.ListEntry.TABLE,
                new String[] {ListNames.ListEntry._ID, ListNames.ListEntry.COL_TASK_TITLE}, null, null, null, null, null);
        Log.d("CREATION", "reaches 3");
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(ListNames.ListEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(index));
        }
        Log.d("CREATION", "reaches 4");
        if(mAdapter == null){
            mAdapter = new ArrayAdapter<>(this, R.layout.item_todo, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        sQLiteDatabase.close();

    }

    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase sQLiteDatabase = mHelper.getWritableDatabase();
        sQLiteDatabase.delete(Task.TaskEntry.TABLE, Task.TaskEntry.COL_TASK_TITLE + " = ?", new String[] {task});
        sQLiteDatabase.close();
        updateUI();
    }


}



