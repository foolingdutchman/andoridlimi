
package com.limi88.financialplanner.pojo.user;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class User implements Parcelable {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;
    @SerializedName("errcode")
    @Expose
    private int errcode;

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("user_authenticated")
    @Expose
    private String userAuthenticated;

    /**
     * 
     * @return
     *     The success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The code
     */
    public int getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The userAuthenticated
     */
    public String isUserAuthenticated() {
        return userAuthenticated;
    }

    /**
     * 
     * @param userAuthenticated
     *     The user_authenticated
     */
    public void setUserAuthenticated(String userAuthenticated) {
        this.userAuthenticated = userAuthenticated;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.errmsg);
        dest.writeInt(this.errcode);
        dest.writeInt(this.code);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.userAuthenticated);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.success = in.readByte() != 0;
        this.errmsg = in.readString();
        this.errcode = in.readInt();
        this.code = in.readInt();
        this.data = in.readParcelable(Data.class.getClassLoader());
        this.userAuthenticated = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
