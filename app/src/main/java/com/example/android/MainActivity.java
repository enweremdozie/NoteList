package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.android.notelist.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private StaggeredGridLayoutManager _sGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(_sGridLayoutManager);

        List<ItemObject> sList = getListItemData();

        SampleRecyclerViewAdapter rcAdapter = new SampleRecyclerViewAdapter(
                MainActivity.this, sList);
        recyclerView.setAdapter(rcAdapter);
    }

    private List<ItemObject> getListItemData()
    {
        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("1984", "George Orwell"));
        listViewItems.add(new ItemObject("Pride and Prejudice", "Jane Austen"));
        listViewItems.add(new ItemObject("One Hundred Years of Solitude", "Gabriel Garcia Marquez"));
        listViewItems.add(new ItemObject("The Book Thief", "Markus Zusak"));
        listViewItems.add(new ItemObject("The Hunger Games", "Suzanne Collins"));
        listViewItems.add(new ItemObject("The Hitchhiker's Guide to the Galaxy", "Douglas Adams"));
        listViewItems.add(new ItemObject("The Theory Of Everything", "Dr Stephen Hawking"));

        return listViewItems;
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

