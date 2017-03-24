
package com.limi88.financialplanner.pojo.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




@Generated("org.jsonschema2pojo")
public class Data implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("android_version")
    @Expose
    private AndroidVersion androidVersion;

    @SerializedName("new_info")
    @Expose
    private boolean newInfo;

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("province_id")
    @Expose
    private int provinceId;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("new_info_count")
    @Expose
    private int newInfoCount;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("personal_images")
    @Expose
    private List<String> personalImages = new ArrayList<String>();

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The provinceId
     */
    public int getProvinceId() {
        return provinceId;
    }

    /**
     * 
     * @param provinceId
     *     The province_id
     */
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * 
     * @return
     *     The organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * 
     * @param organization
     *     The organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * 
     * @return
     *     The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 
     * @param desc
     *     The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 
     * @return
     *     The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 
     * @param avatarUrl
     *     The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 
     * @return
     *     The personalImages
     */
    public List<String> getPersonalImages() {
        return personalImages;
    }

    /**
     * 
     * @param personalImages
     *     The personal_images
     */
    public void setPersonalImages(List<String> personalImages) {
        this.personalImages = personalImages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AndroidVersion getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(AndroidVersion androidVersion) {
        this.androidVersion = androidVersion;
    }


    public boolean getNewInfo() {
        return newInfo;
    }

    public void setNewInfo(boolean newInfo) {
        this.newInfo = newInfo;
    }

    public int getNewInfoCount() {
        return newInfoCount;
    }

    public void setNewInfoCount(int newInfoCount) {
        this.newInfoCount = newInfoCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeParcelable(this.androidVersion, flags);
        dest.writeByte(this.newInfo ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeString(this.gender);
        dest.writeInt(this.provinceId);
        dest.writeString(this.organization);
        dest.writeString(this.desc);
        dest.writeInt(this.newInfoCount);
        dest.writeString(this.avatarUrl);
        dest.writeStringList(this.personalImages);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.androidVersion = in.readParcelable(AndroidVersion.class.getClassLoader());
        this.newInfo = in.readByte() != 0;
        this.id = in.readInt();
        this.gender = in.readString();
        this.provinceId = in.readInt();
        this.organization = in.readString();
        this.desc = in.readString();
        this.newInfoCount = in.readInt();
        this.avatarUrl = in.readString();
        this.personalImages = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
