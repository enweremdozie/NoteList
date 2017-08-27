package com.example.android;

/**
 * Created by dozie on 2017-06-10.
 */

public class ItemObject
{
    private String _name;
    private String _author;

    public ItemObject(String name, String auth)
    {
        this._name = name;
        this._author = auth;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public String getAuthor()
    {
        return _author;
    }

    public void setAuthor(String auth)
    {
        this._author = auth;
    }
}