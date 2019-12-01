package cs160.final_proj_drawer.logic;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.logic.MySingleton;

public class FirebaseFuncs {

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


                    return list;

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
