
package com.limi88.financialplanner.pojo.home;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Product {



    @SerializedName("short_name")
    @Expose
    private String shortName;


    @SerializedName("period_limit")
    @Expose
    private String periodLimit;

    @SerializedName("org_avatar_url")
    @Expose
    private String orgAvatarUrl;
    @SerializedName("financing_body")
    @Expose
    private String financingBody;
    @SerializedName("financing_body_desc")
    @Expose
    private String financingBodyDesc;
    @SerializedName("scale_desc")
    @Expose
    private String scaleDesc;
    @SerializedName("start_date")
    @Expose
    private String startDate;

    @SerializedName("distribute_type")
    @Expose
    private String distributeType;
    @SerializedName("period_desc")
    @Expose
    private String periodDesc;
    @SerializedName("use_for")
    @Expose
    private String useFor;
    @SerializedName("repayment_source")
    @Expose
    private String repaymentSource;
    @SerializedName("highlights")
    @Expose
    private String highlights;
    @SerializedName("guarantee_body")
    @Expose
    private String guaranteeBody;
    @SerializedName("guarantee_body_desc")
    @Expose
    private String guaranteeBodyDesc;
    @SerializedName("min_start_money_desc")
    @Expose
    private String minStartMoneyDesc;
    @SerializedName("invest_area")
    @Expose
    private String investArea;
    @SerializedName("invest_city")
    @Expose
    private String investCity;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("distribute_big_small")
    @Expose
    private String distributeBigSmall;
    @SerializedName("mortgage_rate")
    @Expose
    private String mortgageRate;
    @SerializedName("risk_ctrl")
    @Expose
    private String riskCtrl;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("product_type_name")
    @Expose
    private String productTypeName;
    @SerializedName("progress_desc")
    @Expose
    private String progressDesc;
    @SerializedName("progress_updated_at")
    @Expose
    private String progressUpdatedAt;
    @SerializedName("account_name")
    @Expose
    private String accountName;
    @SerializedName("account_bank")
    @Expose
    private String accountBank;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("account_remark")
    @Expose
    private String accountRemark;

    @SerializedName("fund_type")
    @Expose
    private String fundType;
    @SerializedName("org")
    @Expose
    private Org org;
    @SerializedName("fund_org_name")
    @Expose
    private String fundOrgName;
    @SerializedName("open_date")
    @Expose
    private String openDate;
    @SerializedName("first_found")
    @Expose
    private String firstFound;
    @SerializedName("forbidden_period")
    @Expose
    private String forbiddenPeriod;
    @SerializedName("sure_forbidden_period")
    @Expose
    private String sureForbiddenPeriod;
    @SerializedName("total_net_worth")
    @Expose
    private String totalNetWorth;
    @SerializedName("min_start_money")
    @Expose
    private String minStartMoney;
    @SerializedName("security_brokerage")
    @Expose
    private String securityBrokerage;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("redemption_fee")
    @Expose
    private String redemptionFee;
    @SerializedName("manage_fee_desc")
    @Expose
    private String manageFeeDesc;
    @SerializedName("subscription_fee")
    @Expose
    private String subscriptionFee;
    @SerializedName("performance_pay")
    @Expose
    private String performancePay;
    @SerializedName("account_trusteeship_bank")
    @Expose
    private String accountTrusteeshipBank;
    @SerializedName("invest_concept")
    @Expose
    private String investConcept;
    @SerializedName("is_struct")
    @Expose
    private boolean isStruct;
    @SerializedName("manager_team")
    @Expose
    private ManagerTeam managerTeam;


    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("suggested")
    @Expose
    public int suggested;
    @SerializedName("web_link")
    @Expose
    public String webLink;
    @SerializedName("manager_name")
    @Expose
    public String managerName;
    @SerializedName("manager_phone")
    @Expose
    public String managerPhone;
    @SerializedName("counter")
    @Expose
    public Counter counter;
    @SerializedName("is_favourite")
    @Expose
    public boolean isFavourite;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("cat")
    @Expose
    public String cat = new String();
    @SerializedName("fee_mode")
    @Expose
    public String feeMode;
    @SerializedName("inner_status")
    @Expose
    public String innerStatus;
    @SerializedName("fin_product_org_id")
    @Expose
    public String finProductOrgId;
    @SerializedName("service_manager_id")
    @Expose
    public String serviceManagerId;
    @SerializedName("age_sprd")
    @Expose
    public String ageSprd;
    @SerializedName("insur_sprd")
    @Expose
    public String insurSprd;
    @SerializedName("org_name")
    @Expose
    public String orgName;
    @SerializedName("memo")
    @Expose
    public String memo;
    @SerializedName("fee_period")
    @Expose
    public Object feePeriod;
    @SerializedName("intro")
    @Expose
    public List<Intro> intro = new ArrayList<Intro>();
    @SerializedName("guarantee")
    @Expose
    public String guarantee;
    @SerializedName("policy")
    @Expose
    public List<Object> policy = new ArrayList<Object>();
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("tags")
    @Expose
    public List<Tag> tags=new ArrayList<Tag>();
    @SerializedName("files")
    @Expose
    public List<Object> files = new ArrayList<Object>();
    @SerializedName("insur_org")
    @Expose
    public InsurOrg insurOrg;
    @SerializedName("rates_desc")
    @Expose
    public List<RatesDesc> ratesDesc = new ArrayList<RatesDesc>();

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The managerName
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * 
     * @param managerName
     *     The manager_name
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    /**
     * 
     * @return
     *     The managerPhone
     */
    public String getManagerPhone() {
        return managerPhone;
    }

    /**
     * 
     * @param managerPhone
     *     The manager_phone
     */
    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * @param shortName
     *     The short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

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
     *     The suggested
     */
    public int getSuggested() {
        return suggested;
    }

    /**
     * 
     * @param suggested
     *     The suggested
     */
    public void setSuggested(int suggested) {
        this.suggested = suggested;
    }

    /**
     * 
     * @return
     *     The orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 
     * @param orgName
     *     The org_name
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 
     * @return
     *     The orgAvatarUrl
     */
    public String getOrgAvatarUrl() {
        return orgAvatarUrl;
    }

    /**
     * 
     * @param orgAvatarUrl
     *     The org_avatar_url
     */
    public void setOrgAvatarUrl(String orgAvatarUrl) {
        this.orgAvatarUrl = orgAvatarUrl;
    }

    /**
     * 
     * @return
     *     The financingBody
     */
    public String getFinancingBody() {
        return financingBody;
    }

    /**
     * 
     * @param financingBody
     *     The financing_body
     */
    public void setFinancingBody(String financingBody) {
        this.financingBody = financingBody;
    }

    /**
     * 
     * @return
     *     The financingBodyDesc
     */
    public String getFinancingBodyDesc() {
        return financingBodyDesc;
    }

    /**
     * 
     * @param financingBodyDesc
     *     The financing_body_desc
     */
    public void setFinancingBodyDesc(String financingBodyDesc) {
        this.financingBodyDesc = financingBodyDesc;
    }

    /**
     * 
     * @return
     *     The scaleDesc
     */
    public String getScaleDesc() {
        return scaleDesc;
    }

    /**
     * 
     * @param scaleDesc
     *     The scale_desc
     */
    public void setScaleDesc(String scaleDesc) {
        this.scaleDesc = scaleDesc;
    }

    /**
     * 
     * @return
     *     The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The distributeType
     */
    public String getDistributeType() {
        return distributeType;
    }

    /**
     * 
     * @param distributeType
     *     The distribute_type
     */
    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    /**
     * 
     * @return
     *     The periodDesc
     */
    public String getPeriodDesc() {
        return periodDesc;
    }

    /**
     * 
     * @param periodDesc
     *     The period_desc
     */
    public void setPeriodDesc(String periodDesc) {
        this.periodDesc = periodDesc;
    }

    /**
     * 
     * @return
     *     The useFor
     */
    public String getUseFor() {
        return useFor;
    }

    /**
     * 
     * @param useFor
     *     The use_for
     */
    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    /**
     * 
     * @return
     *     The repaymentSource
     */
    public String getRepaymentSource() {
        return repaymentSource;
    }

    /**
     * 
     * @param repaymentSource
     *     The repayment_source
     */
    public void setRepaymentSource(String repaymentSource) {
        this.repaymentSource = repaymentSource;
    }

    /**
     * 
     * @return
     *     The highlights
     */
    public String getHighlights() {
        return highlights;
    }

    /**
     * 
     * @param highlights
     *     The highlights
     */
    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    /**
     * 
     * @return
     *     The guaranteeBody
     */
    public String getGuaranteeBody() {
        return guaranteeBody;
    }

    /**
     * 
     * @param guaranteeBody
     *     The guarantee_body
     */
    public void setGuaranteeBody(String guaranteeBody) {
        this.guaranteeBody = guaranteeBody;
    }

    /**
     * 
     * @return
     *     The guaranteeBodyDesc
     */
    public String getGuaranteeBodyDesc() {
        return guaranteeBodyDesc;
    }

    /**
     * 
     * @param guaranteeBodyDesc
     *     The guarantee_body_desc
     */
    public void setGuaranteeBodyDesc(String guaranteeBodyDesc) {
        this.guaranteeBodyDesc = guaranteeBodyDesc;
    }

    /**
     * 
     * @return
     *     The minStartMoneyDesc
     */
    public String getMinStartMoneyDesc() {
        return minStartMoneyDesc;
    }

    /**
     * 
     * @param minStartMoneyDesc
     *     The min_start_money_desc
     */
    public void setMinStartMoneyDesc(String minStartMoneyDesc) {
        this.minStartMoneyDesc = minStartMoneyDesc;
    }

    /**
     * 
     * @return
     *     The investArea
     */
    public String getInvestArea() {
        return investArea;
    }

    /**
     * 
     * @param investArea
     *     The invest_area
     */
    public void setInvestArea(String investArea) {
        this.investArea = investArea;
    }

    /**
     * 
     * @return
     *     The investCity
     */
    public String getInvestCity() {
        return investCity;
    }

    /**
     * 
     * @param investCity
     *     The invest_city
     */
    public void setInvestCity(String investCity) {
        this.investCity = investCity;
    }

    /**
     * 
     * @return
     *     The level
     */
    public String getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 
     * @return
     *     The distributeBigSmall
     */
    public String getDistributeBigSmall() {
        return distributeBigSmall;
    }

    /**
     * 
     * @param distributeBigSmall
     *     The distribute_big_small
     */
    public void setDistributeBigSmall(String distributeBigSmall) {
        this.distributeBigSmall = distributeBigSmall;
    }

    /**
     * 
     * @return
     *     The mortgageRate
     */
    public String getMortgageRate() {
        return mortgageRate;
    }

    /**
     * 
     * @param mortgageRate
     *     The mortgage_rate
     */
    public void setMortgageRate(String mortgageRate) {
        this.mortgageRate = mortgageRate;
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

    /**
     * 
     * @return
     *     The remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark
     *     The remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * @return
     *     The progress
     */
    public String getProgress() {
        return progress;
    }

    /**
     * 
     * @param progress
     *     The progress
     */
    public void setProgress(String progress) {
        this.progress = progress;
    }

    /**
     * 
     * @return
     *     The progressDesc
     */
    public String getProgressDesc() {
        return progressDesc;
    }

    /**
     * 
     * @param progressDesc
     *     The progress_desc
     */
    public void setProgressDesc(String progressDesc) {
        this.progressDesc = progressDesc;
    }

    /**
     * 
     * @return
     *     The progressUpdatedAt
     */
    public String getProgressUpdatedAt() {
        return progressUpdatedAt;
    }

    /**
     * 
     * @param progressUpdatedAt
     *     The progress_updated_at
     */
    public void setProgressUpdatedAt(String progressUpdatedAt) {
        this.progressUpdatedAt = progressUpdatedAt;
    }

    /**
     * 
     * @return
     *     The accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 
     * @param accountName
     *     The account_name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 
     * @return
     *     The accountBank
     */
    public String getAccountBank() {
        return accountBank;
    }

    /**
     * 
     * @param accountBank
     *     The account_bank
     */
    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    /**
     * 
     * @return
     *     The accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 
     * @param accountNumber
     *     The account_number
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 
     * @return
     *     The accountRemark
     */
    public String getAccountRemark() {
        return accountRemark;
    }

    /**
     * 
     * @param accountRemark
     *     The account_remark
     */
    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    /**
     * 
     * @return
     *     The ratesDesc
     */
    public List<RatesDesc> getRatesDesc() {
        return ratesDesc;
    }

    /**
     * 
     * @param ratesDesc
     *     The rates_desc
     */
    public void setRatesDesc(List<RatesDesc> ratesDesc) {
        this.ratesDesc = ratesDesc;
    }

    /**
     * 
     * @return
     *     The files
     */
    public List<Object> getFiles() {
        return files;
    }

    /**
     * 
     * @param files
     *     The files
     */
    public void setFiles(List<Object> files) {
        this.files = files;
    }

    /**
     * 
     * @return
     *     The isFavourite
     */
    public boolean isIsFavourite() {
        return isFavourite;
    }

    /**
     * 
     * @param isFavourite
     *     The is_favourite
     */
    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    /**
     * 
     * @return
     *     The counter
     */
    public Counter getCounter() {
        return counter;
    }

    /**
     * 
     * @param counter
     *     The counter
     */
    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    /**
     * 
     * @return
     *     The fundType
     */
    public String getFundType() {
        return fundType;
    }

    /**
     * 
     * @param fundType
     *     The fund_type
     */
    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    /**
     * 
     * @return
     *     The org
     */
    public Org getOrg() {
        return org;
    }

    /**
     * 
     * @param org
     *     The org
     */
    public void setOrg(Org org) {
        this.org = org;
    }

    /**
     * 
     * @return
     *     The fundOrgName
     */
    public String getFundOrgName() {
        return fundOrgName;
    }

    /**
     * 
     * @param fundOrgName
     *     The fund_org_name
     */
    public void setFundOrgName(String fundOrgName) {
        this.fundOrgName = fundOrgName;
    }

    /**
     * 
     * @return
     *     The openDate
     */
    public String getOpenDate() {
        return openDate;
    }

    /**
     * 
     * @param openDate
     *     The open_date
     */
    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    /**
     * 
     * @return
     *     The firstFound
     */
    public String getFirstFound() {
        return firstFound;
    }

    /**
     * 
     * @param firstFound
     *     The first_found
     */
    public void setFirstFound(String firstFound) {
        this.firstFound = firstFound;
    }

    /**
     * 
     * @return
     *     The forbiddenPeriod
     */
    public String getForbiddenPeriod() {
        return forbiddenPeriod;
    }

    /**
     * 
     * @param forbiddenPeriod
     *     The forbidden_period
     */
    public void setForbiddenPeriod(String forbiddenPeriod) {
        this.forbiddenPeriod = forbiddenPeriod;
    }

    /**
     * 
     * @return
     *     The sureForbiddenPeriod
     */
    public String getSureForbiddenPeriod() {
        return sureForbiddenPeriod;
    }

    /**
     * 
     * @param sureForbiddenPeriod
     *     The sure_forbidden_period
     */
    public void setSureForbiddenPeriod(String sureForbiddenPeriod) {
        this.sureForbiddenPeriod = sureForbiddenPeriod;
    }

    /**
     * 
     * @return
     *     The totalNetWorth
     */
    public String getTotalNetWorth() {
        return totalNetWorth;
    }

    /**
     * 
     * @param totalNetWorth
     *     The total_net_worth
     */
    public void setTotalNetWorth(String totalNetWorth) {
        this.totalNetWorth = totalNetWorth;
    }

    /**
     * 
     * @return
     *     The minStartMoney
     */
    public String getMinStartMoney() {
        return minStartMoney;
    }

    /**
     * 
     * @param minStartMoney
     *     The min_start_money
     */
    public void setMinStartMoney(String minStartMoney) {
        this.minStartMoney = minStartMoney;
    }

    /**
     * 
     * @return
     *     The securityBrokerage
     */
    public String getSecurityBrokerage() {
        return securityBrokerage;
    }

    /**
     * 
     * @param securityBrokerage
     *     The security_brokerage
     */
    public void setSecurityBrokerage(String securityBrokerage) {
        this.securityBrokerage = securityBrokerage;
    }

    /**
     * 
     * @return
     *     The channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 
     * @param channel
     *     The channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 
     * @return
     *     The redemptionFee
     */
    public String getRedemptionFee() {
        return redemptionFee;
    }

    /**
     * 
     * @param redemptionFee
     *     The redemption_fee
     */
    public void setRedemptionFee(String redemptionFee) {
        this.redemptionFee = redemptionFee;
    }

    /**
     * 
     * @return
     *     The manageFeeDesc
     */
    public String getManageFeeDesc() {
        return manageFeeDesc;
    }

    /**
     * 
     * @param manageFeeDesc
     *     The manage_fee_desc
     */
    public void setManageFeeDesc(String manageFeeDesc) {
        this.manageFeeDesc = manageFeeDesc;
    }

    /**
     * 
     * @return
     *     The subscriptionFee
     */
    public String getSubscriptionFee() {
        return subscriptionFee;
    }

    /**
     * 
     * @param subscriptionFee
     *     The subscription_fee
     */
    public void setSubscriptionFee(String subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
    }

    /**
     * 
     * @return
     *     The performancePay
     */
    public String getPerformancePay() {
        return performancePay;
    }

    /**
     * 
     * @param performancePay
     *     The performance_pay
     */
    public void setPerformancePay(String performancePay) {
        this.performancePay = performancePay;
    }

    /**
     * 
     * @return
     *     The accountTrusteeshipBank
     */
    public String getAccountTrusteeshipBank() {
        return accountTrusteeshipBank;
    }

    /**
     * 
     * @param accountTrusteeshipBank
     *     The account_trusteeship_bank
     */
    public void setAccountTrusteeshipBank(String accountTrusteeshipBank) {
        this.accountTrusteeshipBank = accountTrusteeshipBank;
    }

    /**
     * 
     * @return
     *     The investConcept
     */
    public String getInvestConcept() {
        return investConcept;
    }

    /**
     * 
     * @param investConcept
     *     The invest_concept
     */
    public void setInvestConcept(String investConcept) {
        this.investConcept = investConcept;
    }

    /**
     * 
     * @return
     *     The isStruct
     */
    public boolean isIsStruct() {
        return isStruct;
    }

    /**
     * 
     * @param isStruct
     *     The is_struct
     */
    public void setIsStruct(boolean isStruct) {
        this.isStruct = isStruct;
    }

    /**
     * 
     * @return
     *     The managerTeam
     */
    public ManagerTeam getManagerTeam() {
        return managerTeam;
    }

    /**
     * 
     * @param managerTeam
     *     The manager_team
     */
    public void setManagerTeam(ManagerTeam managerTeam) {
        this.managerTeam = managerTeam;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public boolean isStruct() {
        return isStruct;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode;
    }

    public String getInnerStatus() {
        return innerStatus;
    }

    public void setInnerStatus(String innerStatus) {
        this.innerStatus = innerStatus;
    }

    public String getFinProductOrgId() {
        return finProductOrgId;
    }

    public void setFinProductOrgId(String finProductOrgId) {
        this.finProductOrgId = finProductOrgId;
    }

    public String getServiceManagerId() {
        return serviceManagerId;
    }

    public void setServiceManagerId(String serviceManagerId) {
        this.serviceManagerId = serviceManagerId;
    }

    public String getAgeSprd() {
        return ageSprd;
    }

    public void setAgeSprd(String ageSprd) {
        this.ageSprd = ageSprd;
    }

    public String getInsurSprd() {
        return insurSprd;
    }

    public void setInsurSprd(String insurSprd) {
        this.insurSprd = insurSprd;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Object getFeePeriod() {
        return feePeriod;
    }

    public void setFeePeriod(Object feePeriod) {
        this.feePeriod = feePeriod;
    }

    public List<Intro> getIntro() {
        return intro;
    }

    public void setIntro(List<Intro> intro) {
        this.intro = intro;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public List<Object> getPolicy() {
        return policy;
    }

    public void setPolicy(List<Object> policy) {
        this.policy = policy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public InsurOrg getInsurOrg() {
        return insurOrg;
    }

    public void setInsurOrg(InsurOrg insurOrg) {
        this.insurOrg = insurOrg;
    }

    public String getPeriodLimit() {
        return periodLimit;
    }

    public void setPeriodLimit(String periodLimit) {
        this.periodLimit = periodLimit;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Product{" +
                "shortName='" + shortName + '\'' +
                ", periodLimit='" + periodLimit + '\'' +
                ", orgAvatarUrl='" + orgAvatarUrl + '\'' +
                ", financingBody='" + financingBody + '\'' +
                ", financingBodyDesc='" + financingBodyDesc + '\'' +
                ", scaleDesc='" + scaleDesc + '\'' +
                ", startDate='" + startDate + '\'' +
                ", distributeType='" + distributeType + '\'' +
                ", periodDesc='" + periodDesc + '\'' +
                ", useFor='" + useFor + '\'' +
                ", repaymentSource='" + repaymentSource + '\'' +
                ", highlights='" + highlights + '\'' +
                ", guaranteeBody='" + guaranteeBody + '\'' +
                ", guaranteeBodyDesc='" + guaranteeBodyDesc + '\'' +
                ", minStartMoneyDesc='" + minStartMoneyDesc + '\'' +
                ", investArea='" + investArea + '\'' +
                ", investCity='" + investCity + '\'' +
                ", level='" + level + '\'' +
                ", distributeBigSmall='" + distributeBigSmall + '\'' +
                ", mortgageRate='" + mortgageRate + '\'' +
                ", riskCtrl='" + riskCtrl + '\'' +
                ", remark='" + remark + '\'' +
                ", progress='" + progress + '\'' +
                ", productTypeName='" + productTypeName + '\'' +
                ", progressDesc='" + progressDesc + '\'' +
                ", progressUpdatedAt='" + progressUpdatedAt + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountBank='" + accountBank + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountRemark='" + accountRemark + '\'' +
                ", fundType='" + fundType + '\'' +
                ", org=" + org +
                ", fundOrgName='" + fundOrgName + '\'' +
                ", openDate='" + openDate + '\'' +
                ", firstFound='" + firstFound + '\'' +
                ", forbiddenPeriod='" + forbiddenPeriod + '\'' +
                ", sureForbiddenPeriod='" + sureForbiddenPeriod + '\'' +
                ", totalNetWorth='" + totalNetWorth + '\'' +
                ", minStartMoney='" + minStartMoney + '\'' +
                ", securityBrokerage='" + securityBrokerage + '\'' +
                ", channel='" + channel + '\'' +
                ", redemptionFee='" + redemptionFee + '\'' +
                ", manageFeeDesc='" + manageFeeDesc + '\'' +
                ", subscriptionFee='" + subscriptionFee + '\'' +
                ", performancePay='" + performancePay + '\'' +
                ", accountTrusteeshipBank='" + accountTrusteeshipBank + '\'' +
                ", investConcept='" + investConcept + '\'' +
                ", isStruct=" + isStruct +
                ", managerTeam=" + managerTeam +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", suggested=" + suggested +
                ", webLink='" + webLink + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerPhone='" + managerPhone + '\'' +
                ", counter=" + counter +
                ", isFavourite=" + isFavourite +
                ", description='" + description + '\'' +
                ", cat='" + cat + '\'' +
                ", feeMode='" + feeMode + '\'' +
                ", innerStatus='" + innerStatus + '\'' +
                ", finProductOrgId='" + finProductOrgId + '\'' +
                ", serviceManagerId='" + serviceManagerId + '\'' +
                ", ageSprd='" + ageSprd + '\'' +
                ", insurSprd='" + insurSprd + '\'' +
                ", orgName='" + orgName + '\'' +
                ", memo='" + memo + '\'' +
                ", feePeriod=" + feePeriod +
                ", intro=" + intro +
                ", guarantee='" + guarantee + '\'' +
                ", policy=" + policy +
                ", note='" + note + '\'' +
                ", tags=" + tags +
                ", files=" + files +
                ", insurOrg=" + insurOrg +
                ", ratesDesc=" + ratesDesc +
                '}';
    }
}
