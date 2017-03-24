
package com.limi88.financialplanner.pojo.clients;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("reals")
    @Expose
    private List<Real> reals = new ArrayList<Real>();
    @SerializedName("tmps")
    @Expose
    private List<Real> tmps = new ArrayList<Real>();
    @SerializedName("reminds")
    @Expose
    private Reminds reminds;
    @SerializedName("search_conditions")
    @Expose
    private SearchConditions searchConditions;

    /**
     * 
     * @return
     *     The reals
     */
    public List<Real> getReals() {
        return reals;
    }

    /**
     * 
     * @param reals
     *     The reals
     */
    public void setReals(List<Real> reals) {
        this.reals = reals;
    }

    /**
     * 
     * @return
     *     The tmps
     */
    public List<Real> getTmps() {
        return tmps;
    }

    /**
     * 
     * @param tmps
     *     The tmps
     */
    public void setTmps(List<Real> tmps) {
        this.tmps = tmps;
    }

    /**
     * 
     * @return
     *     The reminds
     */
    public Reminds getReminds() {
        return reminds;
    }

    /**
     * 
     * @param reminds
     *     The reminds
     */
    public void setReminds(Reminds reminds) {
        this.reminds = reminds;
    }

    /**
     * 
     * @return
     *     The searchConditions
     */
    public SearchConditions getSearchConditions() {
        return searchConditions;
    }

    /**
     * 
     * @param searchConditions
     *     The search_conditions
     */
    public void setSearchConditions(SearchConditions searchConditions) {
        this.searchConditions = searchConditions;
    }

}
