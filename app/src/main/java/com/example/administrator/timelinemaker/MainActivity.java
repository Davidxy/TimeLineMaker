package com.example.administrator.timelinemaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import adapter.TimeLineAdapter;
import view.TimeLineView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> dataList = new ArrayList<>();
    private TimeLineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimeLineAdapter(dataList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mian, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cycle:
                adapter.setType(TimeLineView.CENTERTYPE.CYCLE);
                break;
            case R.id.menu_cycle_ring:
                adapter.setType(TimeLineView.CENTERTYPE.RINGCYCLE);
                break;
            case R.id.menu_other:
                adapter.setType(TimeLineView.CENTERTYPE.OTHER);
                break;
            case R.id.menu_ring:
                adapter.setType(TimeLineView.CENTERTYPE.RING);
                break;
            case R.id.menu_two_ring:
                adapter.setType(TimeLineView.CENTERTYPE.TWORING);
                break;
            case R.id.menu_top:
                adapter.setPositiontype(TimeLineView.POSITIONTYPE.TOP);
                break;
            case R.id.menu_center:
                adapter.setPositiontype(TimeLineView.POSITIONTYPE.CENTER);
                break;
            case R.id.menu_bottom:
                adapter.setPositiontype(TimeLineView.POSITIONTYPE.BOTTOM);
                break;
            case R.id.menu_rondom:
                adapter.setPositiontype(TimeLineView.POSITIONTYPE.RANDOM);
                adapter.setMarginTop(15);
                break;
        }
        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化数据
     **/
    private void iniData() {

        for (int i = 0; i < 10; i++) {
            dataList.add("CYCLE");
            dataList.add("RING");
            dataList.add("RING_CYCLE");
            dataList.add("TWORING");
            dataList.add("OTHER");
        }

    }
}
