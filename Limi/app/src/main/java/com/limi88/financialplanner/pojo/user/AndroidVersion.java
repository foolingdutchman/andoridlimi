package com.limi88.financialplanner.pojo.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hehao
 * Date on 16/11/9.
 * Email: hao.he@sunanzq.com
 */
public class AndroidVersion implements Parcelable {
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("need_update")
    @Expose
    private boolean needUpdate;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("file_url")
    @Expose
    private String fileUrl;
    @SerializedName("show_update")
    @Expose
    private boolean showUpdate;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isShowUpdate() {
        return showUpdate;
    }

    public void setShowUpdate(boolean showUpdate) {
        this.showUpdate = showUpdate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version);
        dest.writeByte(this.needUpdate ? (byte) 1 : (byte) 0);
        dest.writeString(this.note);
        dest.writeString(this.fileUrl);
        dest.writeByte(this.showUpdate ? (byte) 1 : (byte) 0);
    }

    public AndroidVersion() {
    }

    protected AndroidVersion(Parcel in) {
        this.version = in.readString();
        this.needUpdate = in.readByte() != 0;
        this.note = in.readString();
        this.fileUrl = in.readString();
        this.showUpdate = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AndroidVersion> CREATOR = new Parcelable.Creator<AndroidVersion>() {
        @Override
        public AndroidVersion createFromParcel(Parcel source) {
            return new AndroidVersion(source);
        }

        @Override
        public AndroidVersion[] newArray(int size) {
            return new AndroidVersion[size];
        }
    };
}
