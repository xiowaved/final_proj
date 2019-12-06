package cs160.final_proj_drawer.logic;

import android.os.Build;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static java.util.Objects.isNull;

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


    public void addListener(final FirebaseFuncsCallback<ItineraryObject> firebaseCallback, final SearchQueryObject searchQueryObject) {
        this.firebaseCallback = firebaseCallback;
        this.listener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ItineraryObject> itins = new ArrayList<>();
                String currentLocation = searchQueryObject.getLocation();
                String[] tags = searchQueryObject.getTags();
                DataSnapshot item = dataSnapshot.child(currentLocation);

                HashMap hash = (HashMap) item.getValue();
                if (isNull(hash)){
                    itins = new ArrayList<ItineraryObject>();
                } else {
                    itins = handleHash(hash,tags);
                }


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
    public ArrayList<ItineraryObject> handleHash(HashMap<String, HashMap> map,String[] neededTags) {
        Iterator<String> keys = map.keySet().iterator();
        ArrayList<ItineraryObject> returned = new ArrayList<>();
        String key = "";
        while (keys.hasNext()) {
            key = keys.next();
            if (!key.equals("Tags")) {
                boolean hasAll = true;

                HashMap<String, Object> results = map.get(key);
                String itineraryName = (String) results.get("itineraryName");
                String creatorName = (String) results.get("creatorName");
                int numStops = Math.toIntExact((Long) results.get("numStops"));
                String location = (String) results.get("location");
                ArrayList<Stop> badStops = (ArrayList<Stop>) results.get("stops");
                ArrayList<Stop> stops = fixStop(badStops);
                int numLikes = Math.toIntExact((Long) results.get("numLikes"));
                ArrayList<String> tags = (ArrayList<String>) results.get("tags");
//            ArrayList<String> access = (ArrayList<String>) results.get("access");
                String coverPhoto = (String) results.get("coverPhoto");;
                ArrayList<String> access = new ArrayList<>();
                ItineraryObject itinerary = new ItineraryObject(creatorName, itineraryName, numLikes,
                        coverPhoto, location, numStops, stops, tags,
                        access);
                if (neededTags.length == 0) {
                } else {
                    for (int i = 0; i < neededTags.length; i++) {
                        ArrayList<String> itinTags = itinerary.getTags();
                        if (itinTags.contains(neededTags[i])) {
                        } else {
                            hasAll = false;
                        }
                    }
                }

                if (hasAll == true) {
                    returned.add(itinerary);
                } else {
                }
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
        //            Below this fixes the random new fields that it added
        myRef.child(location).child(itin.getItineraryName()).child("liked").removeValue();
        myRef.child(location).child(itin.getItineraryName()).child("bookmarked").removeValue();


    }


}