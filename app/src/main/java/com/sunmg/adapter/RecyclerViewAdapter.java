package com.sunmg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmg.recyclerviewdemo.R;

/**
 * Created by sm on 2016/10/17.
 */

public class RecyclerViewAdapter extends BaseRecyclerAdapter<String> {

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_string,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int RealPosition, String data) {
        if (holder instanceof RecyclerViewHolder){
            ((RecyclerViewHolder)holder).tv.setText(data);
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.one_string);
        }
    }
}
