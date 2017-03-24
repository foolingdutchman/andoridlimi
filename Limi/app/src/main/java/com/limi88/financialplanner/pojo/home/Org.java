
package com.limi88.financialplanner.pojo.home;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Org {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("intro")
    @Expose
    private String intro;
    @SerializedName("concept")
    @Expose
    private String concept;
    @SerializedName("risk_ctrl")
    @Expose
    private String riskCtrl;

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
     *     The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 
     * @param avatar
     *     The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 
     * @return
     *     The intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 
     * @param intro
     *     The intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * 
     * @return
     *     The concept
     */
    public String getConcept() {
        return concept;
    }

    /**
     * 
     * @param concept
     *     The concept
     */
    public void setConcept(String concept) {
        this.concept = concept;
    }

    /**
     * 
     * @return
     *     The riskCtrl
     */
    public String getRiskCtrl() {
        return riskCtrl;
    }

    /**
     * 
     * @param riskCtrl
     *     The risk_ctrl
     */
    public void setRiskCtrl(String riskCtrl) {
        this.riskCtrl = riskCtrl;
    }

    @Override
    public String toString() {
        return "Org{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", intro='" + intro + '\'' +
                ", concept='" + concept + '\'' +
                ", riskCtrl='" + riskCtrl + '\'' +
                '}';
    }
}
