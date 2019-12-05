package cs160.final_proj_drawer.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.SearchQueryObject;

/* this fragment holds the home page and the search function.
*  it's the first page displayed to the user.
*  top is search bar.
*  bottom is home_child_nav_host_fragment.
*  it will navigate between
*  - splash page
*  - display itins
*  - filters
*  based on different actions taken involving search bar above.
*  */
public class HomeFragment extends Fragment {

    private ConstraintLayout searchLayout;
    private ImageButton searchButton;
    private Button filterButton;
    // top search bar -- query itinerary tags
    private SearchView tagSearchBar;
    // lower search bar, appears after clicking inside top one (see Yelp as example);
    private SearchView locationSearchBar;
    private TextView onSearchLocation;
    private NavController childNavController;
    private SearchQueryObject searchQueryObject;

    private int searchHeightSquish = 200;
    private int searchHeightExpand = 350;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        childNavController = Navigation.findNavController(root.findViewById(R.id.home_child_nav_host_fragment));
/*
        LinearLayout layout = root.findViewById(R.id.numberPadLayout);
// Gets the layout params that will allow you to resize the layout
        LayoutParams params = layout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 100;*/

        //find stuff
        searchLayout = root.findViewById(R.id.searchLayout);
        tagSearchBar = root.findViewById(R.id.tagSearchBar);
        locationSearchBar = root.findViewById(R.id.locationSearchBar);
        searchButton = root.findViewById(R.id.searchButton);
        filterButton = root.findViewById(R.id.filterButton);
        onSearchLocation = root.findViewById(R.id.on_search_location);
//
        //navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        locationSearchBar.setVisibility(View.INVISIBLE);
        final LayoutParams searchLayoutParams = searchLayout.getLayoutParams();
        searchLayoutParams.height = searchHeightSquish;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Log.i("NOTE", "clicked search");
                if (tagSearchBar.getQuery().toString().isEmpty()) {
                    return;
                }

                // pass a search query object with the query tags and location to the fragment
                String tagsQuery = tagSearchBar.getQuery().toString();
                childNavController.navigate(R.id.fragment_display_itins);

                locationSearchBar.setVisibility(View.INVISIBLE);
                onSearchLocation.setVisibility(View.VISIBLE);
                onSearchLocation.setText("Exploring " + locationSearchBar.getQuery().toString());
                filterButton.setVisibility(View.VISIBLE);
            }
        });

        //if the user clicks the location, they can change it again
        onSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Log.i("NOTE", "clicked search");
                onSearchLocation.setVisibility(View.INVISIBLE);
                locationSearchBar.setVisibility(View.VISIBLE);
            }
        });


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Log.i("NOTE", "clicked filter");
                Bundle bundle = new Bundle();
                bundle.putString("initialTagQuery", tagSearchBar.getQuery().toString());
                bundle.putString("locationQuery", locationSearchBar.getQuery().toString());
                //delete later
                //this is here for testing
                childNavController.navigate(R.id.fragment_filter,bundle);
            }


        });

        tagSearchBar.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View root, boolean hasFocus) {
                if (hasFocus) {
                    searchLayoutParams.height = searchHeightExpand;
                    locationSearchBar.setVisibility(View.VISIBLE);
                    onSearchLocation.setVisibility(View.INVISIBLE);
                } //else {
                    //exiting search stuff
                    //childNavController.navigate(R.id.fragment_splash);
                    //locationSearchBar.setVisibility(View.INVISIBLE);
                    //filterButton.setVisibility(View.INVISIBLE);
                //}
            }
        });

        locationSearchBar.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View root, boolean hasFocus) {
                if (hasFocus) {
                    //shrug. this is when u tap into the location search bar
                }
            }
        });

        return root;
    }


}