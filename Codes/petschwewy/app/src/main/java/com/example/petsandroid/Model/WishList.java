package com.example.petsandroid.Model;

public class WishList {
    private String pid, pname, price, descrip, discount;

    public WishList() {
    }

    public WishList(String pid, String pname, String price, String descrip, String discount) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.descrip = descrip;
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
