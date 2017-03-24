
package com.limi88.financialplanner.pojo.newyearopenning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("like_num")
    @Expose
    private int likeNum;
    @SerializedName("comment_num")
    @Expose
    private int commentNum;
    @SerializedName("times_ago")
    @Expose
    private String timesAgo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("is_top")
    @Expose
    private boolean isTop;
    @SerializedName("outer_link")
    @Expose
    private String outerLink;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("is_liked")
    @Expose
    private boolean isLiked;
    @SerializedName("is_favourited")
    @Expose
    private boolean isFavourited;

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
     *     The likeNum
     */
    public int getLikeNum() {
        return likeNum;
    }

    /**
     * 
     * @param likeNum
     *     The like_num
     */
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    /**
     * 
     * @return
     *     The commentNum
     */
    public int getCommentNum() {
        return commentNum;
    }

    /**
     * 
     * @param commentNum
     *     The comment_num
     */
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 
     * @return
     *     The timesAgo
     */
    public String getTimesAgo() {
        return timesAgo;
    }

    /**
     * 
     * @param timesAgo
     *     The times_ago
     */
    public void setTimesAgo(String timesAgo) {
        this.timesAgo = timesAgo;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * 
     * @param tag
     *     The tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 
     * @return
     *     The isTop
     */
    public boolean isIsTop() {
        return isTop;
    }

    /**
     * 
     * @param isTop
     *     The is_top
     */
    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }

    /**
     * 
     * @return
     *     The outerLink
     */
    public String getOuterLink() {
        return outerLink;
    }

    /**
     * 
     * @param outerLink
     *     The outer_link
     */
    public void setOuterLink(String outerLink) {
        this.outerLink = outerLink;
    }

    /**
     * 
     * @return
     *     The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 
     * @param avatarUrl
     *     The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 
     * @return
     *     The isLiked
     */
    public boolean isIsLiked() {
        return isLiked;
    }

    /**
     * 
     * @param isLiked
     *     The is_liked
     */
    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    /**
     * 
     * @return
     *     The isFavourited
     */
    public boolean isIsFavourited() {
        return isFavourited;
    }

    /**
     * 
     * @param isFavourited
     *     The is_favourited
     */
    public void setIsFavourited(boolean isFavourited) {
        this.isFavourited = isFavourited;
    }

}
