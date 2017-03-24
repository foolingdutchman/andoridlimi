package com.limi88.financialplanner.pojo.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hehao on 16/9/9.
 */
public class InsurOrg {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("avatar")
    @Expose
    public Avatar avatar;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("typeable_name")
    @Expose
    public String typeableName;
    @SerializedName("intro")
    @Expose
    public String intro;
    @SerializedName("concept")
    @Expose
    public String concept;
    @SerializedName("risk_ctrl")
    @Expose
    public String riskCtrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTypeableName() {
        return typeableName;
    }

    public void setTypeableName(String typeableName) {
        this.typeableName = typeableName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getRiskCtrl() {
        return riskCtrl;
    }

    public void setRiskCtrl(String riskCtrl) {
        this.riskCtrl = riskCtrl;
    }

    @Override
    public String toString() {
        return "InsurOrg{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar=" + avatar +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", typeableName='" + typeableName + '\'' +
                ", intro='" + intro + '\'' +
                ", concept='" + concept + '\'' +
                ", riskCtrl='" + riskCtrl + '\'' +
                '}';
    }
}
