package com.example.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.notelist.R;

import java.util.List;

/**
 * Created by dozie on 2017-06-10.
 */

public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleViewHolders>
{
    private List<ItemObject> itemList;
    private Context context;

    public SampleRecyclerViewAdapter(Context context,
                                     List<ItemObject> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SampleViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.book_list_item, null);
        SampleViewHolders rcv = new SampleViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SampleViewHolders holder, int position)
    {
        holder.bookName.setText(itemList.get(position).getName());
        holder.authorName.setText(itemList.get(position).getAuthor());
    }

    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }
}