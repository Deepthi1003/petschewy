package com.example.petsandroid;

/**
 * Created by abdalla on 1/12/18.
 */

public class ProductData {

    private String productName;
    private String productDescription;
    private int productImage;
    private int productPrice;



    public ProductData(String productName, String productDescription, int productImage, int productPrice ) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productPrice=productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getProductImage() {
        return productImage;
    }
    public int getProductPrice() {
        return productPrice;
    }
}
