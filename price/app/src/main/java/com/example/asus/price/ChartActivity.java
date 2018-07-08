package com.example.asus.price;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


public class ChartActivity extends Activity {
    private LineChart chart;
    private ArrayList<String> Xdata = new ArrayList<>();
    private ArrayList<Entry> Ydata = new ArrayList<>();
    private ArrayList<Entry> Ydata1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chart);
        chart = findViewById(R.id.chart);

        initData();
    }

    private void initData() {
        XAxis xAxis = chart.getXAxis();
        //设置x轴文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置描述文字
        chart.setDescription("7天走势图");
        //模拟一个x轴的数据  12/1 12/2 ... 12/7
         for(int i=0;i<8;i++){
             Xdata.add("12/"+i);
         }
        //模拟一组y轴数据(存放y轴数据的是一个Entry的ArrayList) 他是构建LineDataSet的参数之一
        for(int i=0;i<8;i++){
           Ydata.add(new Entry(i,i));
        }

        LineDataSet dataSet=new LineDataSet(Ydata,"双色球");
        //模拟第二组组y轴数据(存放y轴数据的是一个Entry的ArrayList) 他是构建LineDataSet的参数之一
        Ydata1.add(new Entry(10,12));
        Ydata1.add(new Entry(1,12));
        Ydata1.add(new Entry(5,11));
        Ydata1.add(new Entry(6,22));
        Ydata1.add(new Entry(3,15));
        Ydata1.add(new Entry(10,30));
        Ydata1.add(new Entry(20,28));
        Ydata1.add(new Entry(4,30));

        LineDataSet dataSet1=new LineDataSet(Ydata1,"天气情况");
        dataSet1.setColor(Color.RED);

        //构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
        ArrayList<LineDataSet> dataSets=new ArrayList<>();
        //将数据加入dataSets
        dataSets.add(dataSet1);
        dataSets.add(dataSet);

        //构建一个LineData  将dataSets放入
        LineData data=new LineData(Xdata,dataSets);
        chart.setData(data);

    }
}
