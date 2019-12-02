package cs160.final_proj_drawer.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cs160.final_proj_drawer.R;

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

    private ImageButton searchButton;
    private Button filterButton;
    // top search bar -- query itinerary tags
    private SearchView tagSearchBar;
    // lower search bar, appears after clicking inside top one (see Yelp as example);
    private SearchView locationSearchBar;
    private TextView onSearchLocation;
    private NavController childNavController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        childNavController = Navigation.findNavController(root.findViewById(R.id.home_child_nav_host_fragment));

        //find stuff
        tagSearchBar = root.findViewById(R.id.tagSearchBar);
        locationSearchBar = root.findViewById(R.id.locationSearchBar);
        searchButton = root.findViewById(R.id.searchButton);
        filterButton = root.findViewById(R.id.filterButton);
        onSearchLocation = root.findViewById(R.id.on_search_location);

        //navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Log.i("NOTE", "clicked search");
                childNavController.navigate(R.id.fragment_display_itins);

                locationSearchBar.setVisibility(View.INVISIBLE);
                onSearchLocation.setVisibility(View.VISIBLE);
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

                //delete later
                //this is here for testing
                childNavController.navigate(R.id.fragment_filter);
            }


        });

        tagSearchBar.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View root, boolean hasFocus) {
                if (hasFocus) {
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