package com.example.administrator.cccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.cccc.app.AppConfig;
import com.example.administrator.cccc.bean.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;
import util.UpdateAppUtils;

public class MainActivity extends AppCompatActivity {
    private Unbinder unbinder;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, OtherActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        UpdateKey.API_TOKEN = AppConfig.API_FIRE_TOKEN;
        UpdateKey.APP_ID = AppConfig.APP_FIRE_ID;
        //如果你想通过Dialog来进行下载，可以如下设置
        UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;
        UpdateFunGO.init(this);//Android的自动更新库
    }

    // FIXME: 2018/4/23 0023 
    protected void updateApp(App app) {
        UpdateAppUtils.from(this)
                .serverVersionCode(app.getCode())
                .serverVersionName(app.getVersion())
                .apkPath(app.getPath() + "?v=" + app.getVersion())
                .updateInfo(app.getInfo().trim())
                .update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateFunGO.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UpdateFunGO.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
