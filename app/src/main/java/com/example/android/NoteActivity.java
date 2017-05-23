package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.notelist.R;
import com.example.android.notelist.com.example.android.notelist.data.NoteItem;
import com.example.android.notelist.com.example.android.notelist.data.NotesDataSource;

import java.util.List;

import static com.example.android.notelist.R.id.delete_note;
import static com.example.android.notelist.R.id.share_note;


public class NoteActivity extends AppCompatActivity {
    public static final int EDITOR_ACTIVITY_REQUEST = 1001;
    private static final int MENU_DELETE_ID = 1002;
    NotesDataSource dataSource;
    private List<NoteItem> notesList;
    ListView list;
    ArrayAdapter<NoteItem> adaptor;

    private int currentNoteId;
    EditText editText;
    ArrayAdapter<NoteItem> notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        list = (ListView)findViewById(R.id.list_view);
        registerForContextMenu(list);

        dataSource = new NotesDataSource(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long id) {
                // TODO Auto-generated method stub

                Log.d("CREATION", "long clicked worked");
                lv = (ListView) findViewById(R.id.list);
                registerForContextMenu(lv);
                //longClick();
                return true;
            }
        });*/


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.d("CREATION","click works");

                Object o = list.getItemAtPosition(position);

                //Log.d("CREATION", "position is: " + position);
                //createNote();
                itClicked(position);
            }
        });

        refreshDisplay();
    }


    private void refreshDisplay() {

        //registerForContextMenu(list);
        // android_versions = getResources().getStringArray(R.array.android_versions);
        notesList = dataSource.findAll();

        //ArrayAdapter<NoteItem> adaptor = new ArrayAdapter<NoteItem>(getApplicationContext(), R.layout.list_item_layout, R.id.note_layout, notesList);
        ArrayAdapter<NoteItem> adaptor = new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notesList);

        list.setAdapter(adaptor);
        //setListAdapter(adaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_note, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_create){
            //Log.d("CREATION", "enters options");
            createNote();
        }

        else if(id == android.R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void createNote() {
        //Log.d("CREATION","intent start works");

        NoteItem note = NoteItem.getNew();
        //notesList.add(note);

        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", note.getText());
        // Log.d("CREATION","intent continue works");
        //notesList.add(note);
        //notes.notifyDataSetChanged();

        //notesList = dataSource.findAll();

            startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
            //refreshDisplay();

    }

    public void itClicked(int position){
        NoteItem note = notesList.get(position);
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", note.getText());
        //currentNoteId = position;
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK){
            NoteItem note  = new NoteItem();
            note.setKey(data.getStringExtra("key"));
            note.setText(data.getStringExtra("text"));
            dataSource.update(note);
            refreshDisplay();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentNoteId = (int)info.id;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextual_menu, menu);
        //Log.d("CREATION", "Node id is: " + currentNoteId);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId() == delete_note){
            Log.d("CREATION","context selected");
            NoteItem note = notesList.get(currentNoteId);
            dataSource.remove(note);
            refreshDisplay();
        }

        else if(item.getItemId() == share_note){
            NoteItem note = notesList.get(currentNoteId);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, note.toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            refreshDisplay();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }
}



