package com.example.tourpro.adapter;

import com.example.tourpro.R;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPagerAdater extends PagerAdapter{
    
	int res[]={R.drawable.home1,R.drawable.home2,R.drawable.home3,R.drawable.home4};
	//viewpager显示多少正图片
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return res.length;
	}
    //viewpager会根据这个方法的返回值  判断是否重新生成图片
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	//当屏幕划出页面 会执行销毁页面
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View)object);
	}
	//初始化页面 
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ImageView v=new ImageView(container.getContext());
		v.setImageResource(res[position]);
		//把当前视图添加到容器里
		container.addView(v);
		//返回
		return v;
	}

}
