package cs160.final_proj_drawer.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cs160.final_proj_drawer.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button searchButton;

    // top search bar -- query itinerary tags
    private EditText tagSearchBar;
    // lower search bar, appears after clicking inside top one (see Yelp as example); enter location
    private EditText locationSearchBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        tagSearchBar = root.findViewById(R.id.tagSearchBar);
        searchButton = root.findViewById(R.id.searchButton);


        tagSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                Log.i("user clicked", "TESTING CLICK");
                locationSearchBar = root.findViewById(R.id.locationSearchBar);
                locationSearchBar.setVisibility(View.VISIBLE);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("user searched", "TESTING BUTTON");
                queryCallback(locationSearchBar.getText().toString() + " " + tagSearchBar.getText().toString());
            }
        });
//        tagSearchBar.setOnEd(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                queryCallback(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        // THIS OVERRIDEN FUNCTION IS NOT BEING CALLED WHEN CLICK INSIDE SEARCHVIEW
//        tagSearchBar.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("user query", "TESTING");
//                locationSearchBar = root.findViewById(R.id.locationSearchBar);
//                locationSearchBar.setVisibility(View.VISIBLE);
//            }
//        });






        return root;
    }


    public void queryCallback(String query) {
        /**
         * CB function when user submits a query to search for an itinerary on the home page.
         * If there is no text in the query yet, display the default home page results for the
         * user's current location
         */
//        String currentQuery = tagSearchBar.getQuery().toString();
        if (query == null) {
            // search bar query is not filled in, which means user just opened the home page
            // display itineraries in current location
            // JSON request matching itineraries in current user location
        }
        else {
            // search bar has a query in it, search for itineraries matching this one
            // JSON request matching 1) location in query and 2) tags in search
            Log.i("user searched", query);

        }

    }
}