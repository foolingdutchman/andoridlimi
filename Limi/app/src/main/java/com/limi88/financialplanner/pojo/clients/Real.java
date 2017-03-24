
package com.limi88.financialplanner.pojo.clients;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Real implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("province_id")
    @Expose
    private int provinceId;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level The level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags The tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.avatar);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.level);
        dest.writeString(this.status);
        dest.writeString(this.tags);
        dest.writeString(this.birthday);
        dest.writeString(this.gender);
        dest.writeInt(this.provinceId);
    }

    public Real() {
    }

    protected Real(Parcel in) {
        this.id = in.readInt();
        this.avatar = in.readString();
        this.phone = in.readString();
        this.name = in.readString();
        this.level = in.readString();
        this.status = in.readString();
        this.tags = in.readString();
        this.birthday = in.readString();
        this.gender = in.readString();
        this.provinceId = in.readInt();
    }

    public static final Parcelable.Creator<Real> CREATOR = new Parcelable.Creator<Real>() {
        @Override
        public Real createFromParcel(Parcel source) {
            return new Real(source);
        }

        @Override
        public Real[] newArray(int size) {
            return new Real[size];
        }
    };
}


