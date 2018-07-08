package com.example.wyq.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    List<Bean> mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdata=new ArrayList<>();
        listView=findViewById(R.id.listview);
        MyAdapter myAdapter= new MyAdapter(this,mdata);
        listView.setAdapter(myAdapter);
        getHttp();
    }

    public void getHttp() {
        OkHttpClient mClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url("http://106.15.198.49/test/carInfo.php");
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
                JSONArray jsonArray= null;
                try {
                    jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String license1=jsonObject.getString("license1");
                        String name=jsonObject.getString("name");
                        String balance=jsonObject.getString("balance");
                        Bean bean=new Bean(license1,name,balance);
                        mdata.add(bean);
                        System.out.println("license1" + license1 + ";name" + name + ";balance" + balance);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }


    private List<Map<String,Object>> data() {
        List<Map<String,Object>>list=new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("title", "Google1");
        map.put("info","Android1");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("title", "Google2");
        map.put("info","Android2");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);

        map = new HashMap<>();
        map.put("title", "Google3");
        map.put("info","Android3");
        map.put("img",R.mipmap.ic_launcher);
        list.add(map);

        return list;
    }
}
