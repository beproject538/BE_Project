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

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context mContext;
    List<Item> mData;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView conid;
        TextView inviter;
        Button view;

        public MyViewHolder(View v) {
            super(v);
            logo = v.findViewById(R.id.Logo);
            conid = v.findViewById(R.id.conid);
            inviter = v.findViewById(R.id.inviter);
            view = v.findViewById(R.id.viewButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter(Context mContext, List<Item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item,parent,false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.logo.setImageResource(R.mipmap.ic_launcher_round);
        holder.conid.setText(mData.get(position).getConid());
        holder.inviter.setText(mData.get(position).getInviter());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ConnIntent = new Intent(mContext, ConnectionPageActivity.class);
                ConnIntent.putExtra("conid", holder.conid.getText());
                mContext.startActivity(ConnIntent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
