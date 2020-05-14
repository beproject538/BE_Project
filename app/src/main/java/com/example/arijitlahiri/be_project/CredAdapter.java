package com.example.arijitlahiri.be_project;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.*;

public class CredAdapter extends RecyclerView.Adapter<CredAdapter.MyViewHolder> {

    Context mContext;
    List<CredItem> mData;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cred_key,cred_value;


        public MyViewHolder(View v) {
            super(v);
            cred_key = v.findViewById(R.id.cred_key);
            cred_value = v.findViewById(R.id.cred_value);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CredAdapter(Context mContext, List<CredItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CredAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item,parent,false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.cred_key.setText(mData.get(position).getCred_key());
        holder.cred_value.setText(mData.get(position).getCred_value());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
