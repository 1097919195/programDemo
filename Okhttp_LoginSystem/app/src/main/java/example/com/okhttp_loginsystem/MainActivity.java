package example.com.okhttp_loginsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity  extends AppCompatActivity {
    private Button login;
    private EditText name,pwd;
    private String name1,pwd1;
    private TextView tv;
    private OkHttpClient client = new OkHttpClient();

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private CheckBox rember_password,auto_login;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                String qq = (String) msg.obj;
                Log.e("zjl",qq);
                tv.setText(qq);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * OKhttp简单的登录实现
         */
        auto_login = (CheckBox) findViewById(R.id.auto_login);
        rember_password = (CheckBox) findViewById(R.id.rember_password);
        name= (EditText) findViewById(R.id.name);
        pwd= (EditText) findViewById(R.id.pwd);
        login= (Button) findViewById(R.id.login);
        tv= (TextView) findViewById(R.id.tv);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取相关参数
                name1=name.getText().toString().trim();
                pwd1=pwd.getText().toString().trim();
                //通过okhttp发起post请求
                postRequest(name1,pwd1);
            }
        });
        /**
         * 记住密码，自动登录
         */
        sp = getSharedPreferences("UserInfo",MODE_PRIVATE);
        ed = sp.edit();
        Boolean ck_remb=sp.getBoolean("ck_remb", false);
        Boolean ck_auto=sp.getBoolean("ck_auto", false);
        if(ck_remb==true){
            name.setText(sp.getString("name1", null));
            pwd.setText(sp.getString("pwd1", null));
            rember_password.setChecked(true);
        }else {
            ed.clear();
        }
        rember_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rember_password.isChecked()) {
                    name1=name.getText().toString().trim();
                    pwd1=pwd.getText().toString().trim();
                    ed.putString("name1", name1);
                    ed.putString("pwd1", pwd1);
                    ed.putBoolean("ck_remb", true);
                }else{
                    ed.putBoolean("ck_remb", false);
                }
                ed.commit();
            }
        });
    }
    /**
     * 发送post请求
     */
    private void postRequest(String name,String pwd)  {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormEncodingBuilder()
                .add("username",name)
                .add("password",pwd)
                .build();
        //发起请求
        final Request request = new Request.Builder()
                .url("http://rj.zzx1983.com:30034/autoLogin")
                .post(formBody)
                .build();
        //新建一个线程，用于得到服务器响应的参数
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Response response = null;
//                try {
//                    //回调
//                    response = client.newCall(request).execute();
//                    if (response.isSuccessful()) {
//                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
//                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
//                    } else {
//                        throw new IOException("Unexpected code:" + response);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        Call call = client.newCall(request);
        //enqueue执行的是异步方法吧，其中callback参数是在主线程了
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            /**
             * 此时在非UI线程
             */
            @Override
            public void onResponse(Response response) throws IOException {

                final String res=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(res);
                    }
                });
            }
        });
    }

}