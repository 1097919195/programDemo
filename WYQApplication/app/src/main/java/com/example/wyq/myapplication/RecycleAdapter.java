package com.example.wyq.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    List<WeatherBean> data;
    Context context;
    public RecycleAdapter(Context context, List<WeatherBean> data) {
        this.context=context;
        this.data = data;
    }

    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.weatheritem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder holder, int position) {
        holder.text.setText("");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.num);
        }
    }
}
