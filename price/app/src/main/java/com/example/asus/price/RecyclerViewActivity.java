package com.example.asus.price;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdater;

public class RecyclerViewActivity extends AppCompatActivity implements CallBackUtils.CallBack{
    private RecyclerView recyclerView;
    private RecyclerAdater adater;
    private List<String> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        CallBackUtils.setCallBack(this);
        CallBackUtils.doCallBackMethod();
        data.add("11111");
        data.add("2222");
        data.add("33333");
        data.add("4444");
        data.add("5555");
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adater=new RecyclerAdater(this,data,R.layout.expand_list2);
        recyclerView.setAdapter(adater);
    }

        @Override
        public void doSomeThing(String string) {
            Log.e("name",string);
        }
}
