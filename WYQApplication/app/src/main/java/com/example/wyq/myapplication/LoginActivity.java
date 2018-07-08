package com.example.wyq.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * Created by Administrator on 2018/4/18.
 */

public class LoginActivity extends Activity{

    private EditText User,Psd;
    private Button mLogin,mRegist;
    private CheckBox mRemPsd,mAutoLogin;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Intent intent=new Intent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        setContentView(R.layout.login_layout);
        User=findViewById(R.id.user);
        Psd=findViewById(R.id.psd);
        mLogin=findViewById(R.id.login);
        mRegist=findViewById(R.id.regist);
        mRemPsd=findViewById(R.id.checkbox1);
        mAutoLogin=findViewById(R.id.checkbox2);

        mRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog= new AlertDialog.Builder(LoginActivity.this);
                //dialog.setTitle("自定义对话框").setIcon(R.mipmap.ic_launcher);
                dialog.setView(getLayoutInflater().inflate(R.layout.item, null));
                dialog.create().show();
            }
        });


        sp=getSharedPreferences("login",Context.MODE_PRIVATE);
        editor=sp.edit();

        if(sp.getBoolean("rem",false)){
            User.setText(sp.getString("user",""));
            Psd.setText(sp.getString("psd",""));
            mRemPsd.setChecked(true);
        }
        if (sp.getBoolean("auto",false)){
            mAutoLogin.setChecked(true);
            intent.setClass(LoginActivity.this,MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String mUser=User.getText().toString();
                 String mPsd=Psd.getText().toString();
                if (mUser.equals("user1")&&mPsd.equals("123456")){
                    if (mRemPsd.isChecked()){
                        editor.putBoolean("rem",true);
                        editor.putString("user",mUser);
                        editor.putString("psd",mPsd);
                    }{
                        editor.clear();
                    }
                    if (mAutoLogin.isChecked()){
                        editor.putBoolean("auto",true).commit();
                    }else {
                        editor.putBoolean("auto",false).commit();
                    }
                    editor.apply();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    finish();
                }
            }
        });


    }
}
