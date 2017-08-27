package com.example.android;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notelist.R;

/**
 * Created by dozie on 2017-06-10.
 */

public class SampleViewHolders extends RecyclerView.ViewHolder implements
        View.OnClickListener
{
    public TextView bookName;
    public TextView authorName;

    public SampleViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        bookName = (TextView) itemView.findViewById(R.id.BookName);
        authorName = (TextView) itemView.findViewById(R.id.AuthorName);
    }

    @Override
    public void onClick(View view)
    {
        Toast.makeText(view.getContext(),
                "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT)
                .show();
    }
}