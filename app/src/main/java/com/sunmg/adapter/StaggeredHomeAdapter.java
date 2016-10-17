package com.sunmg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmg.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm on 2016/10/17.
 */

public class StaggeredHomeAdapter extends RecyclerView.Adapter<StaggeredHomeAdapter.MyViewHolder> {

    private List<String> mDatas;
    private LayoutInflater mInflater;

    private List<Integer> mHeights;
    private boolean isStagManager = false;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setStagManager(boolean stagManager) {
        isStagManager = stagManager;
    }

    public StaggeredHomeAdapter(Context context, List<String> datas) {
        mDatas = datas;
        mInflater = LayoutInflater.from(context);

        mHeights = new ArrayList<>();
        for (int i = 0; i< mDatas.size(); i++){
            mHeights.add((int)(100 + Math.random() * 300));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.stag_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        if (isStagManager){
//            ViewGroup.LayoutParams params = holder.tv_stag.getLayoutParams();
//            params.height = mHeights.get(position);
//            holder.tv_stag.setLayoutParams(params);
//        }

        holder.tv_stag.setText(mDatas.get(position));

        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mItemClickListener.onItemClick(v,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mItemClickListener.onItemLongClick(v,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int position) {
        mDatas.add(position, "Insert One");
        mHeights.add(position,(int)(100 + Math.random() * 300));
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDatas.remove(position);
        mHeights.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_stag;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_stag = (TextView) itemView.findViewById(R.id.tv_stag);
        }
    }
}
