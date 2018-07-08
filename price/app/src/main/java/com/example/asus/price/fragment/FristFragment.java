package com.example.asus.price.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.price.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.OrderAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FristFragment extends Fragment {
   private TextView tvName;
   private TextView tvSex;
   private TextView tvPhone;
   private TextView tvIcCard;
   private TextView tvTime;
  private RecyclerView rvCarList;
  private OrderAdapter adapter;
  private List<String> data=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_frist, container, false);
        initData();
        searchOrder();
        initVies(view);
        return view;
    }



    private void initData() {
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body= new  FormBody.Builder()
                .add("phone","18158653013")
                .add("password","123456")
                .build();
        Request request=new Request.Builder()
                .url("http://193.112.129.54/index.php/index/login")
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
                    JSONObject jsonObjects=new JSONObject(response.body().string());
                    JSONObject jsonObjects1=jsonObjects.getJSONObject("content");
                        final String name=jsonObjects1.getString("nickname");
                        final String phone=jsonObjects1.getString("phone");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvName.setText(name);
                            tvPhone.setText(phone);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void searchOrder() {
        data.add("第一个");
        data.add("第二个");
        data.add("第三个");
    }
    private void initVies( View view) {
        tvName=view.findViewById(R.id.name);
        tvSex=view.findViewById(R.id.sex);
        tvPhone=view.findViewById(R.id.phone);
        tvIcCard=view.findViewById(R.id.Idcard);
        tvTime=view.findViewById(R.id.tv_time);
        rvCarList=view.findViewById(R.id.rv_carList);
        rvCarList.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter=new OrderAdapter(getContext(),data,R.layout.layout_order_item);
        rvCarList.setAdapter(adapter);
     adapter.setMyOnItemClickListener(new OrderAdapter.MyOnItemClickListener() {
         @Override
         public void clickListener(int postion) {
             Log.e("postion", String.valueOf(postion));
         }
     });
    }

}
