package com.example.fortune.pnpstore.Interfaces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Products implements Serializable{

    @SerializedName("ProductID")
    public int id;

    @SerializedName("ProductName")
    public String name;

    @SerializedName("Description")
    public String description;

    @SerializedName("Price")
    public double price;

    @SerializedName("IMG_URL")
    public String img_url;
}
