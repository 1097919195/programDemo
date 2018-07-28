package com.example.tourpro;

import com.example.toupro.fragment.CollectionFragment;
import com.example.toupro.fragment.HomePageFragment;
import com.example.toupro.fragment.OrderFragment;
import com.example.toupro.fragment.PersonalFragment;
import com.example.toupro.fragment.SettingFragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity  {

	FragmentManager manager;
	HomePageFragment homePageFragment;
	CollectionFragment collectionFragment;
	SettingFragment settingFrgment;
	OrderFragment orderfragment;
	PersonalFragment personalfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        RadioGroup rg_activity=(RadioGroup) findViewById(R.id.rg_activity);
        //获取一个管理者
        manager = getSupportFragmentManager();
        homePageFragment =new HomePageFragment();
        //开启一个事物
        FragmentTransaction transaction = manager.beginTransaction();
        //添加到容器里面
        transaction.add(R.id.rl_container, homePageFragment);
        //提交修改
        transaction.commit();
        
        rg_activity.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				
				FragmentTransaction transaction2 = manager.beginTransaction();
				if(homePageFragment!=null){
					transaction2.hide(homePageFragment);
				}
				if(collectionFragment!=null){
					transaction2.hide(collectionFragment);
				}
				if(settingFrgment!=null){
					transaction2.hide(settingFrgment);
				}
				if(orderfragment!=null){
					transaction2.hide(orderfragment);
				}
				if(personalfragment!=null){
					transaction2.hide(personalfragment);
				}
				switch (arg1) {
                case R.id.rb_1:
                	if(collectionFragment==null){
						collectionFragment=new CollectionFragment();
						transaction2.add(R.id.rl_container,collectionFragment);
					}else{
						transaction2.show(collectionFragment);
					}
					break;
                case R.id.rb_2:
                	if(orderfragment==null){
                		orderfragment=new OrderFragment();
						transaction2.add(R.id.rl_container,orderfragment);
					}else{
						transaction2.show(orderfragment);
					}
					break;
				case R.id.rb_3:
					if(homePageFragment==null){
						homePageFragment=new HomePageFragment();
						transaction2.add(R.id.rl_container,homePageFragment);
					}else{
						transaction2.show(homePageFragment);
					}
					break;
                case R.id.rb_4:
                	if(personalfragment==null){
                		personalfragment=new PersonalFragment();
						transaction2.add(R.id.rl_container,personalfragment);
					}else{
						transaction2.show(personalfragment);
					}
					break;
                case R.id.rb_5:
                	if(settingFrgment==null){
                		settingFrgment=new SettingFragment();
						transaction2.add(R.id.rl_container,settingFrgment);
					}else{
						transaction2.show(settingFrgment);
					}
					break;
				}
				transaction2.commit();			
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    
}
