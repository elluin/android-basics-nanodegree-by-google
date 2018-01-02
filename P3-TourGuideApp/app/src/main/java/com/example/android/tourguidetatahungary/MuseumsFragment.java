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
public class MuseumsFragment extends Fragment {

    public MuseumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.the_list, container, false);

        // Create a list of museums
        final ArrayList<Sights> words = new ArrayList<Sights>();

        words.add(new Sights(getString(R.string.domonkos_kuny), getString(R.string.domonkos_kuny_location),getString(R.string.domonkos_kuny_open), R.drawable.castle2));
        words.add(new Sights(getString(R.string.ungarndeutsches), getString(R.string.ungarndeutsches_location),getString(R.string.ungarndeutsches_open), R.drawable.deutsh));
        words.add(new Sights(getString(R.string.sculpture), getString(R.string.sculpture_location),getString(R.string.sculpture_open), R.drawable.statue));
        words.add(new Sights(getString(R.string.geological_garden), getString(R.string.geological_garden_location),getString(R.string.geological_garden_open), R.drawable.geological));
        words.add(new Sights(getString(R.string.slaughter), getString(R.string.slaughter_location),getString(R.string.slaughter_open), R.drawable.slaughterhouse));
        words.add(new Sights(getString(R.string.little_castle), getString(R.string.little_castle_location),getString(R.string.little_castle_open), R.drawable.littlecastel));


        SightsAdapter adapter =
                new SightsAdapter(getActivity(), words, R.color.categories);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(adapter);

        return rootView;
    }

}
