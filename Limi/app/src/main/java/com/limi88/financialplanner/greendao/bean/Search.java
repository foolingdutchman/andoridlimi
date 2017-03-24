package com.limi88.financialplanner.greendao.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SEARCH".
 */
public class Search {

    private Long id;
    private String searchTitle;

    public Search() {
    }

    public Search(Long id) {
        this.id = id;
    }

    public Search(Long id, String searchTitle) {
        this.id = id;
        this.searchTitle = searchTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

}