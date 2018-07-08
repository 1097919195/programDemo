package com.example.asus.price;

import android.app.Activity;
import android.graphics.Color;
import android.print.PageRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class MpAndroidActivity extends Activity {
    private BarChart chart;
    private XAxis xAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_android);
        initViews();

    }

    private void initViews() {
        chart = findViewById(R.id.chart);
        //基本设置
        xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        chart.setDrawGridBackground(false);//是否显示表格颜色
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.setTouchEnabled(false);//设置是否可以触摸
        chart.setDragEnabled(true);//设置是否拖曳
        chart.setScaleEnabled(true);// 设置是否可以缩放
        // y轴和比例尺
        chart.setDescription("四个季度");//数据描述
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        Legend legend=chart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

        //x轴数据，显示位置
        ArrayList<String> xVaues=new ArrayList<>();
        xVaues.add("一季度");
        xVaues.add("二季度");
        xVaues.add("三季度");
        xVaues.add("四季度");

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//数据位于底部
        //4、y轴数据
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        //new BarEntry(20, 0)前面代表数据，后面代码柱状图的位置；
        yValues.add(new BarEntry(20, 0));
        yValues.add(new BarEntry(18, 1));
        yValues.add(new BarEntry(4, 2));
        yValues.add(new BarEntry(45, 3));

        //5、设置显示的数字为整形
        BarDataSet barDataSet=new BarDataSet(yValues,"");
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                int n = (int) v;
                return n + "";
            }

        });
        //6、设置柱状图的颜色
        barDataSet.setColors(new int[]{Color.rgb(104, 202, 37), Color.rgb(192, 32, 32),
                Color.rgb(34, 129, 197), Color.rgb(175, 175, 175)});
        //7、显示，柱状图的宽度和动画效果
        BarData barData = new BarData(xVaues, barDataSet);
        barDataSet.setBarSpacePercent(40f);//值越大，柱状图就越宽度越小；
        chart.animateY(1000);
        chart.setData(barData); //



    }
}
