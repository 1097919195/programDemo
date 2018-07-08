package example.com.retrofitp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText name,pwd;
    private String name1,pwd1,res;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        tv = (TextView) findViewById(R.id.tv);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rj.zzx1983.com:30034")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZjlBean bean = retrofit.create(ZjlBean.class);
        Call<LoginBean> call = bean.getlogin("pad01","123456");
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean loginBean= response.body();
                Log.e("log",loginBean.message);
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {

                Log.e("log", "Error" + t.toString());
            }
        });
    }




//    public void postRequest(final String name, final String pwd){
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter)throws Exception {
//
//                RequestBody formbody = new FormEncodingBuilder()
//                        .add("username", name)
//                        .add("password", pwd)
//                        .build();
//                Request request = new Request.Builder()
//                        .url("http://rj.zzx1983.com:30034/autoLogin")
//                        .post(formbody)
//                        .build();
//                //Log.e("TAG", name);
//
//                Call call = mClinet.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Response response) throws IOException {
//
//                        res = response.body().string();
//                    }
//                });
////                Response response = call.execute();
////                res=response.body().string();
//
//                emitter.onNext(res);
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("TAG", "subscribe");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e("TAG", "onNext" + s);
//                tv.setText(s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("TAG", "error");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("TAG", "complete");
//            }
//        });
//    }
}
