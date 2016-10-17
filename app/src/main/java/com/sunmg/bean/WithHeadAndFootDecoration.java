package com.sunmg.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by sm on 2016/10/17.
 */

public class WithHeadAndFootDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private boolean hasHeader;

    public WithHeadAndFootDecoration(Context context, boolean hasHeader) {
        this(context);
        this.hasHeader = hasHeader;
    }

    public WithHeadAndFootDecoration(Context context) {
        TypedArray array = context.obtainStyledAttributes(ATTRS);
        mDivider = array.getDrawable(0);
        array.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) manager).getSpanCount();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) manager).getSpanCount();
        }
        return spanCount;
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getRight()+params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /***
     * 判断是否为最后一列
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount){
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            if ((pos + 1) % spanCount == 0){
                return true;
            }
        }else if (manager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) manager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                if ((pos + 1) % spanCount == 0)
                    return true;
            }else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)
                    return true;
            }
        }
        return false;
    }

    /****
     * 判断是否为最后一行
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount){
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)
                return true;
        }else if (manager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) manager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)
                    return true;
            }else{
                if ((pos + 1) % spanCount == 0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int count = parent.getAdapter().getItemCount();
        int pos = position;

        if (hasHeader){
            if (position == 0){
                outRect.set(0,0,0,mDivider.getIntrinsicHeight());
                return;
            }else {
                pos = position - 1;
            }
        }

        outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
    }
}
