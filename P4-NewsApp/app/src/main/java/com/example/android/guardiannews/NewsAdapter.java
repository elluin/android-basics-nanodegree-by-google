package com.example.android.guardiannews;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * An {@link NewsAdapter} knows how to create a list item layout for each news
 * in the data source (a list of {@link News} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param newss is the list of news, which is the data source of the adapter
     */

    public NewsAdapter(Context context, ArrayList<News> newss) {
        super(context, 0, newss);
    }

    /**
     * Returns a list item view that displays information about the news at the given position
     * in the list of news.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Find the news at the given position in the list of news
        News currentNews = getItem(position);

        TextView newsTitleTextView = (TextView) listItemView.findViewById(R.id.textview_title);
        newsTitleTextView.setText(currentNews.getTitle());

        TextView newsSectionNameTextView = (TextView) listItemView.findViewById(R.id.textview_section_name);
        newsSectionNameTextView.setText(currentNews.getSectionName());

        TextView newsPublicationDateTextView = (TextView) listItemView.findViewById(R.id.textview_publication_date);
        newsPublicationDateTextView.setText(currentNews.getPublicationDate());



        ImageView thumbnailImageView = (ImageView) listItemView.findViewById(R.id.imageview_news_thumbnail);
        Picasso.with(getContext()).load(currentNews.getUrlThumbnail()).placeholder(R.drawable.guardian_logo).into(thumbnailImageView);

        return listItemView;
    }
}
