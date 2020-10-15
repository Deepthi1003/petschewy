package com.example.petsandroid.Model;

import java.util.ArrayList;

public class OrderHistory {
    private String name,date,address,state;
    private ArrayList<String> prodid;

    public OrderHistory() {
    }

    public OrderHistory(String name, String date, String address, String state) {
        this.name = name;
        this.date = date;
        this.address = address;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<String> getProdid() {
        return prodid;
    }
}
