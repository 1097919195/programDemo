package example.com.aaaa.activity;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baseapp.AppManager;

import example.com.aaaa.R;

/**
 * Created by asus-pc on 2017/12/7.
 */

public class OtherActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_other;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        AppManager.getAppManager().addActivity(this);

    }
}
