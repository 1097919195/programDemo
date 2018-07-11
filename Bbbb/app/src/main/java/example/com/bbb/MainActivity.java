package example.com.bbb;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


//横竖屏切换
public class MainActivity extends AppCompatActivity {

    BasePopupWindow popupWindow;
    View pop;
    RelativeLayout rl_main;
    Button btn, pop_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Tag", "onCreate");
        initView();
    }

    private void initView() {
        pop = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
        pop_btn = (Button) pop.findViewById(R.id.pop_btn);
        rl_main = (RelativeLayout) findViewById(R.id.main_content);
        btn = (Button) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupwindow();
            }
        });

        pop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

    }

    private void showPopupwindow() {
        popupWindow = new BasePopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(240);
        popupWindow.setContentView(pop);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAsDropDown(rl_main, Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Tag", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Tag", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Tag", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Tag", "onStop");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Tag", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Tag", "onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("Tag", "onConfigurationChanged");
    }
}
