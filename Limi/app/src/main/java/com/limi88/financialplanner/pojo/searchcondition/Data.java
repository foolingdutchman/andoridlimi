
package com.limi88.financialplanner.pojo.searchcondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("status")
    @Expose
    private List<String> status = new ArrayList<String>();

    @SerializedName("bond_fund_type")
    @Expose
    private List<String> bondFundType = new ArrayList<String>();

    @SerializedName("fund_type")
    @Expose
    private List<String> fundType = new ArrayList<String>();

    @SerializedName("forbidden_period")
    @Expose
    private List<String> forbiddenPeriod = new ArrayList<String>();

    @SerializedName("min_start_money")
    @Expose
    private List<String> minStartMoney = new ArrayList<String>();

    @SerializedName("fund_rates.frontend_rebate_percent")
    @Expose
    private List<String> frontendRebatePercent = new ArrayList<String>();

    @SerializedName("fund_rates.backend_rebate_percent")
    @Expose
    private List<String> backendRebatePercent = new ArrayList<String>();

    @SerializedName("is_struct")
    @Expose
    private List<String> isStruct = new ArrayList<String>();

    @SerializedName("cat")
    @Expose
    private List<String> cat = new ArrayList<String>();

    @SerializedName("fin_product_org_id")
    @Expose
    private List<String> finProductOrgId = new ArrayList<String>();


    @SerializedName("channel")
    @Expose
    private List<String> channel = new ArrayList<String>();

    @SerializedName("trust_rates.expect_rate")
    @Expose
    private List<String> trustRatesExpectRate = new ArrayList<String>();
    @SerializedName("period")
    @Expose
    private List<String> period = new ArrayList<String>();

    @SerializedName("period_limit")
    @Expose
    private List<String> periodLimit = new ArrayList<String>();
    @SerializedName("invest_area")
    @Expose
    private List<String> investArea = new ArrayList<String>();
    @SerializedName("invest_city")
    @Expose
    private List<String> investCity = new ArrayList<String>();
    @SerializedName("distribute_type")
    @Expose
    private List<String> distributeType = new ArrayList<String>();
    @SerializedName("level")
    @Expose
    private List<String> level = new ArrayList<String>();
    @SerializedName("trust_rates.rebate_percent")
    @Expose
    private List<String> trustRatesRebatePercent = new ArrayList<String>();
    @SerializedName("distribute_big_small")
    @Expose
    private List<String> distributeBigSmall = new ArrayList<String>();
    @SerializedName("mortgage_rate")
    @Expose
    private List<String> mortgageRate = new ArrayList<String>();
    @SerializedName("keys")
    @Expose
    private List<String> keys = new ArrayList<String>();

    private HashMap<String, List<String>> datalist = new HashMap<>();

    /**
     * @return The status
     */
    public List<String> getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(List<String> status) {
        this.status = status;

    }

    /**
     * @return The trustRatesExpectRate
     */
    public List<String> getTrustRatesExpectRate() {
        return trustRatesExpectRate;

    }

    /**
     * @param trustRatesExpectRate The trust_rates.expect_rate
     */
    public void setTrustRatesExpectRate(List<String> trustRatesExpectRate) {
        this.trustRatesExpectRate = trustRatesExpectRate;

    }

    /**
     * @return The period
     */
    public List<String> getPeriod() {
        return period;
    }

    /**
     * @param period The period
     */
    public void setPeriod(List<String> period) {
        this.period = period;

    }

    /**
     * @return The investArea
     */
    public List<String> getInvestArea() {
        return investArea;
    }

    /**
     * @param investArea The invest_area
     */
    public void setInvestArea(List<String> investArea) {
        this.investArea = investArea;

    }

    /**
     * @return The investCity
     */
    public List<String> getInvestCity() {
        return investCity;
    }

    /**
     * @param investCity The invest_city
     */
    public void setInvestCity(List<String> investCity) {
        this.investCity = investCity;

    }

    /**
     * @return The distributeType
     */
    public List<String> getDistributeType() {
        return distributeType;
    }

    /**
     * @param distributeType The distribute_type
     */
    public void setDistributeType(List<String> distributeType) {
        this.distributeType = distributeType;

    }

    /**
     * @return The level
     */
    public List<String> getLevel() {
        return level;
    }

    /**
     * @param level The level
     */
    public void setLevel(List<String> level) {
        this.level = level;

    }

    /**
     * @return The trustRatesRebatePercent
     */
    public List<String> getTrustRatesRebatePercent() {
        return trustRatesRebatePercent;
    }

    /**
     * @param trustRatesRebatePercent The trust_rates.rebate_percent
     */
    public void setTrustRatesRebatePercent(List<String> trustRatesRebatePercent) {
        this.trustRatesRebatePercent = trustRatesRebatePercent;

    }

    /**
     * @return The distributeBigSmall
     */
    public List<String> getDistributeBigSmall() {
        return distributeBigSmall;
    }

    /**
     * @param distributeBigSmall The distribute_big_small
     */
    public void setDistributeBigSmall(List<String> distributeBigSmall) {
        this.distributeBigSmall = distributeBigSmall;

    }

    /**
     * @return The mortgageRate
     */
    public List<String> getMortgageRate() {
        return mortgageRate;
    }

    /**
     * @param mortgageRate The mortgage_rate
     */
    public void setMortgageRate(List<String> mortgageRate) {
        this.mortgageRate = mortgageRate;
    }


    public List<String> getBondFundType() {
        return bondFundType;
    }

    public void setBondFundType(List<String> bondFundType) {
        this.bondFundType = bondFundType;
    }

    public List<String> getFundType() {
        return fundType;
    }

    public void setFundType(List<String> fundType) {
        this.fundType = fundType;
    }

    public List<String> getForbiddenPeriod() {
        return forbiddenPeriod;
    }

    public void setForbiddenPeriod(List<String> forbiddenPeriod) {
        this.forbiddenPeriod = forbiddenPeriod;
    }

    public List<String> getMinStartMoney() {
        return minStartMoney;
    }

    public void setMinStartMoney(List<String> minStartMoney) {
        this.minStartMoney = minStartMoney;
    }

    public List<String> getFrontendRebatePercent() {
        return frontendRebatePercent;
    }

    public void setFrontendRebatePercent(List<String> frontendRebatePercent) {
        this.frontendRebatePercent = frontendRebatePercent;
    }

    public List<String> getBackendRebatePercent() {
        return backendRebatePercent;
    }

    public void setBackendRebatePercent(List<String> backendRebatePercent) {
        this.backendRebatePercent = backendRebatePercent;
    }

    public List<String> getIsStruct() {
        return isStruct;
    }

    public void setIsStruct(List<String> isStruct) {
        this.isStruct = isStruct;
    }

    public List<String> getCat() {
        return cat;
    }

    public void setCat(List<String> cat) {
        this.cat = cat;
    }

    public List<String> getFinProductOrgId() {
        return finProductOrgId;
    }

    public void setFinProductOrgId(List<String> finProductOrgId) {
        this.finProductOrgId = finProductOrgId;
    }

    public List<String> getChannel() {
        return channel;
    }

    public void setChannel(List<String> channel) {
        this.channel = channel;
    }

    public List<String> getPeriodLimit() {
        return periodLimit;
    }

    public void setPeriodLimit(List<String> periodLimit) {
        this.periodLimit = periodLimit;
    }

    /**
     * @return The keys
     */
    public List<String> getKeys() {
        return keys;
    }

    /**
     * @param keys The keys
     */
    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public HashMap<String, List<String>> getDatalist() {
        datalist.put("status", status);
        datalist.put("trust_rates.expect_rate", trustRatesExpectRate);
        datalist.put("period", trustRatesExpectRate);
        datalist.put("invest_area", investArea);
        datalist.put("invest_city", investCity);
        datalist.put("level", level);
        datalist.put("distribute_type", distributeType);
        datalist.put("trust_rates.rebate_percent", trustRatesRebatePercent);
        datalist.put("distribute_big_small", distributeBigSmall);
        datalist.put("mortgage_rate", mortgageRate);
        datalist.put("channel",channel);
        datalist.put("fund_rates.backend_rebate_percent",backendRebatePercent);
        datalist.put("fund_rates.frontend_rebate_percent",frontendRebatePercent);
        datalist.put("is_struct",isStruct);
        datalist.put("cat",cat);
        datalist.put("fin_product_org_id",finProductOrgId);
        datalist.put("min_start_money",minStartMoney);
        datalist.put("period_limit",periodLimit);
        datalist.put("forbidden_period",forbiddenPeriod);
        datalist.put("fund_type",fundType);
        datalist.put("bond_fund_type",bondFundType);
        return datalist;
    }

    public void setDatalist(HashMap<String, List<String>> datalist) {

        this.datalist = datalist;
    }
}
