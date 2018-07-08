package com.example.zjl.dddd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zjl.dddd.R;
import com.example.zjl.dddd.utils.ThemeUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置对应的主题 ，在ui创建好之后设置主题无效，所以要放到setContentView（）方法前面setTheme()
        ThemeUtil.onActivityCreatedSetTheme(this);
        setContentView(R.layout.act_main);

    }
    public void onClick(View view){
        //切换日夜间模式
        ThemeUtil.ChangeCurrentTheme(this);

    }
}
