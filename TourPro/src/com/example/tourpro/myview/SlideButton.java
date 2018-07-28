package com.example.tourpro.myview;

import com.example.tourpro.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SlideButton extends View{

	Bitmap sild_bg_btn,sild_bg_on,sild_bg_off;
	//��ȡ����ͼƬ
	public SlideButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sild_bg_btn=BitmapFactory.decodeResource(getResources(), R.drawable.sild_bg_btn);
		sild_bg_on=BitmapFactory.decodeResource(getResources(), R.drawable.sild_bg_on);
		sild_bg_off=BitmapFactory.decodeResource(getResources(), R.drawable.sild_bg_off);
	}

	//���ÿؼ��Ŀ�ߺ�ͼƬһ��
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(sild_bg_on.getWidth(), sild_bg_on.getHeight());
	}
	
	//���ݷ���ֵ�水ťͼ
	boolean isChecked=false;
	@Override
	protected void onDraw(Canvas canvas) {
		if(isChecked==true){
			canvas.drawBitmap(sild_bg_on, 0,0, null);
			canvas.drawBitmap(sild_bg_btn, sild_bg_on.getWidth()-sild_bg_btn.getWidth(), 0, null);
		}else{
			canvas.drawBitmap(sild_bg_off, 0,0, null);
			canvas.drawBitmap(sild_bg_btn, 0, 0, null);
		}
		super.onDraw(canvas);
	}
	
	//��ⷵ�ص�ֵ
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			isChecked=!isChecked;
			//�������»��ƽ���
			invalidate();
		}
		return true;//�õ��ִ��
	}
}
