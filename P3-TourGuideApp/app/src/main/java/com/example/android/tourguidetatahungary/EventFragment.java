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
public class EventFragment extends Fragment {

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.the_list, container, false);

        // Create a list of events
        final ArrayList<Sights> sights = new ArrayList<Sights>();

        sights.add(new Sights(getString(R.string.horse_show), getString(R.string.horse_show_location),getString(R.string.horse_show_open), R.drawable.horsefest));
        sights.add(new Sights(getString(R.string.tata_crowd), getString(R.string.tata_crowd_location),getString(R.string.tata_crowd_open), R.drawable.crowd));
        sights.add(new Sights(getString(R.string.patara), getString(R.string.patara_location),getString(R.string.patara_open), R.drawable.patara));
        sights.add(new Sights(getString(R.string.water_fest), getString(R.string.water_fest_location),getString(R.string.water_fest_open), R.drawable.watermusicflower));
        sights.add(new Sights(getString(R.string.grease_musical), getString(R.string.grease_musical_location),getString(R.string.grease_musical_open), R.drawable.openairstage));
        sights.add(new Sights(getString(R.string.big_fishing), getString(R.string.big_fishing_location),getString(R.string.big_fishing_open), R.drawable.fishermen));
        sights.add(new Sights(getString(R.string.goose_fest), getString(R.string.goose_fest_location),getString(R.string.goose_fest_open), R.drawable.goose));

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
