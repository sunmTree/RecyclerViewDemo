package com.sunmg.recyclerviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.sunmg.adapter.StaggeredHomeAdapter;
import com.sunmg.bean.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GridLayManActivity extends AppCompatActivity implements StaggeredHomeAdapter.OnItemClickListener, View.OnClickListener {

    private RecyclerView grid_recycle;
    private Button add_item,del_item;

    private List<String> mDatas;
    private StaggeredHomeAdapter mAdapter;

    /***
     * 页面跳转
     *
     * @param context
     * @param LIST_MODEL 1 for grid  2 for stag
     */
    public static void pageIntent(Context context, int LIST_MODEL) {
        Intent intent = new Intent(context, GridLayManActivity.class);
        intent.putExtra("list_model", LIST_MODEL);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_grid_lay_man);

        grid_recycle = (RecyclerView) findViewById(R.id.grid_recycle);
        add_item = (Button) findViewById(R.id.add_item);
        del_item = (Button) findViewById(R.id.del_item);

        initData();
        int model = getIntent().getIntExtra("list_model", 0);
        if (model == 1)
            grid_recycle.setLayoutManager(new GridLayoutManager(this, 3));
        else if (model == 2){
            grid_recycle.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        }
        mAdapter = new StaggeredHomeAdapter(this, mDatas);
        grid_recycle.setAdapter(mAdapter);
        if (model == 2)
            mAdapter.setStagManager(true);
        else
            mAdapter.setStagManager(false);
        grid_recycle.addItemDecoration(new DividerGridItemDecoration(this));
        mAdapter.setItemClickListener(this);

        add_item.setOnClickListener(this);
        del_item.setOnClickListener(this);
    }

    private void initData() {
        mDatas = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            mDatas.add("Position: " + i);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position: "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_item:
                mAdapter.addData(1);
                break;
            case R.id.del_item:
                mAdapter.removeData(1);
                break;
        }
    }
}
