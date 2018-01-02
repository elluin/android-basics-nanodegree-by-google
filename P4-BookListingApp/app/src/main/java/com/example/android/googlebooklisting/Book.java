package com.example.android.googlebooklisting;
import java.util.List;

public class Book {
    private String mTitle;
    private List<String> mAuthors;
    private String mSubtitle;
    private String mCoverImagePath;

    public Book(String title, List<String> authors, String subtitle, String coverImagePath) {
        this.mTitle = title;
        this.mAuthors = authors;
        this.mSubtitle = subtitle;
        this.mCoverImagePath = coverImagePath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public List<String> getAuthors() {
        return mAuthors;
    }

    public String getCoverImagePath() {
        return mCoverImagePath;
    }
}
