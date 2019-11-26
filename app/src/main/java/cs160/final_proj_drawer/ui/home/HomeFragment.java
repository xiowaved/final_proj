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
import cs160.final_proj_drawer.ItineraryObject;
import cs160.final_proj_drawer.MySingleton;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.Stop;

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

//    private Button searchButton;
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
//                ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName", 0,
//                        "coverPhoto", 1, null, new ArrayList<String>(), new ArrayList<String>());
//
//                itineraries.add(itinerary);
                int drawableID = categoryPics.getResourceId(i,0);
                cats.add("cat "+drawableID);
//
            }
        makeItineraries(itineraries,url);



            catAdapter = new CatAdapter(getContext(), cats);
            catItins.setAdapter(catAdapter);

//        tagSearchBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Log.i("user searched", "TESTING BUTTON");
//                navController.navigate(R.id.action_nav_home_to_search);
//
//
//                //the line under this kills the app
//                //queryCallback(locationSearchBar.getText().toString() + " " + tagSearchBar.getText().toString());
//            }
//        });
        tagSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ///Log.i("user clicked", "TESTING CLICK");

                //why does clicking into this make everything else invisible?
                if (hasFocus) {
                    navController.navigate(R.id.action_nav_home_to_search);
                    Toast.makeText(root.getContext(),"in the search",Toast.LENGTH_SHORT).show();
                }


                //locationSearchBar = root.findViewById(R.id.locationSearchBar);
                //locationSearchBar.setVisibility(View.VISIBLE);
            }
        });

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Log.i("user searched", "TESTING BUTTON");
//                navController.navigate(R.id.action_nav_home_to_search);
//
//
//                //the line under this kills the app
//                //queryCallback(locationSearchBar.getText().toString() + " " + tagSearchBar.getText().toString());
//            }
//        });

        return root;
    }
        public void makeItineraries(final ArrayList<ItineraryObject> list, String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        JSONObject info = response;
                        Iterator<String> keys = info.keys();
                        String name = "";
                        while (keys.hasNext()) {
                            name = keys.next();
                            if (name != "Tags") {
                                try {
                                    JSONObject itin = (JSONObject) info.get(name);
                                    String creator = (String) itin.get("creatorName");
                                    String itinName = (String) itin.get("itineraryName");
//                                    String coverPhoto = (String) itin.get("coverPhoto");
                                    int numLikes = (int) itin.get("numLikes");
                                    int numStops = (int) itin.get("numStops");
                                    JSONArray tagsJSON = itin.getJSONArray("tags");
                                    ArrayList<String> tags = new ArrayList<>();
                                    for (int i = 0; i < tagsJSON.length(); i++) {
                                        tags.add((String)tagsJSON.get(i));
                                    }
                                    JSONArray stopsJSON = itin.getJSONArray("stops");
                                    ArrayList<Stop> stops = new ArrayList<>();
                                    for (int i = 0; i < stopsJSON.length(); i++) {
                                        JSONObject stopJSON = stopsJSON.getJSONObject(i);
                                        String desc = (String) stopJSON.get("description");
                                        int index = (int) stopJSON.get("index");
                                        String location = (String) stopJSON.get("location");
                                        String stopname = (String) stopJSON.get("name");
                                        Stop newstop = new Stop(new ArrayList<String>(), stopname, location, desc,index);
                                        stops.add(newstop);



                                    }
                                    JSONArray accessJSON = itin.getJSONArray("access");
                                    ArrayList<String> access = new ArrayList<>();
                                    for (int i = 0; i < accessJSON.length(); i++) {
                                        access.add((String)accessJSON.get(i));
                                    }
                                    ItineraryObject itinerary = new ItineraryObject(creator,itinName, numLikes,
                                    "nothing", numStops, stops, tags, access);
                                    list.add(itinerary);

                                } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                                }
                            } else {}
                        }
                        if (name != "Tags") {
                            try {
                                JSONObject itin = (JSONObject) info.get(name);
                                String creator = (String) itin.get("creatorName");
                                String itinName = (String) itin.get("itineraryName");
//                                String coverPhoto = (String) itin.get("coverPhoto");
                                int numLikes = (int) itin.get("numLikes");
                                int numStops = (int) itin.get("numStops");
                                JSONArray tagsJSON = itin.getJSONArray("tags");
                                ArrayList<String> tags = new ArrayList<>();
                                for (int i = 0; i < tagsJSON.length(); i++) {
                                    tags.add((String)tagsJSON.get(i));
                                }
                                JSONArray stopsJSON = itin.getJSONArray("stops");
                                ArrayList<Stop> stops = new ArrayList<>();
                                for (int i = 0; i < stopsJSON.length(); i++) {
                                    JSONObject stopJSON = stopsJSON.getJSONObject(i);
                                    String desc = (String) stopJSON.get("description");
                                    int index = (int) stopJSON.get("index");
                                    String location = (String) stopJSON.get("location");
                                    String stopname = (String) stopJSON.get("name");
                                    Stop newstop = new Stop(new ArrayList<String>(), stopname, location, desc,index);
                                    stops.add(newstop);



                                }
                                JSONArray accessJSON = itin.getJSONArray("access");
                                ArrayList<String> access = new ArrayList<>();
                                for (int i = 0; i < accessJSON.length(); i++) {
                                    access.add((String)accessJSON.get(i));
                                }
                                ItineraryObject itinerary = new ItineraryObject(creator,itinName, numLikes,
                                        "nothing", numStops, stops, tags, access);
                                list.add(itinerary);

                            } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                            }
                        } else {}


                        try { Tags = (JSONObject) info.get("Tags"); }
                        catch (JSONException e) { }
                        itinAdapter = new ItinAdapter(getContext(), list);
                        homeItins.setAdapter(itinAdapter);
                    }
                    },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Context context = getContext();
        MySingleton mySingleton = new MySingleton(context);
        mySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

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