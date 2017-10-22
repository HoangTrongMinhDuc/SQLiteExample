package com.hoangtrongminhduc.html5.dev.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HTML5 on 22/10/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.DataViewHolder> {
    private List<Contact> contacts;
    private Context mContext;
    private LayoutInflater mInflater;

    public RecycleAdapter(List<Contact> contacts, Context mContext) {
        this.contacts = contacts;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecycleAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_recycle, parent, false);
        return new DataViewHolder(itemView);
    }
    //Xu li item
    @Override
    public void onBindViewHolder(RecycleAdapter.DataViewHolder holder, int position) {
        final Contact contact = contacts.get(position);
        holder.tv_name.setText(contact.getName()); //set text cho item
        //xu li su kien chon item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gui doi tuong contact qua activity Detail neu chon item
                Intent intent = new Intent(mContext, Detail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SEND",contact);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        public DataViewHolder(View itemView){
            super(itemView);
            itemView.setClickable(true);
            tv_name = (TextView)itemView.findViewById(R.id.user_name);
        }
    }
}