package com.example.android.notelist.com.example.android.notelist.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NoteItem{

    private String key;
    private String text;
    private double longitude;
    private double latitude;


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

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getLongitude(double longitude){
        return longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLatitude(double latitude){
        return latitude;
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
        note.setLatitude(0);
        note.setLongitude(0);

        return note;

    }
///////////remember problem for longitude and latitude might be here
    @Override
    public String toString() {
        return this.getText();
    }
}


