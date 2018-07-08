package com.example.asus.price;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.ExpandAdapter;
import bean.ChildBean;
import bean.GroupBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ExpandActivity extends Activity {
    private ExpandableListView list;
    private ExpandAdapter adapter;
    private List<GroupBean> group = new ArrayList<>();
    private List<ArrayList<ChildBean>> dara1 = new ArrayList<ArrayList<ChildBean>>();
    private List<ChildBean> child ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_expand);
        initViews();
        initDatas();
    }

    private void initDatas() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        final Request request = new Request.Builder()
                .url("http://106.15.198.49/test/busInfo.php")
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("success", response.body().string());
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        child = new ArrayList<>();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pstion = jsonObject.getString("postion");
                        group.add(new GroupBean(pstion));
                        Log.e("TDF",""+jsonArray.length());
                        Log.e("aGG","1");
                        JSONArray json = jsonObject.getJSONArray("title");
                        for (int j = 0; j < json.length(); j++) {
                            JSONObject jsonObject1 = json.getJSONObject(j);
                            String station = jsonObject1.getString("station");
                            String buscode = jsonObject1.getString("buscode");
                            String timeStart = jsonObject1.getString("timeStart");
                            String timeEnd = jsonObject1.getString("timeEnd");
                            String distance = jsonObject1.getString("distance");
                            child.add(new ChildBean(station, buscode, timeStart, timeEnd, distance));
                              Log.e("aGG","2");

                        }
                        dara1.add((ArrayList<ChildBean>) child);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

//        group.add(new GroupBean("医院"));
//        group.add(new GroupBean("学校"));
//        group.add(new GroupBean("公园"));
//        child = new ArrayList<>();
//        child.add(new ChildBean("1","2","3","4","5"));
//        child.add(new ChildBean("1","2","3","4","5"));
//        child.add(new ChildBean("1","2","3","4","5"));
//        child.add(new ChildBean("1","2","3","4","5"));
//        dara1.add((ArrayList<ChildBean>) child);
//
//        child = new ArrayList<>();
//        child.add(new ChildBean("1","2","3","4","5"));
//        child.add(new ChildBean("0","2","3","4","5"));
//        child.add(new ChildBean("0","2","3","4","5"));
//        child.add(new ChildBean("0","2","3","4","5"));
//        child.add(new ChildBean("0","2","3","4","5"));
//        dara1.add((ArrayList<ChildBean>) child);
//
//        child = new ArrayList<>();
//        child.add(new ChildBean("1","2","3","4","5"));
//        child.add(new ChildBean("2","2","3","4","5"));
//        child.add(new ChildBean("2","2","3","4","5"));
//        child.add(new ChildBean("2","2","3","4","5"));
//        dara1.add((ArrayList<ChildBean>) child);

    }

    private void initViews() {
        list = findViewById(R.id.expand_list);
        adapter = new ExpandAdapter(this, group, dara1);
        list.setAdapter(adapter);

    }
}
