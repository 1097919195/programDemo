package example.com.retrofitpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://api.thinkpage.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持Observable，Call类型是DefaultCallAdapterFactory默认支持的
                .client(new OkHttpClient())
                .build();
        IWeather iWeather = retrofit2.create(IWeather.class);

        //first
        Call<WeatherBean> call = iWeather.getWeather("rot2enzrehaztkdk","hangzhou");
        call.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                WeatherBean weatherBean = response.body();
                Log.e("log",weatherBean.results.get(0).location.name);
                Log.e("log",weatherBean.results.get(0).now.temperature+"°");
                Log.e("log",weatherBean.results.get(0).now.text);
                //Log.e("log",response.body().toString());
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                Log.e("log", "Error" + t.toString());
            }
        });

        //second  (https://blog.csdn.net/vae260772/article/details/72967219)
        Observable<WeatherBean> observable = iWeather.getWeatherWithObservable("rot2enzrehaztkdk", "hangzhou");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<WeatherBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherBean weatherBean) {
                Log.e("Retrofit Observable",weatherBean.results.get(0).location.name);
            }
        });
    }
}
