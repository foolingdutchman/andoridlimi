
package com.limi88.financialplanner.pojo.home.servicetools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.limi88.financialplanner.pojo.home.CommonTool;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("plan")
    @Expose
    private List<CommonTool> plan = new ArrayList<CommonTool>();
    @SerializedName("calculator")
    @Expose
    private List<CommonTool> calculator = new ArrayList<CommonTool>();
    @SerializedName("test")
    @Expose
    private List<CommonTool> test = new ArrayList<CommonTool>();
    @SerializedName("keys")
    @Expose
    private List<String> keys = new ArrayList<String>();

    private Map<String,List<CommonTool>> mToolMap =new HashMap<>();

    /**
     * 
     * @return
     *     The plan
     */
    public List<CommonTool> getPlan() {
        return plan;
    }

    /**
     * 
     * @param plan
     *     The plan
     */
    public void setPlan(List<CommonTool> plan) {
        this.plan = plan;

    }

    /**
     * 
     * @return
     *     The calculator
     */
    public List<CommonTool> getCalculator() {
        return calculator;
    }

    /**
     * 
     * @param calculator
     *     The calculator
     */
    public void setCalculator(List<CommonTool> calculator) {
        this.calculator = calculator;

    }

    /**
     * 
     * @return
     *     The test
     */
    public List<CommonTool> getTest() {
        return test;
    }

    /**
     * 
     * @param test
     *     The test
     */
    public void setTest(List<CommonTool> test) {
        this.test = test;

    }

    /**
     * 
     * @return
     *     The keys
     */
    public List<String> getKeys() {
        return keys;
    }

    /**
     * 
     * @param keys
     *     The keys
     */
    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public Map<String, List<CommonTool>> getToolMap() {
        mToolMap.put("plan",plan);
        mToolMap.put("calculator",calculator);
        mToolMap.put("test",test);

        return mToolMap;
    }
}
