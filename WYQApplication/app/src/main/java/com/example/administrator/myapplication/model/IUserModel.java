package com.example.administrator.myapplication.model;

import com.example.administrator.myapplication.bean.UserBean;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface IUserModel {
    void setID (int id);
    void setFirstName (String firstName);
    void setLastName (String lastName);
    int getID();
    UserBean load (int id);//通过id读取user信息,返回一个UserBean
}
