
package com.limi88.financialplanner.pojo.home;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Counter {

    @SerializedName("mail_count")
    @Expose
    private int mailCount;
    @SerializedName("download_count")
    @Expose
    private int downloadCount;
    @SerializedName("browse_count")
    @Expose
    private int browseCount;
    @SerializedName("appointment_count")
    @Expose
    private int appointmentCount;

    /**
     * 
     * @return
     *     The mailCount
     */
    public int getMailCount() {
        return mailCount;
    }

    /**
     * 
     * @param mailCount
     *     The mail_count
     */
    public void setMailCount(int mailCount) {
        this.mailCount = mailCount;
    }

    /**
     * 
     * @return
     *     The downloadCount
     */
    public int getDownloadCount() {
        return downloadCount;
    }

    /**
     * 
     * @param downloadCount
     *     The download_count
     */
    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    /**
     * 
     * @return
     *     The browseCount
     */
    public int getBrowseCount() {
        return browseCount;
    }

    /**
     * 
     * @param browseCount
     *     The browse_count
     */
    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    /**
     * 
     * @return
     *     The appointmentCount
     */
    public int getAppointmentCount() {
        return appointmentCount;
    }

    /**
     * 
     * @param appointmentCount
     *     The appointment_count
     */
    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "mailCount=" + mailCount +
                ", downloadCount=" + downloadCount +
                ", browseCount=" + browseCount +
                ", appointmentCount=" + appointmentCount +
                '}';
    }
}
