package com.example.android.notelist.com.example.android.notelist.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by dozie on 2017-05-11.
 */

public class NotesDataSource {

    public static final String PREFKEY = "notes";
    private SharedPreferences notePrefs;

    public NotesDataSource(Context context){

        notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public List<NoteItem> findAll(){
int i = 0;
        Map<String,?> notesMap = notePrefs.getAll();

        SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());

        List<NoteItem> noteList = new ArrayList<NoteItem>();

        for (String key: keys) {
            Log.d("CREATION", "it reaches here again");
            NoteItem note = new NoteItem();
            //note = NoteItem.getNew();
            note.setKey(key);
            note.setText((String)notesMap.get(key));
            noteList.add(note);
            //Log.d("CREATION", "this is what you typed: " + noteList.get(i).toString());
            i++;
        }

        //NoteItem note = NoteItem.getNew();
        //noteList.add(note);

        return noteList;
    }

    public boolean update(NoteItem note){
        SharedPreferences.Editor editor = notePrefs.edit();
        editor.putString(note.getKey(), note.getText())  ;
        editor.commit();
        return true;
    }

    public boolean remove(NoteItem note){

        if(notePrefs.contains(note.getKey())){
            SharedPreferences.Editor editor = notePrefs.edit();
            editor.remove(note.getKey());
            editor.commit();

        }

        return true;
    }


}
