package cs160.final_proj_drawer;

import android.content.Context;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.MySingleton;
import cs160.final_proj_drawer.logic.Stop;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


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
                R.id.nav_settings, R.id.nav_saved, R.id.nav_posted)
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
                                    String coverPhoto = (String) itin.get("coverPhoto");
                                    String location = (String) itin.get("location");
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
                                        String stopLocation = (String) stopJSON.get("location");
                                        String stopname = (String) stopJSON.get("name");
                                        Stop newstop = new Stop(new ArrayList<String>(), stopname, stopLocation, desc,index);
                                        stops.add(newstop);
                                    }
                                    JSONArray accessJSON = itin.getJSONArray("access");
                                    ArrayList<String> access = new ArrayList<>();
                                    for (int i = 0; i < accessJSON.length(); i++) {
                                        access.add((String)accessJSON.get(i));
                                    }
                                    ItineraryObject itinerary = new ItineraryObject(creator,itinName, numLikes,
                                            coverPhoto,location, numStops, stops, tags, access);
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
                                String coverPhoto = (String) itin.get("coverPhoto");
                                String location = (String) itin.get("location");
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
                                    String stopLocation = (String) stopJSON.get("location");
                                    String stopname = (String) stopJSON.get("name");
                                    Stop newstop = new Stop(new ArrayList<String>(), stopname, stopLocation, desc,index);
                                    stops.add(newstop);



                                }
                                JSONArray accessJSON = itin.getJSONArray("access");
                                ArrayList<String> access = new ArrayList<>();
                                for (int i = 0; i < accessJSON.length(); i++) {
                                    access.add((String)accessJSON.get(i));
                                }
                                ItineraryObject itinerary = new ItineraryObject(creator,itinName, numLikes,
                                        coverPhoto,location, numStops, stops, tags, access);
                                list.add(itinerary);

                            } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                            }
                        } else {}



//                        ItinAdapter itinAdapter = new ItinAdapter(context, list, navController);
//                        homeItins.setAdapter(itinAdapter);
                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton mySingleton = new MySingleton(context);
        mySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

}
