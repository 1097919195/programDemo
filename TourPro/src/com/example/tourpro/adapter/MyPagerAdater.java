package com.example.tourpro.adapter;

import com.example.tourpro.R;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPagerAdater extends PagerAdapter{
    
	int res[]={R.drawable.home1,R.drawable.home2,R.drawable.home3,R.drawable.home4};
	//viewpager��ʾ������ͼƬ
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return res.length;
	}
    //viewpager�������������ķ���ֵ  �ж��Ƿ���������ͼƬ
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	//����Ļ����ҳ�� ��ִ������ҳ��
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View)object);
	}
	//��ʼ��ҳ�� 
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ImageView v=new ImageView(container.getContext());
		v.setImageResource(res[position]);
		//�ѵ�ǰ��ͼ��ӵ�������
		container.addView(v);
		//����
		return v;
	}

}
