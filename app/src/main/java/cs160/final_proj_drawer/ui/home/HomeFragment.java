package cs160.final_proj_drawer.ui.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs160.final_proj_drawer.adapters.CatAdapter;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.MySingleton;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

/* this fragment holds the home page and the search function.
*  it's the first page displayed to the user
*  top is search bar, bottom is splash page.
*
*   FOR FINAL PROJ: make a navhostfragment in the
*   bottom of this page. it will navigate between
*   - splash page
*   - display itins
*   - filters
*  */
public class HomeFragment extends Fragment {

    private ImageButton searchButton;
    private NavController navController;
    public String urlRoot = "https://travelr-7feac.firebaseio.com/Locations";
    public String currentLocation = "Berkeley";
    public JSONObject Tags;


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

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
            String url = urlRoot + "/" + currentLocation + ".json";

        for (int i = 0; i < 4; i++)
            {
                ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName", 0,
                        "coverPhoto", 1, null, new ArrayList<String>(), new ArrayList<String>());

                itineraries.add(itinerary);
                int drawableID = categoryPics.getResourceId(i,0);
                cats.add("cat "+drawableID);
//
            }
        //makeItineraries(itineraries,url);



            catAdapter = new CatAdapter(getContext(), cats);
            catItins.setAdapter(catAdapter);

        tagSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(root.getContext(),"in the search",Toast.LENGTH_SHORT).show();
                }

                //locationSearchBar = root.findViewById(R.id.locationSearchBar);
                //locationSearchBar.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }


}