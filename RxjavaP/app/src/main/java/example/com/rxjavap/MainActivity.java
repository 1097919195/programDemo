package example.com.rxjavap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText name,pwd;
    private String name1,pwd1,res;
    private TextView tv;
    OkHttpClient mClinet = new OkHttpClient();
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
            public void onClick(View v) {

                name1 = name.getText().toString().trim();
                pwd1 = pwd.getText().toString().trim();
                //通过okhttp发起post请求
                postRequest(name1, pwd1);
            }
        });
    }

    public void postRequest(final String name, final String pwd){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter)throws Exception {

                RequestBody formbody = new FormEncodingBuilder()
                        .add("username", name)
                        .add("password", pwd)
                        .build();
                Request request = new Request.Builder()
                        .url("http://rj.zzx1983.com:30034/autoLogin")
                        .post(formbody)
                        .build();
                //Log.e("TAG", name);

                Call call = mClinet.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        res = response.body().string();
                    }
                });
//                Response response = call.execute();
//                res=response.body().string();

                emitter.onNext(res);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("TAG", "subscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("TAG", "onNext" + s);
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
