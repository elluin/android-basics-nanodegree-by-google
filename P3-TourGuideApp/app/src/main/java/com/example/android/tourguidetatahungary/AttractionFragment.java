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
public class AttractionFragment extends Fragment {

    public AttractionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.the_list, container, false);

        // Create a list of attractions
        final ArrayList<Sights> sights = new ArrayList<Sights>();

        sights.add(new Sights(getString(R.string.old_lake), getString(R.string.old_lake_location),getString(R.string.old_lake_open), R.drawable.oldlake));
        sights.add(new Sights(getString(R.string.cseke_lake), getString(R.string.cseke_lake_location),getString(R.string.cseke_lake_open), R.drawable.csekelake));
        sights.add(new Sights(getString(R.string.clocktower), getString(R.string.clocktower_location),getString(R.string.clocktower_open), R.drawable.clocktower));
        sights.add(new Sights(getString(R.string.ruins), getString(R.string.ruins_location),getString(R.string.ruins_open), R.drawable.ruin));
        sights.add(new Sights(getString(R.string.calvary), getString(R.string.calvary_location),getString(R.string.calvary_open), R.drawable.calvary));
        sights.add(new Sights(getString(R.string.derito_lake), getString(R.string.derito_lake_location),getString(R.string.derito_lake_open), R.drawable.deritolake));
        sights.add(new Sights(getString(R.string.fenyes_trail), getString(R.string.fenyes_trail_location),getString(R.string.fenyes_trail_open), R.drawable.trail));

        SightsAdapter adapter =
                new SightsAdapter(getActivity(), sights, R.color.categories);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of sights.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(adapter);

        return rootView;
    }

}