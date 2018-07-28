package com.example.tourpro.myview;

import com.example.tourpro.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
//这就是一个自定义view
public class HomeButton extends View{
	
	int colors[]={0xffffa011,0xffdf5431,0xffb551a5,0xff299be8,0xff44daff,0xff6cb5b5};
	int HomeButton_bgcolor;
	int height,width;
	Bitmap tupian1;
	int pic;
	String text;
	boolean b;
	Bitmap bitmaps[]={BitmapFactory.decodeResource(getResources(), R.drawable.home_hotel),BitmapFactory.decodeResource(getResources(), R.drawable.home_groupbuy),
			BitmapFactory.decodeResource(getResources(), R.drawable.home_train),BitmapFactory.decodeResource(getResources(), R.drawable.home_lastmin),
			BitmapFactory.decodeResource(getResources(), R.drawable.home_flight),BitmapFactory.decodeResource(getResources(), R.drawable.home_car),
			BitmapFactory.decodeResource(getResources(), R.drawable.home_scenery)};
	public HomeButton(Context context, AttributeSet attrs) {
		super(context, attrs);//此处的super必须写在语句的前面
		//读取设置的自定义属性
		TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.HomeButton);
		//是否大图
		b=array.getBoolean(R.styleable.HomeButton_isBig, false);
		//读取图片
		pic=array.getInteger(R.styleable.HomeButton_pic,0);
		text=array.getString(R.styleable.HomeButton_text);
		tupian1=BitmapFactory.decodeResource(getResources(), R.drawable.fingerprint);
		//得到的颜色
		HomeButton_bgcolor=array.getInteger(R.styleable.HomeButton_bgcolor, 0);
		//得到的手机屏幕宽
		width=((Activity)context).getWindow().getWindowManager().getDefaultDisplay().getWidth()-30;
		//根据设置的大小来划定控件的大小
		if(b){
			height=width/2;
		}else{
			height=width/4;
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//设置控件的大小,把原来的super去掉
		setMeasuredDimension(width/2, height);
	}

	@Override
	//ondraw试图显示或者什么样都是有这个决定
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		if(HomeButton_bgcolor==1){
//			canvas.drawColor(Color.BLUE);
//		}else{
//			canvas.drawColor(Color.YELLOW);
//		}
		canvas.drawColor(colors[HomeButton_bgcolor]);

		if(b){
			Matrix matrix=new Matrix();
			matrix.postTranslate((getWidth()-bitmaps[pic].getWidth())/2, (getHeight()-bitmaps[pic].getHeight())/2);
			canvas.drawBitmap(bitmaps[pic], matrix, null);
			Paint paint=new Paint();
			paint.setColor(Color.WHITE);
			paint.setTextSize(30);
			canvas.drawText(text, 10, 50, paint);
		}else{
			canvas.drawBitmap(bitmaps[pic], 30,(getHeight()-bitmaps[pic].getHeight())/2, null);
			Paint paint=new Paint();
			paint.setTextSize(20);
			paint.setColor(Color.WHITE);
			canvas.drawText(text, 40+bitmaps[pic].getWidth(), height/3, paint);
		}
		//添加手指印
		if(isCheck){
			Matrix matrix=new Matrix();
			matrix.postTranslate((getWidth()-tupian1.getWidth())/2, (getHeight()-tupian1.getHeight())/2);
			canvas.drawBitmap(tupian1, matrix, null);
		}
		
		super.onDraw(canvas);
		
	}
	boolean isCheck=false;
	//触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isCheck=true;
			Animation animation=AnimationUtils.loadAnimation(getContext(), R.anim.start);
			startAnimation(animation);
			invalidate();//请求页面重新绘制
			break;
        case MotionEvent.ACTION_UP:
        	isCheck=false;
			Animation animation1=AnimationUtils.loadAnimation(getContext(), R.anim.end);
			startAnimation(animation1);
			invalidate();//请求页面重新绘制
			break;

        case MotionEvent.ACTION_CANCEL:
        	isCheck=false;
			Animation animation2=AnimationUtils.loadAnimation(getContext(), R.anim.end);
			startAnimation(animation2);
			invalidate();//请求页面重新绘制
			break;
		}
		
		return true;
	}
}
