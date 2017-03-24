
package com.limi88.financialplanner.pojo.home;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Data {

    @SerializedName("banners")
    @Expose
    private List<Banner> banners = new ArrayList<Banner>();
    @SerializedName("limi_headlines")
    @Expose
    private List<LimiHeadline> limiHeadlines = new ArrayList<LimiHeadline>();
    @SerializedName("common_tools")
    @Expose
    private List<CommonTool> commonTools = new ArrayList<CommonTool>();

    /**
     * 
     * @return
     *     The banners
     */
    public List<Banner> getBanners() {
        return banners;
    }

    /**
     * 
     * @param banners
     *     The banners
     */
    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    /**
     * 
     * @return
     *     The limiHeadlines
     */
    public List<LimiHeadline> getLimiHeadlines() {
        return limiHeadlines;
    }

    /**
     * 
     * @param limiHeadlines
     *     The limi_headlines
     */
    public void setLimiHeadlines(List<LimiHeadline> limiHeadlines) {
        this.limiHeadlines = limiHeadlines;
    }

    /**
     * 
     * @return
     *     The commonTools
     */
    public List<CommonTool> getCommonTools() {
        return commonTools;
    }

    /**
     * 
     * @param commonTools
     *     The common_tools
     */
    public void setCommonTools(List<CommonTool> commonTools) {
        this.commonTools = commonTools;
    }

}
