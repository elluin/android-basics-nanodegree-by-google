package com.example.android.tourguidetatahungary;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SightsAdapter extends ArrayAdapter<Sights> {

    private int mColorResourceId;


    public SightsAdapter(Context context, ArrayList<Sights> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final Sights currentSights = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentSights.getSightsName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView addressTextView = (TextView) listItemView.findViewById(R.id.address_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        addressTextView.setText(currentSights.getSightsAddress());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView openingHoursTextView = (TextView) listItemView.findViewById(R.id.opening_hours_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        openingHoursTextView.setText(currentSights.getSightsOpeningHours());


        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        if (currentSights.hasImage()) {
            // Set the ImageView to the image resource specified in the current Sights
            imageView.setImageResource(currentSights.getImageDrawableId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);


        // Return the whole list item layout
        // so that it can be shown in the ListView
        return listItemView;
    }

}
