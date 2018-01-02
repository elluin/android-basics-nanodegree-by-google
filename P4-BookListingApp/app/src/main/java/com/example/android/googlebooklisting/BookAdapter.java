package com.example.android.googlebooklisting;

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
 * An {@link BookAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Book} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    private static final String AUTHOR_SEPARATOR = ", ";

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
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

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        ImageView coverImageView = (ImageView) listItemView.findViewById(R.id.imageview_book_thumbnail);
        Picasso.with(getContext()).load(currentBook.getCoverImagePath()).placeholder(R.drawable.placeholder_book).into(coverImageView);

        TextView bookTitleTextView = (TextView) listItemView.findViewById(R.id.textview_title);
        bookTitleTextView.setText(currentBook.getTitle());

        TextView bookAuthorsTextView = (TextView) listItemView.findViewById(R.id.textview_authors);
        bookAuthorsTextView.setText(TextUtils.join(AUTHOR_SEPARATOR, currentBook.getAuthors()));

        TextView bookSubtitleTextView = (TextView) listItemView.findViewById(R.id.textview_subtitle);
        bookSubtitleTextView.setText(currentBook.getSubtitle());


        return listItemView;
    }
}
