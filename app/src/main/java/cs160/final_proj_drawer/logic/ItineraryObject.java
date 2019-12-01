package cs160.final_proj_drawer.logic;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItineraryObject implements Serializable {
     private String creatorName;
     private String itineraryName;
     private int numLikes;
     private String coverPhoto;
     private String location;
     private int numStops;
     private List<Stop> stops;
     private List<String> tags;
     private List<String> access;


    public ItineraryObject(String creatorName, String itineraryName, int numLikes,
                                String coverPhoto, String location, int numStops, List<Stop> stops, List<String> tags,
                                List<String>access) {
        // Create an itinerary object
        this.creatorName = creatorName;
        this.itineraryName = itineraryName;
        this.numLikes = numLikes;
        this.coverPhoto = coverPhoto;
        this.location = location;
        this.numStops = numStops;
        this.stops = stops;
        this.tags = tags;
        this.access = access;
    }

    public ItineraryObject(JSONObject itin) {

        String creator = "";
        String itinName = "";
        String coverPhoto = "";
        String location = "";
        int numLikes = 0;
        int numStops = 0;
        ArrayList<Stop> stops = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> access = new ArrayList<>();
        try {
        creator = (String) itin.get("creatorName");
        itinName = (String) itin.get("itineraryName");
        coverPhoto = (String) itin.get("coverPhoto");
        location = (String) itin.get("location");
        numLikes = (int) itin.get("numLikes");
        numStops = (int) itin.get("numStops");
        JSONArray tagsJSON = itin.getJSONArray("tags");

        for (int i = 0; i < tagsJSON.length(); i++) {
            tags.add((String)tagsJSON.get(i));
        }
        JSONArray stopsJSON = itin.getJSONArray("stops");

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

        for (int i = 0; i < accessJSON.length(); i++) {
            access.add((String)accessJSON.get(i));
        }} catch (JSONException e) {
            e.printStackTrace();
        }
        this.creatorName = creator;
        this.location = location;
        this.access = access;
        this.coverPhoto = coverPhoto;
        this.itineraryName = itinName;
        this.numLikes = numLikes;
        this.numStops = numStops;
        this.tags = tags;
        this.stops = stops;

    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public String getItineraryName() {
        return this.itineraryName;
    }

    public int getNumLikes() {
        return this.numLikes;
    }

    public int getNumStops() {
        return this.numStops;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<String> getAccess() {
        return this.access;
    }

    public String getLocation() {return this.location;}

}


