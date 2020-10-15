package com.example.petsandroid.Model;

import java.util.ArrayList;
import java.util.Map;

public class AdminOrders extends ArrayList<AdminOrders> {
    private String name, phone, address, city, state, date, time, totalAmount;
    private ArrayList<String> product;
//    public AdminOrders(ArrayList<AdminOrders> list) {
//    }

    public AdminOrders(String name, String phone, String address, String city, String state, String date, String time, String totalAmount) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.product = product;
    }



    public AdminOrders(AdminOrders adminOrders) {
        this.setName(adminOrders.getName());
        this.setPhone(adminOrders.getPhone());
        this.setAddress(adminOrders.getAddress());
        this.setCity(adminOrders.getCity());
        this.setState(adminOrders.getState());
        this.setDate(adminOrders.getDate());
        this.setTime(adminOrders.getTime());
        this.setTotalAmount(adminOrders.getTotalAmount());
    }
//    public AdminOrders(Map map) {
//    }

    public ArrayList<String> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<String> product) {
        this.product = product;
    }

    public AdminOrders(Map params) {
        //this.setProduct((ArrayList<String>) params.get("Product"));
        this.setName((String) "Name: "+params.get("name"));
        this.setPhone((String) "Phone: "+params.get("phone"));
        this.setAddress((String) "Address: "+params.get("address"));
        this.setCity((String) "City :"+params.get("city"));
        this.setState((String) "Status: "+params.get("state"));
        this.setDate((String) "Date:"+params.get("date"));
        this.setTime((String) "Time:"+params.get("time"));
        this.setTotalAmount((String) "Total Amount: "+params.get("totalAmount"));

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

}
