package com.sunmg.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sunmg.adapter.BaseRecyclerAdapter;
import com.sunmg.adapter.RecyclerViewAdapter;
import com.sunmg.bean.WithHeadAndFootDecoration;

import java.util.ArrayList;

public class LinearLMActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<String> mDatas;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_lm);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initData();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new RecyclerViewAdapter();
        mAdapter.addDatas(mDatas);
        mAdapter.setListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object data, int position) {
                Toast.makeText(LinearLMActivity.this, "data:" + data.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(Object data, int position) {

            }
        });
        View headerView = LayoutInflater.from(this).inflate(R.layout.one_image, null);
        ImageView image = (ImageView) headerView.findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LinearLMActivity.this, "IC_LAUNCHER", Toast.LENGTH_SHORT).show();
            }
        });
//        mAdapter.setHeaderView(headerView);
        mAdapter.setFooterView(headerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new WithHeadAndFootDecoration(this));
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDatas.add("" + i);
        }
    }
}
