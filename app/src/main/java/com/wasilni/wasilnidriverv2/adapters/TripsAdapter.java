package com.wasilni.wasilnidriverv2.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasilni.wasilnidriverv2.R;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TripsAdapter() {
//        mDataset = myDataset;
    }

    @Override
    public TripsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v;
        if(viewType == 1)
        {
            v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pooling_trip_item, parent, false);
        }
        else{
            v =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trip_item, parent, false);

        }
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 1)
        {
            return 1;
        }
        else{
            return 0;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.textView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return 7;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);

            Log.d("SED", "MyViewHolder: I am doing it the right way right ?");
        }
    }
}