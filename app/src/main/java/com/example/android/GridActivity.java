package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.notelist.R;
import com.example.android.notelist.com.example.android.notelist.data.NoteItem;
import com.example.android.notelist.com.example.android.notelist.data.NotesDataSource;

import java.util.List;

import static com.example.android.notelist.R.id.delete_note;
import static com.example.android.notelist.R.id.share_note;


public class GridActivity extends AppCompatActivity {
    public static final int EDITOR_ACTIVITY_REQUEST = 1001;
    private static final int MENU_DELETE_ID = 1002;
    NotesDataSource dataSource;
    private List<NoteItem> notesList;

    ArrayAdapter<NoteItem> adaptor;

    private int currentNoteId;
    EditText editText;
    ArrayAdapter<NoteItem> notes;

    private GridAdaptor madapter;
    private GridView gridView;

    GridView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);
        gridView = (GridView) findViewById(R.id.gridView1);
        //Log.d("CREATION", "grid adapter is never ever created");
        registerForContextMenu(gridView);
        dataSource = new NotesDataSource(this);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.list_menu) {
                    callListActivity();
                }

                return true;
            }
        });
        /*gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long id) {
                // TODO Auto-generated method stub
                Log.d("CREATION", "long clicked worked");
                //registerForContextMenu(gridView);
                currentNoteId = position;
                return true;
            }
        });*/


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("CREATION", "click works");
                //currentNoteId = position;
                itClicked(position);
            }
        });

        //Log.d("CREATION", "grid adapter is never created");
        refreshDisplay();

    }

    private void callListActivity(){
        Intent j = new Intent(this, ListActivity.class);
        startActivity(j);

    }

    private void refreshDisplay() {

        notesList = dataSource.findAll();
        //Log.d("CREATION", "grid adapter is not even created");
        madapter = new GridAdaptor(this, notesList);
        //Log.d("CREATION", "grid adapter is not created");

        //Log.d("CREATION", "grid adapter is still not created");
        gridView.setAdapter(madapter);
        //ArrayAdapter<NoteItem> adaptor = new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notesList);
        //Log.d("CREATION", "grid adapter is created");
        //list.setAdapter(adaptor);
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

        if (id == R.id.action_create) {
            //Log.d("CREATION", "enters options");
            createNote();
        } else if (id == android.R.id.home) {
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
        intent.putExtra("longi_tude", note.getLongi_tude(0));
        intent.putExtra("lati_tude", note.getLati_tude(0));
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
        intent.putExtra("longi_tude", note.getLongi_tude(0));
        intent.putExtra("lati_tude", note.getLati_tude(0));
        //currentNoteId = position;
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK){
            NoteItem note  = new NoteItem();
            note.setKey(data.getStringExtra("key"));
            note.setText(data.getStringExtra("text"));
            note.setLongi_tude(data.getDoubleExtra("longi_tude", 0));
            note.setLati_tude(data.getDoubleExtra("lati_tude", 0));
            dataSource.update(note);
            refreshDisplay();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        GridView.AdapterContextMenuInfo info = (GridView.AdapterContextMenuInfo) menuInfo;
        currentNoteId = (int)info.id;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextual_menu, menu);
        //Log.d("CREATION", "Node id is: " + currentNoteId);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId() == delete_note){
            Log.d("CREATION","context selected: " + currentNoteId);
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



class GridAdaptor extends BaseAdapter {
    private List<NoteItem> note;
    //private ArrayList<Integer> listFlag;
    private Activity activity;
    public static final int EDITOR_ACTIVITY_REQUEST = 1001;


    public GridAdaptor(Activity activity,List<NoteItem> note) {
        super();
        this.note = note;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return note.size();
    }

    @Override
    public NoteItem getItem(int position) {
        // TODO Auto-generated method stub
        return note.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public static class ViewHolder
    {
        //public ImageView imgViewFlag;
        public TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.grids, null);

            view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
            //view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewTitle.setText(note.get(position).toString());


        return convertView;
    }


}