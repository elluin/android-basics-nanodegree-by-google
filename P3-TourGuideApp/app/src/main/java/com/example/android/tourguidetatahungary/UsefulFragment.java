package com.example.android.tourguidetatahungary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsefulFragment extends Fragment {

    public UsefulFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.the_list, container, false);

        // Create a list of usefuls
        final ArrayList<Sights> sights = new ArrayList<Sights>();

        sights.add(new Sights(getString(R.string.post_office), getString(R.string.post_office_location),getString(R.string.post_office_open), Sights.NO_IMAGE_PROVIDED));
        sights.add(new Sights(getString(R.string.hospital), getString(R.string.hospital_location),getString(R.string.hospital_open), Sights.NO_IMAGE_PROVIDED));
        sights.add(new Sights(getString(R.string.police), getString(R.string.police_location),getString(R.string.police_open), Sights.NO_IMAGE_PROVIDED));
        sights.add(new Sights(getString(R.string.angyal_pharmacy), getString(R.string.angyal_pharmacy_location),getString(R.string.angyal_pharmacy_open), Sights.NO_IMAGE_PROVIDED));
        sights.add(new Sights(getString(R.string.nonstop_market), getString(R.string.nonstop_market_location),getString(R.string.nonstop_market_open), Sights.NO_IMAGE_PROVIDED));
        sights.add(new Sights(getString(R.string.bus_terminal), getString(R.string.bus_terminal_location),getString(R.string.bus_terminal_open), Sights.NO_IMAGE_PROVIDED));


        SightsAdapter adapter =
                new SightsAdapter(getActivity(), sights, R.color.categories);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(adapter);

        return rootView;
    }

}
