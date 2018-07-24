package example.com.okh_rxj_ret_all;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText name,pwd;
    private String name1;
    private String pwd1;
    private String res;
    private TextView tv;

    Call<LoginBean> call;
    Observable<LoginBean> observable;

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
                name1 = name.getText().toString().trim();
                pwd1 = pwd.getText().toString().trim();
                postRequest(name1,pwd1);
            }
        });

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://rj.zzx1983.com:30034")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ZjlBean bean = retrofit.create(ZjlBean.class);
//        call = bean.getlogin("pad01","123456");
//
//        call.enqueue(new Callback<LoginBean>() {
//            @Override
//            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
//                LoginBean loginBean= response.body();
//                Log.e("log",loginBean.message);
//            }
//
//            @Override
//            public void onFailure(Call<LoginBean> call, Throwable t) {
//
//                Log.e("log", "Error" + t.toString());
//            }
//        });
    }

    public void postRequest(final String name, final String pwd){
        //上游
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter)throws Exception {

                //初始化Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiContants.ADDRESS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Observable，Call类型是DefaultCallAdapterFactory默认支持的
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);
                call = apiService.getlogin(name,pwd);
                //first
                call.enqueue(new Callback<LoginBean>() {
                    @Override
                    public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                       if(response.code()!=500) {
                           try {
                               LoginBean loginBean = response.body();
                               res = loginBean.message;
                               emitter.onNext(res);
                           } catch (Exception e) {
                               e.printStackTrace();
                           } finally {
                           }
                       }else
                       {
                           tv.setText("请求失败");
                           Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                       }
                        //Log.e("log",loginBean.message);
                    }

                    @Override
                    public void onFailure(Call<LoginBean> call, Throwable t) {
                        Log.e("log", "Error" + t.toString());
                    }
                });

//                Response response = call.execute();
//                res=response.body().string();

                //second  (https://blog.csdn.net/vae260772/article/details/72967219)
                observable = apiService.getloginWithObservable(name, pwd);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean value) {
                        Log.e("TAG", "onNext With Observable");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });  // 网络请求切换在io线程中调用


            }
        }).subscribe(new Observer<String>() {
            private Disposable mDisposable;
            private int i;
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("TAG", "subscribe");
                mDisposable=d;
            }

            @Override
            public void onNext(String s) {
                Log.e("TAG", "onNext" + s);
//                i++;
//                if (i == 2) {
//                    Log.d("Tag", "dispose");
//                    mDisposable.dispose();//使下游收不到事件
//                    Log.d("Tag", "isDisposed : " + mDisposable.isDisposed());
//                }
                tv.setText(s);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "error");
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "complete");
            }
        });
    }
}
