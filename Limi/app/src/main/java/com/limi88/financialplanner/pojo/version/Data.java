package com.limi88.financialplanner.pojo.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hehao
 * Date on 16/11/9.
 * Email: hao.he@sunanzq.com
 */
public class Data {
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

    public String isNote() {
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

    public String getNote() {
        return note;
    }


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
