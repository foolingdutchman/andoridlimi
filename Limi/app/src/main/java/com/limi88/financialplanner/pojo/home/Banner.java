
package com.limi88.financialplanner.pojo.home;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Banner {

    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("target_link")
    @Expose
    private String targetLink;
    @SerializedName("sign")
    @Expose
    private boolean sign;

    /**
     * @return The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return The targetLink
     */
    public String getTargetLink() {
        return targetLink;
    }

    /**
     * @param targetLink The target_link
     */
    public void setTargetLink(String targetLink) {
        this.targetLink = targetLink;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }
}
