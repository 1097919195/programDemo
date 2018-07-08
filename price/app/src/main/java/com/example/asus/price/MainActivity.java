package com.example.asus.price;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import adapter.MyAdapter;
import bean.ListBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {
    private ListView listView;
    private MyAdapter adapter;
    private ImageView ivMenu;
    private  List<ListBean> dataList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body= new FormBody.Builder().build();
        final Request request=new Request.Builder()
                        .url("http://106.15.198.49/test/carInfo.php")
                        .post(body)
                        .build();

                Call call=okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        try {
                           JSONArray jsonArray=new JSONArray(response.body().string());
                               for(int i=0;i<jsonArray.length();i++) {
                               JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                               String name=jsonObject.getString("name");
                               String license=jsonObject.getString("license1");
                               String balance=jsonObject.getString("balance");
                               ListBean bean=new ListBean(name,license,balance);
                               dataList.add(bean);

                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void initView() {
        listView=findViewById(R.id.listview);
        ivMenu=findViewById(R.id.iv_menu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        startActivity(new Intent().setClass(MainActivity.this,ExpandActivity.class));
            }
        });
        adapter=new MyAdapter(this,dataList,R.layout.layout_list_item);
        listView.setAdapter(adapter);

    }
}
