package com.limi88.financialplanner.pojo.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hehao
 * Date on 16/11/17.
 * Email: hao.he@sunanzq.com
 */
public class Tag {
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("color")
    @Expose
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
