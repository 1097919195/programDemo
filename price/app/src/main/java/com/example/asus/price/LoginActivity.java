package com.example.asus.price;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity implements View.OnClickListener {
    private Button btnLogin, btnRegister;
    private CheckBox cbRemember, cbAotologin;
    private EditText etUser, etPsd;
    private ImageView ivMenu;
    private boolean RePsd = false;
    private boolean AuLogin = false;
    private SharedPreferences sp;
    String status, LoginMsg;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initview();
        initListener();
        initData();
    }


    private void initview() {
        sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
        editor = sp.edit();
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        cbRemember = findViewById(R.id.cb_RePsd);
        cbAotologin = findViewById(R.id.cb_autoLogin);
        etUser = findViewById(R.id.et_user);
        etPsd = findViewById(R.id.et_password);
        ivMenu = findViewById(R.id.iv_menu);
    }

    private void initListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String user = etUser.getText().toString().trim();
        String Psd = etPsd.getText().toString().trim();
        switch (view.getId()) {
            //登录
            case R.id.btn_login:
                if (!user.isEmpty() && !Psd.isEmpty()) {
                    OkHttpClient httpClient = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phone", user)
                            .add("password", Psd)
                            .build();
                    final Request request = new Request.Builder()
                            .url("http://193.112.129.54/index.php/index/login")
                            .post(body)
                            .build();
                    Call call = httpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                status = jsonObject.getString("status");
                                LoginMsg = jsonObject.getString("msg");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (status.equals("1")) {
                                            Toast.makeText(LoginActivity.this, LoginMsg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent().setClass(LoginActivity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            Log.e("status", LoginMsg);
                                            Toast.makeText(LoginActivity.this, LoginMsg, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });
                    rememberPsd(user, Psd);
                    autoLoigin(user, Psd);
                }
                break;
            //注册
            case R.id.btn_register:

                startActivity(new Intent().setClass(this, RadioAvtivity.class));
                break;
        }
    }

    private void autoLoigin(String user, String psd) {
        if (cbAotologin.isChecked()) {
            cbAotologin.setChecked(true);
            AuLogin = true;
            editor.putBoolean("AuLogin", true);
            editor.commit();
        } else {
            cbAotologin.setChecked(false);
        }
    }

    private void rememberPsd(String user, String psd) {
        if (cbRemember.isChecked()) {
            editor.putBoolean("RePsd", true);
            editor.putString("user", user);
            editor.putString("psd", psd);
            editor.commit();
        } else {
            editor.clear();
        }
    }

    private void initData() {
        boolean Cb1 = sp.getBoolean("RePsd", false);
        if (Cb1) {
            cbRemember.setChecked(true);
            etUser.setText(sp.getString("user", "").toString());
            etPsd.setText(sp.getString("psd", "").toString());
        } else {
            etUser.setText("");
            etUser.setText("");
            cbRemember.setChecked(false);
            Cb1 = false;
        }
        //自动登录
        boolean Cb2 = sp.getBoolean("AuLogin", false);
        if (Cb2) {
            cbAotologin.setChecked(true);
            startActivity(new Intent().setClass(this, MainActivity.class));
            finish();
        } else {
            cbAotologin.setChecked(false);
            Cb2 = false;
        }

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(LoginActivity.this, FourthActivity.class));
            }
        });
    }

}
