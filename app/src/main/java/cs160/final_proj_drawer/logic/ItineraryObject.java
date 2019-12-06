package cs160.final_proj_drawer.logic;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ItineraryObject implements Serializable {
     private String creatorName;
     private String itineraryName;
     private int numLikes;
     private String coverPhoto;
     private String location;
     private int numStops;
     private ArrayList<Stop> stops;
     private ArrayList<String> tags;
     private ArrayList<String> access;
    //static FirebaseDatabase database = FirebaseDatabase.getInstance();
    //static DatabaseReference myRef = database.getReference("Locations");

     public ItineraryObject() {}

    public ItineraryObject(String creatorName, String itineraryName, int numLikes,
                                String coverPhoto, String location, int numStops, ArrayList<Stop> stops, ArrayList<String> tags,
                                ArrayList<String>access) {
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

    // this creates an itineraryObject from a JSON Object
    // we get itins this way from Firebase.
    public ItineraryObject(JSONObject itin) {
        Log.i("ItinJsonConst", "inside");
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
        }catch (JSONException e){
            creator = "Anonymous";
        }
        try{
            itinName = (String) itin.get("itineraryName");
        } catch(JSONException e) {
            itinName = "Untitled";
        }
        try{
            coverPhoto = (String) itin.get("coverPhoto");
        } catch(JSONException e) {
            coverPhoto = null;
        }
        try{
            location = (String) itin.get("location");
        } catch(JSONException e) {
            location = "Unknown Location";
        }
        try{
            numLikes = (int) itin.get("numLikes");
        } catch(JSONException e) {
            numLikes = 0;
        }
        try{
            numStops = (int) itin.get("numStops");
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
        } catch(JSONException e) {
            numStops = 0;
            stops = null;
        }
        try{
            JSONArray tagsJSON = itin.getJSONArray("tags");

            for (int i = 0; i < tagsJSON.length(); i++) {
                tags.add((String)tagsJSON.get(i));
            }

        } catch(JSONException e) {
            tags = null;
        }
        try{
            JSONArray accessJSON = itin.getJSONArray("access");

            for (int i = 0; i < accessJSON.length(); i++) {
                access.add((String)accessJSON.get(i));
            }
        } catch(JSONException e) {
            access = null;
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

    public void setItineraryName(String name) {
        this.itineraryName = name;
    }

    public boolean getBookmarked() {
        return this.tags.contains("bookmarked");
    }
    public String getCoverPhoto() {
        return this.coverPhoto;
    }

    public int getNumLikes() {
        return this.numLikes;
    }

    public int getNumStops() {
        return this.numStops;
    }

    public ArrayList<Stop> getStops() {
        return this.stops;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public ArrayList<String> getAccess() {
        return this.access;
    }

    public String getLocation() {return this.location;}

    public void setItineraryLocation(String location) {
        this.location = location;
    }

    public void addTags(String tag){this.tags.add(tag);}

    public void setBookmarked(boolean isBookmarked) {
         if (isBookmarked){
             this.tags.add("bookmarked");
             //myRef.child(this.location).child(this.itineraryName).setValue(this);
             //            Below this fixes the random new fields that it added
             //myRef.child(this.location).child(this.itineraryName).child("liked").removeValue();
             //myRef.child(this.location).child(this.itineraryName).child("bookmarked").removeValue();


         } else {
             this.tags.remove("bookmarked");
             //myRef.child(this.location).child(this.itineraryName).setValue(this);
             //            Below this fixes the random new fields that it added
             //myRef.child(this.location).child(this.itineraryName).child("liked").removeValue();
             //myRef.child(this.location).child(this.itineraryName).child("bookmarked").removeValue();

         }
    }

    public void clickBookmark() {
        setBookmarked(!getBookmarked());
    }

    public void addStop(Stop s) {
        this.stops.add(s);
    }
    public void removeStop(int position) {
        this.stops.remove(position);
    }
    public void replaceStop(int position, Stop s) {
        this.stops.set(position, s);
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    public void setNumStops(int num) {
        this.numStops = num;
    }

    public void clickLiked() { setLiked(!getLiked());}
    public void setLiked(boolean isLiked) {
        if (isLiked){
            this.tags.add("liked");
            this.numLikes +=1;
            //myRef.child(this.location).child(this.itineraryName).setValue(this);
//            Below this fixes the random new fields that it added
            //myRef.child(this.location).child(this.itineraryName).child("liked").removeValue();
            //myRef.child(this.location).child(this.itineraryName).child("bookmarked").removeValue();
        } else {
            this.tags.remove("liked");
            this.numLikes -=1;
            //myRef.child(this.location).child(this.itineraryName).setValue(this);
            //            Below this fixes the random new fields that it added
            //myRef.child(this.location).child(this.itineraryName).child("liked").removeValue();
            //myRef.child(this.location).child(this.itineraryName).child("bookmarked").removeValue();
        }
    }
    public boolean getLiked(){
        return this.tags.contains("liked");
    }
}


