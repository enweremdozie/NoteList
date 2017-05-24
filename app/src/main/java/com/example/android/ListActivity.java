package com.example.android;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private ArrayAdapter<TextView> mAdapter;
    public String nameOfList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHelper = new ListHelper(this);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.action_create) {
            switch (item.getItemId()) {
            case R.id.action_create:
                Log.d("CREATION", "gets this far-1");
                final EditText listEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("New List")
                        .setMessage("List Name:")
                        .setView(listEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Log.d("CREATION", "gets this far0");
                                String listName = String.valueOf(listEditText.getText());
                                nameOfList = listName;
                                SQLiteDatabase sQLiteDatabase = mHelper.getReadableDatabase();
                                Log.d("CREATION", "gets this far");
                                ContentValues values = new ContentValues();
                                Log.d("CREATION", "gets this far1");
                                values.put(ListNames.ListEntry.COL_TASK_TITLE, listName);
                                Log.d("CREATION", "gets this far2");
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

    private void nameOfList(String listName) {
        //this.setTitle(listName);
        Intent intent = new Intent(this, ListActivityEditor.class);
        intent.putExtra("title", listName);
        startActivity(intent);
    }

    public void updateUI(){
        Log.d("CREATION", "gets this far3");
        ArrayList<TextView> listNames = new ArrayList<>();
        SQLiteDatabase sQLiteDatabase = mHelper.getReadableDatabase();
        Cursor cursor = sQLiteDatabase.query(ListNames.ListEntry.TABLE,
                        new String[] {ListNames.ListEntry._ID, ListNames.ListEntry.COL_TASK_TITLE}, null, null, null, null, null);

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(ListNames.ListEntry.COL_TASK_TITLE);
            listNames.get(index).setText(nameOfList);

        }

        if(mAdapter == null){
            mAdapter = new ArrayAdapter<>(this, R.layout.list_look, R.id.list_title, listNames);
            mTaskListView.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(listNames);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        sQLiteDatabase.close();

    }

    public void deleteTask(View view){
    View parent = (View) view.getParent();
        TextView taskTExtView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTExtView.getText());
        SQLiteDatabase sQLiteDatabase = mHelper.getWritableDatabase();
        sQLiteDatabase.delete(Task.TaskEntry.TABLE, Task.TaskEntry.COL_TASK_TITLE + " = ?", new String[] {task});
        sQLiteDatabase.close();
        updateUI();
    }


}



