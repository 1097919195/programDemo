package example.com.okhttpp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@EActivity(R.layout.activity_simple_okhttp)
public class SimpleOkhttpActivity extends AppCompatActivity {

    @ViewById(R.id.login)
    Button login;
    @ViewById(R.id.cookie)
    Button cookie;
    @ViewById(R.id.cookieText)
    TextView cookieText;

    @Click(R.id.login)
    public void dologin(){

        getRequest();
    }

    @Click(R.id.cookie)
    public void docookie(){

        postRequest();
    }

    /**
     * 发送一个get请求
     */
    private void getRequest() {

        OkHttpClient client=new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://www.baidu.com").build();
        Call call = client.newCall(request);
        //enqueue执行的是异步方法吧，其中callback参数是在主线程了
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            /**
             * 此时还在非UI线程
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cookieText.setText(res);
                    }
                });
            }
        });
    }

    /**
     * 发送一个post请求
     */
    private void postRequest() {

        OkHttpClient client = new OkHttpClient();
        //建立请求表单，添加上传服务器的参数
        FormBody.Builder formbody = new FormBody.Builder()
                .add("username","pad01")
                .add("password","123456");
        //发起请求
        final Request requset=new Request.Builder()
                .url("http://rj.zzx1983.com:30034/autoLogin")
                .post(formbody.build())
                .build();
        Call call=client.newCall(requset);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //也是非UI线程
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cookieText.setText(res);
                    }
                });
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_simple_okhttp);

    }
}
