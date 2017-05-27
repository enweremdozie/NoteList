package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.notelist.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, GridActivity.class);
        startActivity(i);
    }



        //Log.i("NOTES", note.getKey());

       public void openNote(View view){
        Intent i = new Intent(this, GridActivity.class);
           startActivity(i);
    }

    public void openList(View view){
        Intent j = new Intent(this, ListActivity.class);
        startActivity(j);
    }

}

