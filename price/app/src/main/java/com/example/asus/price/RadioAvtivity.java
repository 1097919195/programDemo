package com.example.asus.price;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;

import com.example.asus.price.fragment.FristFragment;
import com.example.asus.price.fragment.SecondFragment;
import com.example.asus.price.fragment.ThridFragment;


public class RadioAvtivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_radio_avtivity);
        initView();
        initData();
        changeFragment(new FristFragment());
    }


    private void changeFragment(Fragment fm) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_layout,fm);
        transaction.commit();
    }
    private void initView() {
        group=findViewById(R.id.group);

    }
    private void initData() {
        group.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
         switch (i){
             case R.id.radio1:
               changeFragment(new FristFragment());
                 break;
             case R.id.radio2:
                 changeFragment(new SecondFragment());
                 break;
             case R.id.radio3:
                 changeFragment(new ThridFragment());
                 break;
         }
    }
}
