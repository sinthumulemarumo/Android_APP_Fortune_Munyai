package com.example.fortune.pnpstore.Interfaces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Users implements Serializable {

    @SerializedName ("CustomerID")
    public int id;

    @SerializedName("FName")
    public String FName;

    @SerializedName("LName")
    public String LName;

    @SerializedName("UserName")
    public String username;

    @SerializedName("Email")
    public String email;


}
