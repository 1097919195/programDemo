package com.example.administrator.myapplication.view;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface IUserView {
    int getID();
    String getFirstName();
    String getLastName();

    void setFirstName(String firstName);
    void setLastName(String lastName);
}
