package com.example.android.guardiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int ARTICLES_LOADER_ID = 1;

    private TextView mEmptyStateTextView;
    private ListView mNewsListView;
    private SwipeRefreshLayout mSwipeContainer;
    private ProgressBar mProgressBar;


    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView = (TextView) findViewById(R.id.textview_message);

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout_news);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(ARTICLES_LOADER_ID, null, MainActivity.this);
                } else {
                    showMessage(getString(R.string.no_internet_connection));
                }
            }
        });

        mNewsListView = (ListView) findViewById(R.id.list);
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());
        mNewsListView.setAdapter(mNewsAdapter);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_loading_indicator);

        if (isNetworkAvailable()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ARTICLES_LOADER_ID, null, this);
        } else {
            showMessage(getString(R.string.no_internet_connection));
        }

        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = mNewsAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                if (null != websiteIntent.resolveActivity(getPackageManager())) {
                    startActivity(websiteIntent);
                } else {
                    Log.w(LOG_TAG, "Install a browser to read the article.");
                    Toast.makeText(MainActivity.this, getString(R.string.no_browser),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        boolean articlesAvailable = (null != data && !data.isEmpty());
        if (articlesAvailable) {
            showNewss(data);
        } else {
            showMessage(getString(R.string.no_news));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        showMessage(getString(R.string.no_news));
    }

    private void showMessage(String message) {
        mSwipeContainer.setRefreshing(false);
        mNewsListView.setVisibility(View.GONE);

        mEmptyStateTextView.setText(message);
        mEmptyStateTextView.setVisibility(View.VISIBLE);
    }


    private void showNewss(List<News> articles) {
        mSwipeContainer.setRefreshing(false);
        mEmptyStateTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mNewsAdapter.clear();
        mNewsAdapter.addAll(articles);
        mNewsListView.setVisibility(View.VISIBLE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}