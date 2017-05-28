package com.example.android.notelist.com.example.android.notelist.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dozie on 2017-05-11.
 */

public class NoteItem{

    private String key;
    private String text;
    private double longi_tude;
    private double lati_tude;


    public String getKey(){
        return key;
    }
    public void setKey(String key){
        this.key = key;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setLongi_tude(double longi_tude){
        this.longi_tude = longi_tude;
    }

    public double getLongi_tude(double longi_tude){
        return longi_tude;
    }
    public void setLati_tude(double lati_tude){
        this.lati_tude = lati_tude;
    }

    public double getLati_tude(double lati_tude){
        return lati_tude;
    }

    public static NoteItem getNew(){
        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);
        String pattern = "yyyy-MM-dd HH:mm:ss Z";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String key = formatter.format(new Date());

        NoteItem note = new NoteItem();
        note.setKey(key);
        note.setText("");
        note.setLati_tude(0);
        note.setLongi_tude(0);

        return note;

    }
///////////remember problem for longitude and latitude might be here
    @Override
    public String toString() {
        return this.getText();
    }
}


