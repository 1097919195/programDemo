package com.example.wyq.myapplication;

/**
 * Created by Administrator on 2018/4/17.
 */

public class Bean {

    private String license1;
    private String name;
    private String balance;

    public Bean(String license1, String name, String balance) {
        this.balance=balance;
        this.license1=license1;
        this.name=name;
    }

    public String getLicense1() {
        return license1;
    }

    public void setLicense1(String license1) {
        this.license1 = license1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
