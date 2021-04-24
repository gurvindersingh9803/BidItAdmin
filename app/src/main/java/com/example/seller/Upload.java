package com.example.seller;

import android.util.Log;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String pPrice;
    private String pDesc;
    private String mImageUrl;
    private String mKey;
    private String sellerId;
    private String dateTime;



    public Upload() {
        //empty constructor needed
    }
    public Upload(String name, String imageUrl, String Price, String pDescs, String currentUserAdmin,String dateTimer) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        pDesc = pDescs;
        pPrice = Price;
        sellerId = currentUserAdmin;
        dateTime = dateTimer;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getImageUrl() {

        Log.i("gggggggggggggggggggg",mImageUrl);
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
