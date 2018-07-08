package com.example.wyq.myapplication;

import android.location.Location;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class WeatherBean {

    private String high;
    private String low;

    public WeatherBean(String high, String low) {
        this.high=high;
        this.low=low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }
}
