package com.example.android.googlebooklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {


    private String mSearchTerm;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context    of the activity
     * @param searchTerm
     */
    public BookLoader(Context context, String searchTerm) {
        super(context);
        mSearchTerm = searchTerm;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        List<Book> books = null;
        if (null != mSearchTerm && !mSearchTerm.isEmpty()) {
            books = QueryUtils.fetchBookData(getContext(), mSearchTerm);
        }
        return books;
    }
}
