package com.limi88.financialplanner.pojo.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.limi88.financialplanner.pojo.home.Product;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hehao on 16/9/12.
 */
public class ProductData {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("data")
    @Expose
    private List<Product> data= new ArrayList<Product>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
