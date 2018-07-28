package com.example.toupro.fragment;


import com.example.tourpro.R;
import com.example.tourpro.adapter.MyPagerAdater;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HomePageFragment extends Fragment {
	
	//onCreate��ָ������fragment��������Activity.onCreate������������г�ʼ������view֮��Ķ�����
	//onCreateView�Ǵ�����fragment��Ӧ����ͼ������������ﴴ���Լ�����ͼ�����ظ������ߡ�
	
	//alt+shift+s ��д����  Override
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//ʵ����һ����ͼ
		View v=inflater.inflate(R.layout.homepage_frag, null);
		final	ViewPager vp=(ViewPager) v.findViewById(R.id.vp);
		final RadioGroup rg=(RadioGroup) v.findViewById(R.id.rg);
		MyPagerAdater adater=new MyPagerAdater();
		//vp���������� ���ҳ�������
		vp.setAdapter(adater);

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				Log.e("JUIN","SDSFSDF");
				//���õ�ǰ�ǵڼ�ҳ
				int i=0;
				switch (arg1) {
				case R.id.rb1:
					i=0;
					break;
				case R.id.rb2:
					i=1;
					break;
				case R.id.rb3:
					i=2;
					break;
				case R.id.rb4:
					i=3;
					break;
				}
				vp.setCurrentItem(i);
				
			}
		});
		//����ҳ��������
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				RadioButton rb=(RadioButton) rg.getChildAt(arg0);
				rb.setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//��ʵ����������View����
		return v;
	}

}
