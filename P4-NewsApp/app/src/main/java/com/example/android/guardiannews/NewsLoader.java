package com.example.android.guardiannews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private static final String QUERY_URL =
            "http://content.guardianapis.com/search?q=debates&show-fields=thumbnail&api-key=test";


    public NewsLoader(Context context) { super(context); }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> newses = QueryUtils.fetchNewsData(QUERY_URL);
        return newses;
    }
}