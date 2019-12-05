package cs160.final_proj_drawer.logic;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import cs160.final_proj_drawer.R;

/* This is all of our functions that interact with firebase.
   They are created here, as separate from UI, so we can
   Call them freely from any other part of our app
 */
public class FirebaseFuncs<Model> {


    /*  stuff to access our project's firebase database.
            if we change stuff on our firebase, we have to
            change these
         */

    // Initialize Firebase Auth
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("Locations");
    public static String url = "https://travelr-7feac.firebaseio.com/Locations/";
    public FirebaseFuncsCallback<ItineraryObject> firebaseCallback;
    private ValueEventListener listener;
    static StorageReference storage = FirebaseStorage.getInstance().getReference();

    public interface FirebaseFuncsCallback<ItineraryObject> {
        void onSuccess(ArrayList<ItineraryObject> result);

        void onError(Exception e);
    }

    //    when we get this working it won't need to take in Context anymore
    public static void putImage(Context context) {
// This is an example of using a drawable png
        int pictureID = R.drawable.bookmark;
        Uri filePath = getUriToDrawable(context, pictureID);
        /**/

        final StorageReference storageRef = storage.child("new folder/third_bookmark");
//
//
//        convert the image to a Bitmap (easier to put in)
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), pictureID);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        storageRef.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String URL = uri.toString();
                            Log.i("URL", URL);
                        }
                    });
                }
            }
        });

    }


    //    Code that was needed to get URI from drawable ID
    public static final Uri getUriToDrawable(Context context,
                                             int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }

    public void addListener(final FirebaseFuncsCallback<ItineraryObject> firebaseCallback) {
        this.firebaseCallback = firebaseCallback;
        this.listener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ItineraryObject> itins = new ArrayList<>();
                DataSnapshot item = dataSnapshot.child("Berkeley");

                HashMap hash = (HashMap) item.getValue();
                itins = handleHash(hash);

                firebaseCallback.onSuccess(itins);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseCallback.onError(databaseError.toException());
            }
        };
        myRef.addValueEventListener(listener);
//        listener = new BaseValueEventListener(mapper, firebaseCallback);
//        databaseReference.addValueEventListener(listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<ItineraryObject> handleHash(HashMap<String, HashMap> map) {
        Iterator<String> keys = map.keySet().iterator();
        ArrayList<ItineraryObject> returned = new ArrayList<>();
        String key = "";
        while (keys.hasNext()) {
            key = keys.next();
            if (!key.equals("Tags")) {

                HashMap<String, Object> results = map.get(key);
                boolean isBookmarked = (boolean) results.get("isBookmarked");
                String itineraryName = (String) results.get("itineraryName");
                String creatorName = (String) results.get("creatorName");
                int numStops = Math.toIntExact((Long) results.get("numStops"));
                String location = (String) results.get("location");
                ArrayList<Stop> badStops = (ArrayList<Stop>) results.get("stops");
                ArrayList<Stop> stops = fixStop(badStops);
                int numLikes = Math.toIntExact((Long) results.get("numLikes"));
                ArrayList<String> tags = (ArrayList<String>) results.get("tags");
//            ArrayList<String> access = (ArrayList<String>) results.get("access");
                String coverPhoto = (String) results.get("coverPhoto");
//                String coverPhoto = "none";
                ArrayList<String> access = new ArrayList<>();
                ItineraryObject itinerary = new ItineraryObject(creatorName, itineraryName, numLikes,
                        coverPhoto, location, numStops, stops, tags,
                        access, isBookmarked);
                returned.add(itinerary);
            } else {
            }
        }

        return returned;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Stop> fixStop(ArrayList badStopList) {
        ArrayList<Stop> returned = new ArrayList<>();
        for (int i = 0; i < badStopList.size(); i++) {
            HashMap map = (HashMap) badStopList.get(i);
            String name = (String) map.get("name");
            String description = (String) map.get("description");
            int index = Math.toIntExact((Long) map.get("index"));
            String location = (String) map.get("location");
            List<String> empty = new ArrayList<>();
            Stop newStop = new Stop(empty, name, location, description, index);
            returned.add(newStop);

        }
        return returned;
    }

    public void removeListener() {
        myRef.removeEventListener(listener);
    }

    /*
        this should write a single finalized itin to our database.
        use when the user submits thei created itin
     */
    public static void writeSingleItin(ItineraryObject itin) {
        // i lifted this code out of create Itin
        // i do not know how this works, isn't working here
        //todo make this work
        String location = itin.getLocation();
        myRef.child(location).child(itin.getItineraryName()).setValue(itin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        myRef.push();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                    }
                });

        List<String> tags = itin.getTags();
        for (int i = 0; i < tags.size(); i++) {
            myRef.child(location).child(tags.get(i)).child(itin.getItineraryName()).setValue(itin);
        }


    }

    //toDo make a function in itinObject that converts it to a json object, and vice versa

    // todo vaguely understand what is happening here
    // this needs application context, not activity context
    // (otherwise itll die every time activity is redrawn, like rotation)


