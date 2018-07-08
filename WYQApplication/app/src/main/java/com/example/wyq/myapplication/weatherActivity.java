package com.example.wyq.myapplication;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/18.
 */

public class weatherActivity extends Activity {

    private RecyclerView recyclerView;
    List<WeatherBean> data;
    private TextView temp,today;
    WeatherBean bean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weatherlayout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        recyclerView = findViewById(R.id.recycleview);
        temp=findViewById(R.id.temp);
        today=findViewById(R.id.today);
        data=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        RecycleAdapter adapter=new RecycleAdapter(weatherActivity.this,data);
        recyclerView.setAdapter(adapter);
        getHttp();

    }
    public void getHttp() {
        OkHttpClient mClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url("https://api.seniverse.com/v3/weather/daily.json?key=gfektehqctfegw8n&location=shaoxing&language=zh-Hans&unit=c&start=0&days=5");
        final Request request = requestBuilder.build();
        Call mcall = mClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr=response.body().string();
                Log.d("TAG",jsonStr);
                try {
                    JSONObject jsonObjectFirst = new JSONObject(jsonStr);
                    JSONArray jsonArrayResults = (JSONArray) jsonObjectFirst.get("results");
                    JSONObject jsonObjectWeatherData = (JSONObject) jsonArrayResults.get(0);
                    JSONArray jsonArraydaily = (JSONArray) jsonObjectWeatherData.get("daily");
                    for (int i = 0; i <jsonArraydaily.length(); i++) {
                        JSONObject jsonObject=jsonArraydaily.getJSONObject(i);
                        String high=jsonObject.getString("high");
                        String low=jsonObject.getString("low");
                        // String balance=jsonObject.getString("balance");
                        bean=new WeatherBean(high,low);
                        data.add(bean);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                temp.setText(bean.getHigh());
                                today.setText("今天："+bean.getLow()+"-"+bean.getHigh()+"℃");

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }
}
