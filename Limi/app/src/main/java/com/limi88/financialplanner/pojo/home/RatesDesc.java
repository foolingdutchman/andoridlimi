
package com.limi88.financialplanner.pojo.home;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RatesDesc {



    @SerializedName("expect_rate")
    @Expose
    private String expectRate;
    @SerializedName("rebate_percent")
    @Expose
    private String rebatePercent;

    @SerializedName("base_rate")
    @Expose
    private String baseRate;
    @SerializedName("frontend_rebate_percent")
    @Expose
    private String frontendRebatePercent;
    @SerializedName("backend_rebate_percent")
    @Expose
    private String backendRebatePercent;
    @SerializedName("amount_desc")
    @Expose
    private String amountDesc;

    /**
     * 
     * @return
     *     The baseRate
     */
    public String getBaseRate() {
        return baseRate;
    }

    /**
     * 
     * @param baseRate
     *     The base_rate
     */
    public void setBaseRate(String baseRate) {
        this.baseRate = baseRate;
    }

    public String getExpectRate() {
        return expectRate;
    }

    public void setExpectRate(String expectRate) {
        this.expectRate = expectRate;
    }

    public String getRebatePercent() {
        return rebatePercent;
    }

    public void setRebatePercent(String rebatePercent) {
        this.rebatePercent = rebatePercent;
    }

    /**
     * 
     * @return
     *     The frontendRebatePercent
     */
    public String getFrontendRebatePercent() {
        return frontendRebatePercent;
    }

    /**
     * 
     * @param frontendRebatePercent
     *     The frontend_rebate_percent
     */
    public void setFrontendRebatePercent(String frontendRebatePercent) {
        this.frontendRebatePercent = frontendRebatePercent;
    }

    /**
     * 
     * @return
     *     The backendRebatePercent
     */
    public String getBackendRebatePercent() {
        return backendRebatePercent;
    }

    /**
     * 
     * @param backendRebatePercent
     *     The backend_rebate_percent
     */
    public void setBackendRebatePercent(String backendRebatePercent) {
        this.backendRebatePercent = backendRebatePercent;
    }

    /**
     * 
     * @return
     *     The amountDesc
     */
    public String getAmountDesc() {
        return amountDesc;
    }

    /**
     * 
     * @param amountDesc
     *     The amount_desc
     */
    public void setAmountDesc(String amountDesc) {
        this.amountDesc = amountDesc;
    }

}
