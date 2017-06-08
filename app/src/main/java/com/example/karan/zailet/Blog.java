package com.example.karan.zailet;

/**
 * Created by Karan on 08-06-2017.
 */

public class Blog {

    private String mTitle;
    private String mTime;
    private String mDescription;
    private String mThumbnail;
    private String mAuthorName;
    private String mDP;

    public Blog(String postTitle, String description, String thumbnail)
    {
        mTitle = postTitle;
        mDescription = description;
        mThumbnail = thumbnail;
    }

    public  String getTitle() {
        return mTitle;
    }
    public  String  getTime() {
        return mTime;
    }
    public  String getDescription() {
        return mDescription;
    }
    public String getThumbnail() { return mThumbnail; }
    public String getAuthorName() { return mAuthorName; }
    public String getDP() { return mDP; }
}
