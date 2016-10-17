package com.sunmg.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm on 2016/10/17.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;
    public static final int TYPE_NORMAL = 0;

    private List<T> mDatss = new ArrayList<>();

    private View mHeaderView;
    private View mFooterView;

    public interface OnItemClickListener<T> {
        void onItemClick(T data, int position);

        void onItemLongClick(T data, int position);
    }

    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datss) {
        mDatss.addAll(datss);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        } else if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        } else if (position >= mDatss.size() && mFooterView != null) {
            return TYPE_FOOTER;
        } else
            return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new Holder(mHeaderView);
        if (mFooterView != null && viewType == TYPE_FOOTER)
            return new Holder(mFooterView);
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER || getItemViewType(position) == TYPE_HEADER)
            return;
        final int pos = getReallyPosition(holder);
        final T data = mDatss.get(pos);
        onBind(holder, pos, data);

        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(data, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClick(data, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER) {
                        return gridLayoutManager.getSpanCount();
                    } else if (getItemViewType(position) == TYPE_FOOTER) {
                        return gridLayoutManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams stagParams = (StaggeredGridLayoutManager.LayoutParams) params;
            int position = holder.getLayoutPosition();
            if ((position == 0 && mHeaderView != null) || (position >= mDatss.size() && mFooterView != null)) {
                stagParams.setFullSpan(true);
            }
        }
    }

    public int getReallyPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mDatss.size();
        } else if (mHeaderView != null && mFooterView != null) {
            return mDatss.size() + 2;
        } else {
            return mDatss.size() + 1;
        }
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType);

    public abstract void onBind(RecyclerView.ViewHolder holder, int RealPosition, T data);

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
