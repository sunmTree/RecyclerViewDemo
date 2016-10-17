package com.sunmg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmg.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by sm on 2016/10/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mDatas;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public HomeAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.one_string,parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.one_string.setText(mDatas.get(position));

        if (itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position1 = holder.getLayoutPosition();
                    itemClickListener.onItemClick(v,position1);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position1 = holder.getLayoutPosition();
                    itemClickListener.onItemLongClick(v,position1);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView one_string;

        public MyViewHolder(View itemView) {
            super(itemView);
            one_string = (TextView) itemView.findViewById(R.id.one_string);
        }
    }

}
