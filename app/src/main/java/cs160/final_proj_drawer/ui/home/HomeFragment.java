package cs160.final_proj_drawer.ui.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.CatAdapter;
import cs160.final_proj_drawer.ItinAdapter;
import cs160.final_proj_drawer.ItineraryObject;
import cs160.final_proj_drawer.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
//    private Button searchButton;
    private ImageButton searchButton;
    private NavController navController;


    private RecyclerView homeItins;
    private RecyclerView catItins;
    private ItinAdapter itinAdapter;
    private CatAdapter catAdapter;
    private TypedArray categoryPics;

    // top search bar -- query itinerary tags
    private EditText tagSearchBar;
    // lower search bar, appears after clicking inside top one (see Yelp as example);
    // enter location
    private EditText locationSearchBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        //find stuff
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        tagSearchBar = root.findViewById(R.id.tagSearchBar);
        searchButton = root.findViewById(R.id.searchButton);


        //recycler views setup
        homeItins = (RecyclerView) root.findViewById(R.id.home_itins);
        final LinearLayoutManager itinLayoutManager = new LinearLayoutManager(getActivity());
        itinLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeItins.setLayoutManager(itinLayoutManager);

        catItins = (RecyclerView) root.findViewById(R.id.home_cats);
        final LinearLayoutManager catLayoutManager = new LinearLayoutManager(getActivity());
        catLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        catItins.setLayoutManager(catLayoutManager);

        // get info for category data
        categoryPics = getResources().obtainTypedArray(R.array.category_pics);

        //eventually wanna modularize this out of this func. just wanna call this
            ArrayList itineraries = new ArrayList<ItineraryObject>();
            ArrayList cats = new ArrayList<String>();
            //make json call, find the length and that is your for loop upper bound
            for (int i = 0; i < 4; i++)
            {
                ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName", 0,
                        "coverPhoto", 1, null, new ArrayList<String>(), new ArrayList<String>());

                itineraries.add(itinerary);
                int drawableID = categoryPics.getResourceId(i,0);
                cats.add("cat "+drawableID);

            }

            itinAdapter = new ItinAdapter(getContext(), itineraries);
            homeItins.setAdapter(itinAdapter);
            catAdapter = new CatAdapter(getContext(), cats);
            catItins.setAdapter(catAdapter);

        tagSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ///Log.i("user clicked", "TESTING CLICK");

                //why does clicking into this make everything else invisible?


                Toast.makeText(root.getContext(),"in the search",Toast.LENGTH_SHORT).show();

                //locationSearchBar = root.findViewById(R.id.locationSearchBar);
                //locationSearchBar.setVisibility(View.VISIBLE);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("user searched", "TESTING BUTTON");
                navController.navigate(R.id.action_nav_home_to_search);


                //the line under this kills the app
                //queryCallback(locationSearchBar.getText().toString() + " " + tagSearchBar.getText().toString());
            }
        });

        return root;
    }

    public void goToSearch() {
        navController.navigate(R.id.action_nav_home_to_search);

    }

    public void queryCallback(String query) {
        /**
         * CB function when user submits a query to search for an itinerary on the home page.
         * If there is no text in the query yet, display the default home page results for the
         * user's current location
         */
        // String currentQuery = tagSearchBar.getQuery().toString();
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