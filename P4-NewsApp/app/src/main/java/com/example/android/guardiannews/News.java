package com.example.android.guardiannews;

public class News {
    private String mTitle;
    private String mSectionName;
    private String mPublicationDate;
    private String mUrl;
    private String mUrlThumbnail;

    public News(String title, String sectionName, String publicationDate, String url, String urlThumbnail) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mPublicationDate = publicationDate;
        this.mUrl = url;
        this.mUrlThumbnail = urlThumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlThumbnail() {
        return mUrlThumbnail;
    }
}
