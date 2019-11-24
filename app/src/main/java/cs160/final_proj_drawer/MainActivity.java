package cs160.final_proj_drawer;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public String urlRoot = "https://travelr-7feac.firebaseio.com/Locations";
    public String currentLocation = "Berkeley";
    public ArrayList<JSONObject> listOfItineraries = new ArrayList<>();
//    public JSONArray Tags;
    public JSONObject Tags;
    public JSONObject itineraries;


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
        getItineraries();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getItineraries() {
        String url = urlRoot + "/" + currentLocation + ".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        itineraries = response;
                        Iterator<String> keys = itineraries.keys();
                        while (keys.hasNext()) {
                            String name = keys.next();
                            if (name != "Tags") {
                                try {
                                    listOfItineraries.add((JSONObject) itineraries.get(name));
                                } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                                }
                            } else {
                                if (keys.hasNext()) {
                                    name = keys.next();
                                }
                            }
                        }
                        try {
                            Tags = (JSONObject) itineraries.get("Tags");
                        } catch (JSONException e) {

                        }
//                        This is the function that populates cards with itinerary info
                        cardConstructor();

                    }


                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton mySingleton = new MySingleton(this);
        mySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    public void cardConstructor() {
//        not sure how the cards showing the itineraries are made, but this method will get the appropriate information
        for (int i = 0; i < listOfItineraries.size();i ++) {
            try {
                String itineraryName = listOfItineraries.get(i).getString("itineraryName");
            } catch (JSONException e) {}
//            try {
//                There's not currently coverphotos in the Firebase, so this will return null. that's why its commented out
//                String photoname = listOfItineraries.get(i).getString("coverPhoto");

//            } catch (JSONException e) {}
            try {
                int numLikes = (int) listOfItineraries.get(i).get("numLikes");
            } catch (JSONException e) {

            }
//            Add the code to put into the cards here!!

            /***
             * Code to implement the itinerary views here.
             * use itineraryName, photoname, and numLikes
             */

        }
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
}
