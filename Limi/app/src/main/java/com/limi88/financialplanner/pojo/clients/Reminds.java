
package com.limi88.financialplanner.pojo.clients;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Reminds {

    @SerializedName("today")
    @Expose
    private List<Real> today = new ArrayList<Real>();
    @SerializedName("tomorrow")
    @Expose
    private List<Real> tomorrow = new ArrayList<Real>();
    @SerializedName("next_week")
    @Expose
    private List<Real> nextWeek = new ArrayList<Real>();

    /**
     * 
     * @return
     *     The today
     */
    public List<Real> getToday() {
        return today;
    }

    /**
     * 
     * @param today
     *     The today
     */
    public void setToday(List<Real> today) {
        this.today = today;
    }

    /**
     * 
     * @return
     *     The tomorrow
     */
    public List<Real> getTomorrow() {
        return tomorrow;
    }

    /**
     * 
     * @param tomorrow
     *     The tomorrow
     */
    public void setTomorrow(List<Real> tomorrow) {
        this.tomorrow = tomorrow;
    }

    /**
     * 
     * @return
     *     The nextWeek
     */
    public List<Real> getNextWeek() {
        return nextWeek;
    }

    /**
     * 
     * @param nextWeek
     *     The next_week
     */
    public void setNextWeek(List<Real> nextWeek) {
        this.nextWeek = nextWeek;
    }

}