//    getItineraries should only be used for the homefragment, or for when the location changes.
//    Any additional filtering needs to use getNestedItineraries
//    I think this will also need to be used for bookmarked itineraries, just given correct url




    //    getNestedItineraries should be our main itinerary getting thing, it can
//    take anywhere from 1 to a million tags and it returns itineraries that have all of the tags.
//
    public static void getNestedItineraries(final ArrayList<ItineraryObject> list, final Context context, SearchQueryObject params) {

        final String[] neededTags = params.getTags();
        String location = params.getLocation();
        String Url = url + location + "json";

//        Log.i("getItin", "called getItin");
        // I did it this way b/c there might be itineraries with names that come after tags.
//        that's why its this two-tier search.
//        First it goes through all the itineraries, ignoring the tags folder, and gets to the last one and adds it
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        JSONObject info = response;
                        Iterator<String> keys = info.keys();
                        String name = "";
                        
                        while (keys.hasNext()) {
                            name = keys.next();
                            if (!name.equals("Tags")) {
                                try {
                                    boolean hasAll = true;


                                    JSONObject itin = info.getJSONObject(name);
                                    Log.i("getItin", "before filling itin " + name);
                                    ItineraryObject itinerary = new ItineraryObject(itin);
                                    if (neededTags.length == 0) {
                                    } else {
                                        for (int i = 0; i < neededTags.length; i++) {
                                            ArrayList<String> tags = itinerary.getTags();
                                            if (tags.contains(neededTags[i])) {
                                            } else {
                                                hasAll = false;
                                            }
                                        }
                                    }

                                    if (hasAll == true) {
                                        list.add(itinerary);
                                    } else {
                                    }
                                    ;

//                                   HERE is where the itinerary is added once its fully been constructed
                                    //Log.i("getItin", "should have added (first if)");

                                    //Log.i("getItin", "should have added (first if)");

                                } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                                }
                            } else {
                            }
                        }
                        if (!name.equals("Tags")) {
                            try {

                                boolean hasAll = true;


                                JSONObject itin = info.getJSONObject(name);
                                Log.i("getItin", "before filling itin " + name);
                                ItineraryObject itinerary = new ItineraryObject(itin);

                                if (neededTags.length == 0) {
                                } else {
                                    for (int i = 0; i < neededTags.length; i++) {
                                        ArrayList<String> tags = itinerary.getTags();
                                        if (tags.contains(neededTags[i])) {
                                        } else {
                                            hasAll = false;
                                        }
                                    }
                                }

                                if (hasAll == true) {
                                    list.add(itinerary);
                                } else {
                                }
                                ;

                            } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                            }
                        } else {
                        }

//this is where it's done. we want a way for it to convey it is done. maybe loop / wait until this mutates a bool val?
//                    return list;

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        FirebaseSingleton firebaseSingleton = new FirebaseSingleton(context);
        firebaseSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


}