package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.notelist.R;
import com.example.android.notelist.com.example.android.notelist.data.NoteItem;

/**
 * Created by dozie on 2017-05-13.
 */

public class NoteEditorActivity extends AppCompatActivity {

    private NoteItem note;
    TextView displayText;
    String textOnScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayText = (TextView) findViewById(R.id.noteText);//                 OVER HERE
        Intent intent = this.getIntent();
        note = new NoteItem();
        note.setKey(intent.getStringExtra("key"));
        note.setText(intent.getStringExtra("text"));


            EditText et = (EditText) findViewById(R.id.noteText);
            et.setText(note.getText());
            //textOnScreen = et.getText().toString();
            et.setSelection(et.getText().length());
            //displayText.setText(textOnScreen);

    }

private void saveAndFinish(){
    EditText et = (EditText) findViewById(R.id.noteText);
    String noteText = et.getText().toString();

    Intent intent = new Intent();
    //Intent intent = new Intent(this, NoteActivity.class);
    intent.putExtra("key", note.getKey());
    intent.putExtra("text", noteText);
    //Log.d("CREATION", "enters here 3");
    //intent.putExtra("text", noteText);
    if(noteText.length() > 0) {
        setResult(RESULT_OK, intent);
        //Log.d("CREATION", "enters here 4");
    }
    finish();
    //Log.d("CREATION", "enters here 5");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.d("CREATION", "enters here 1");
        if(item.getItemId() == android.R.id.home){
            saveAndFinish();
        }
        return false;
    }



    @Override
    public void onBackPressed() {
        saveAndFinish();
    }
}
