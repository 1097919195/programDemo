package com.example.administrator.myapplication.bean;

/**
 * Created by Administrator on 2017/7/4.
 */

public class UserBean {
    private String mFirstName;
    private String mLastName;

    public UserBean(String firstname,String lastname){
        this.mFirstName=firstname;
        this.mLastName=lastname;
    }

    public String getFirstName(){
        return mFirstName;
    }

    public String getLastName(){
        return mLastName;
    }

}




