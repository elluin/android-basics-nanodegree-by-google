package com.example.android.guardiannews;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String JSON_KEY_ARTICLES = "response";
    private static final String JSON_KEY_ARTICLE_RESULTS = "results";
    private static final String JSON_KEY_ARTICLE_TITLE = "webTitle";
    private static final String JSON_KEY_ARTICLE_SECTION_NAME = "sectionName";
    private static final String JSON_KEY_ARTICLE_WEBURL = "webUrl";
    private static final String JSON_KEY_ARTICLE_FIELDS = "fields";
    private static final String JSON_KEY_ARTICLE_THUMBNAIL_PATH = "thumbnail";

    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<News> newses = extractFeatureFromJson(jsonResponse);

        return newses;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        ArrayList<News> newses = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject jsonResponse = baseJsonResponse.getJSONObject(JSON_KEY_ARTICLES);
            JSONArray newsArray = jsonResponse.getJSONArray(JSON_KEY_ARTICLE_RESULTS);

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String title = currentNews.getString(JSON_KEY_ARTICLE_TITLE);
                String sectionName = currentNews.getString(JSON_KEY_ARTICLE_SECTION_NAME);
                String webPublicationDate = currentNews.getString("webPublicationDate");
                String webUrl = currentNews.getString(JSON_KEY_ARTICLE_WEBURL);

                String thumbnail = null;
                JSONObject newsImages = currentNews.optJSONObject(JSON_KEY_ARTICLE_FIELDS);
                if (null != newsImages) {
                    thumbnail = newsImages.optString("thumbnail");
                }

                News news = new News(title, sectionName, webPublicationDate, webUrl, thumbnail);
                newses.add(news);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }

        return newses;
    }
}
