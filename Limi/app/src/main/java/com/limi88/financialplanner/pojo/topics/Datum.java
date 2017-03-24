
package com.limi88.financialplanner.pojo.topics;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("hide_name")
    @Expose
    private boolean hideName;
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
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("full_images")
    @Expose
    private List<String> fullImages = null;
    @SerializedName("medium_images")
    @Expose
    private List<String> mediumImages = null;
    @SerializedName("is_liked")
    @Expose
    private boolean isLiked;
    @SerializedName("is_favourited")
    @Expose
    private boolean isFavourited;

     @SerializedName("is_hot")
    @Expose
    private boolean isHot;


    @SerializedName("user")
    @Expose
    private User user;

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
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The hideName
     */
    public boolean isHideName() {
        return hideName;
    }

    /**
     * 
     * @param hideName
     *     The hide_name
     */
    public void setHideName(boolean hideName) {
        this.hideName = hideName;
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
     *     The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * 
     * @return
     *     The fullImages
     */
    public List<String> getFullImages() {
        return fullImages;
    }

    /**
     * 
     * @param fullImages
     *     The full_images
     */
    public void setFullImages(List<String> fullImages) {
        this.fullImages = fullImages;
    }

    /**
     * 
     * @return
     *     The mediumImages
     */
    public List<String> getMediumImages() {
        return mediumImages;
    }

    /**
     * 
     * @param mediumImages
     *     The medium_images
     */
    public void setMediumImages(List<String> mediumImages) {
        this.mediumImages = mediumImages;
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

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public boolean isFavourited() {
        return isFavourited;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }
}
