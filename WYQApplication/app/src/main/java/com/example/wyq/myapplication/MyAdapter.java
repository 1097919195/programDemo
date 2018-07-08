package com.example.wyq.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    List<Bean> mdata;


    public MyAdapter(Context context, List<Bean> mdata) {
        this.context=context;
        this.mdata=mdata;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int i) {
        return mdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            view=inflater.inflate(R.layout.item,null);
        TextView num=view.findViewById(R.id.tv1);
        TextView car_name=view.findViewById(R.id.car_name);
        TextView car_num=view.findViewById(R.id.car_num);
        TextView money=view.findViewById(R.id.money);
        CheckBox cb=view.findViewById(R.id.rb1);

        car_name.setText(mdata.get(i).getName());
        car_num.setText(mdata.get(i).getLicense1());
        money.setText(mdata.get(i).getBalance());
        num.setText(i+1+"");
        return view;
    }
}
