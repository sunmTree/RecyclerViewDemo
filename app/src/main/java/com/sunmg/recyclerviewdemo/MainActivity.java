package com.sunmg.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunmg.adapter.HomeAdapter;
import com.sunmg.bean.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeAdapter.OnItemClickListener {


    private RecyclerView list_recycle;
    private List<String> recyclerList;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_recycle = (RecyclerView) findViewById(R.id.list_recycle);

        initData();
        list_recycle.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeAdapter(this,recyclerList);
        list_recycle.setAdapter(mAdapter);
        list_recycle.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        mAdapter.setItemClickListener(this);
    }

    private void initData() {
        recyclerList = new ArrayList<>();

        recyclerList.add("LinearLayoutManager");
        recyclerList.add("GridLayoutManager");
        recyclerList.add("StaggeredGridLayoutManager");
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position){
            case 0:
                Intent intent = new Intent(MainActivity.this, LinearLMActivity.class);
                startActivity(intent);
                break;
            case 1:
                GridLayManActivity.pageIntent(MainActivity.this,1);
                break;
            case 2:
                GridLayManActivity.pageIntent(MainActivity.this,2);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
