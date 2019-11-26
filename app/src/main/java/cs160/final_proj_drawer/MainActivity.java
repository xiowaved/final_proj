package cs160.final_proj_drawer;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cs160.final_proj_drawer.adapters.ItinAdapter;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public String urlRoot = "https://travelr-7feac.firebaseio.com/Locations";
    public String currentLocation = "Berkeley";
    public ArrayList<JSONObject> listOfItineraries = new ArrayList<>();
//    public JSONArray Tags;
    public static JSONObject Tags;
    public JSONObject itineraries;
    public String searchedTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_create,
                R.id.nav_tools, R.id.nav_saved, R.id.nav_posted)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setSearchedTag (View view) {
        EditText searchbar = view.findViewById(R.id.tagSearchBar);
        searchedTag = searchbar.getText().toString();
    }


    public void ClickItinerary (View view){
//        ItineraryCard = findViewById(R.id.ItineraryCard);
//        String itinname = ItineraryCard.getText()
//        JSONObject itinerary = itineraries.get(itinname);
        /***
         * I'm not sure if we are using intents to start the view activity fragment
         *
         * but here is how you would get all the information you need.
         */
//        String creatorName = itinerary.getString("creatorName")
        /**
         * Again, because I am not sure how viewItinerary works
         * I have to do some more code to get all the stops, but it should be ok
         */
//        JSONArray stops = itinerary.get("stops");
    }

    public static void makeItineraries(final ArrayList<ItineraryObject> list, String url, final Context context, final RecyclerView homeItins, final NavController navController){
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
                        ItinAdapter itinAdapter = new ItinAdapter(context, list, navController);
                        homeItins.setAdapter(itinAdapter);
                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton mySingleton = new MySingleton(context);
        mySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }
    public static void makeItinerariesArray(final ArrayList<ItineraryObject> list, String url, final Context context, final RecyclerView homeItins, final NavController navController){


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        ArrayList<ItineraryObject> list = new ArrayList<>();
                        JSONArray info = response;


                        for (int index = 0; index < response.length(); index++) {
                            JSONObject itin = null;
                            try {
                                itin = response.getJSONObject(index);
                            } catch (JSONException e) {}

                            try {
                                String creator = (String) itin.get("creatorName");
                                String itinName = (String) itin.get("itineraryName");
//                                    String coverPhoto = (String) itin.get("coverPhoto");
                                int numLikes = (int) itin.get("numLikes");
                                int numStops = (int) itin.get("numStops");
                                JSONArray tagsJSON = itin.getJSONArray("tags");
                                ArrayList<String> tags = new ArrayList<>();
                                for (int index2 = 0; index2 < tagsJSON.length(); index2++) {
                                    tags.add((String)tagsJSON.get(index2));
                                }
                                JSONArray stopsJSON = itin.getJSONArray("stops");
                                ArrayList<Stop> stops = new ArrayList<>();
                                for (int index3 = 0; index3 < stopsJSON.length(); index3++) {
                                    JSONObject stopJSON = stopsJSON.getJSONObject(index3);
                                    String desc = (String) stopJSON.get("description");
                                    int stopIndex = (int) stopJSON.get("index");
                                    String location = (String) stopJSON.get("location");
                                    String stopname = (String) stopJSON.get("name");
                                    Stop newstop = new Stop(new ArrayList<String>(), stopname, location, desc,stopIndex);
                                    stops.add(newstop);



                                }
                                JSONArray accessJSON = itin.getJSONArray("access");
                                ArrayList<String> access = new ArrayList<>();
                                for (int index4 = 0; index4 < accessJSON.length(); index4++) {
                                    access.add((String)accessJSON.get(index4));
                                }
                                ItineraryObject itinerary = new ItineraryObject(creator,itinName, numLikes,
                                        "nothing", numStops, stops, tags, access);
                                list.add(itinerary);

                            } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                            }
                        }


                        ItinAdapter itinAdapter = new ItinAdapter(context, list, navController);
                        homeItins.setAdapter(itinAdapter);
                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton mySingleton = new MySingleton(context);
        mySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }


}
