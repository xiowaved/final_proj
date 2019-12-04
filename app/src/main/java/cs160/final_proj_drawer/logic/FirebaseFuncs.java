package cs160.final_proj_drawer.logic;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import cs160.final_proj_drawer.R;

/* This is all of our functions that interact with firebase.
   They are created here, as seperate from UI as possible, so we can
   Call them freely from any other part of our app
 */
public class FirebaseFuncs<Model> {


    /*  stuff to access our project's firebase database.
            if we change stuff on our firebase, we have to
            change these
         */
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("Locations");
    public static String url = "https://travelr-7feac.firebaseio.com/Locations/";
    public FirebaseFuncsCallback<ItineraryObject> firebaseCallback;
    private ValueEventListener listener;
    static StorageReference storage = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;

    public interface FirebaseFuncsCallback<ItineraryObject> {
        void onSuccess(ArrayList<ItineraryObject> result);

        void onError(Exception e);
    }

//    when we get this working it won't need to take in Context anymore
    public static void putImage(Context context){
// This is an example of using a drawable png
        int pictureID = R.drawable.bookmark;
          Uri filePath = getUriToDrawable(context,pictureID);
        /**/

        final StorageReference storageRef= storage.child("new folder/third_bookmark");
//
//
//        convert the image to a Bitmap (easier to put in)
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(),pictureID);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        storageRef.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String URL = uri.toString();
                            Log.i("URL",URL);
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
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    public void addListener(final FirebaseFuncsCallback<ItineraryObject> firebaseCallback) {
        this.firebaseCallback = firebaseCallback;
        this.listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ItineraryObject> itins = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    itins.add(item.getValue(ItineraryObject.class));
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
    public static void getItineraries(final ArrayList<ItineraryObject> list, String url, final Context context){

        Log.i("getItin", "called getItin");
        // I did it this way b/c there might be itineraries with names that come after tags.
//        that's why its this two-tier search.
//        First it goes through all the itineraries, ignoring the tags folder, and gets to the last one and adds it
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

                                    JSONObject itin = info.getJSONObject(name);
                                    Log.i("getItin", "before filling itin "+ name);
                                    ItineraryObject itinerary = new ItineraryObject(itin);
//                                   HERE is where the itinerary is added once its fully been constructed
                                    list.add(itinerary);

                                } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                                }
                            } else {}
                        }
                        if (name != "Tags") {
                            try {

                                ItineraryObject itinerary = new ItineraryObject(info.getJSONObject("name"));
//                                HERE is where the itinerary is added once its fully been constructed
                                list.add(itinerary);
                                Log.i("getItin", "should have added (second if)");

                            } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                            }
                        } else {}

//this is where it's done. we want a way for it to convey it is done. maybe loop / wait until this mutates a bool val?
//                    return list;

                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton mySingleton = new MySingleton(context);
        mySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }
    public static void getNestedItineraries(final ArrayList<ItineraryObject> list, String givenUrl, final Context context, final ArrayList<String> neededTags){

//        Log.i("getItin", "called getItin");
        // I did it this way b/c there might be itineraries with names that come after tags.
//        that's why its this two-tier search.
//        First it goes through all the itineraries, ignoring the tags folder, and gets to the last one and adds it
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,givenUrl,null,
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
                                    Log.i("getItin", "before filling itin "+ name);
                                    ItineraryObject itinerary = new ItineraryObject(itin);
                                    for (int i = 0; i < neededTags.size(); i++) {
                                        ArrayList<String> tags = itinerary.getTags();
                                        if (tags.contains(neededTags.get(i))) {

                                        } else {
                                            hasAll = false;

                                        }
                                    }
                                    if (hasAll == true) {
                                        list.add(itinerary);
                                    } else {};

//                                   HERE is where the itinerary is added once its fully been constructed
                                    //Log.i("getItin", "should have added (first if)");

                                    //Log.i("getItin", "should have added (first if)");

                                } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                                }
                            } else {}
                        }
                        if (!name.equals("Tags")) {
                            try {

                                boolean hasAll = true;


                                JSONObject itin = info.getJSONObject(name);
                                Log.i("getItin", "before filling itin "+ name);
                                ItineraryObject itinerary = new ItineraryObject(itin);
                                for (int i = 0; i < neededTags.size(); i++) {
                                    ArrayList<String> tags = itinerary.getTags();
                                    if (tags.contains(neededTags.get(i))) {

                                    } else {
                                        hasAll = false;
                                    }
                                }
                                if (hasAll == true) {
                                    list.add(itinerary);
                                } else {};

                            } catch (JSONException e) {
//                                    this is required for code to work, ignore it
                            }
                        } else {}

//this is where it's done. we want a way for it to convey it is done. maybe loop / wait until this mutates a bool val?
//                    return list;

                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton mySingleton = new MySingleton(context);
        mySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


    public static void createTestItinerary(View view){
//        create a test itinerary with my information
        List<String> photolist = new ArrayList<>();
        List<Stop> stoplist = new ArrayList<>();
        List<String> taglist = new ArrayList<>();
        List<String> acclist = new ArrayList<>();
        Stop Safeway = new Stop(photolist, "Safeway", "6310 College Ave, Oakland, CA 94618", "I stopped here to pickup some meat. " +
                "They have pretty good deals here and I walked away with some pork loin that was on sale. They're also open 24 hours!", 0);
        Stop BerkeleyBowl = new Stop(photolist, "Berkeley Bowl", "2020 Oregon St, Berkeley, CA 94703", "Very diverse set of produce. " +
                "Large selection and good prices on fruits/veggies not so great prices on everything else.", 1);
        Stop WholeFoods = new Stop(photolist, "Whole Foods", "3000 Telegraph Ave, Berkeley, CA 94705", "Wide selection of organic food" +
                "a little pricey, but they have a good selection of exotic food/drinks, such as kombucha", 2);
        stoplist.add(Safeway);
        stoplist.add(BerkeleyBowl);
        stoplist.add(WholeFoods);
        taglist.add("shopping");
        taglist.add("grocery");
        taglist.add("food");
        acclist.add("elevator");
//        ItineraryObject itin1 = new ItineraryObject("Colby", "Grocery Shopping", 15,
//                "blackness", 3, stoplist,taglist ,
//                acclist);
        photolist = new ArrayList<>();
        stoplist = new ArrayList<>();
        taglist = new ArrayList<>();
        acclist = new ArrayList<>();
        Stop Artis = new Stop(photolist, "Artis", "1717 Fourth St B, Berkeley, CA 94710", "They have great coffee and beans! " +
                "I always get a Lavender Latte. Get there early if you plan on staying!", 0);
        Stop AmazonFourStar = new Stop(photolist, "Amazon Four Star", "1787 Fourth St, Berkeley, CA 94710", "Cool place to stop by for gift ideas. " +
                "They also have a nice mix of books,toys, and tech.", 2);
        Stop CasaLatina = new Stop(photolist, "Casa Latina", "1805 San Pablo Ave, Berkeley, CA 94702", "They have so many Mexican pastries!" +
                "Their breakfast burritos are to die for! Get a glass of fresh squeezed orange juice, too", 1);
        stoplist.add(Artis);
        stoplist.add(CasaLatina);
        stoplist.add(AmazonFourStar);
        taglist.add("shopping");
        taglist.add("coffee");
        taglist.add("food");
        taglist.add("mexican");
        taglist.add("family friendly");
        acclist.add("ADA");
//        ItineraryObject itin2 = new ItineraryObject("Colby", "A day off in West Berkeley", 167,
//                "blackness", 3, stoplist,taglist ,
//                acclist);
        photolist = new ArrayList<>();
        stoplist = new ArrayList<>();
        taglist = new ArrayList<>();
        acclist = new ArrayList<>();
        Stop LBL = new Stop(photolist, "Lawrence Berkeley Labs", "1 Centennial Dr, Berkeley, CA 94720", "Great views of the whole bay! " +
                "It's also cool to visit at night but be safe! others visit too.", 0);
        Stop FireTrails = new Stop(photolist, "Fire Trails", "425 Lower Fire Trail, Berkeley, CA 94704", "Nice place to run! Good if you want to train for hills " +
                "Pretty difficult but if you can run it you feel great! Nice views along the way", 1);
        stoplist.add(LBL);
        stoplist.add(FireTrails);
        taglist.add("hiking");
        taglist.add("running");
        taglist.add("views");
        taglist.add("scenic");
//        ItineraryObject itin3 = new ItineraryObject("Colby", "Places with a view", 15,
//                "blackness", 2, stoplist,taglist ,
//                acclist);
        ArrayList<ItineraryObject> groceryTag = new ArrayList<>();
        ArrayList<ItineraryObject> shoppingTag = new ArrayList<>();
        ArrayList<ItineraryObject> hikingTag = new ArrayList<>();
        ArrayList<ItineraryObject> foodTag = new ArrayList<>();
        ArrayList<ItineraryObject> viewsTag = new ArrayList<>();
        ArrayList<ItineraryObject> scenicTag = new ArrayList<>();
        ArrayList<ItineraryObject> familyfriendlyTag = new ArrayList<>();
        ArrayList<ItineraryObject> runningTag = new ArrayList<>();
        ArrayList<ItineraryObject> mexicanTag = new ArrayList<>();
        ArrayList<ItineraryObject> coffeeTag = new ArrayList<>();
        HashMap<String,ArrayList<ItineraryObject>> LocationTags = new HashMap<>();
//        groceryTag.add(itin1);
//        shoppingTag.add(itin1);
//        shoppingTag.add(itin2);
//        hikingTag.add(itin3);
//        foodTag.add(itin1);
//        foodTag.add(itin2);
//        viewsTag.add(itin3);
//        scenicTag.add(itin3);
//        familyfriendlyTag.add(itin2);
//        runningTag.add(itin3);
//        mexicanTag.add(itin2);
//        coffeeTag.add(itin2);
        LocationTags.put("grocery",groceryTag);
        LocationTags.put("shopping",shoppingTag);
        LocationTags.put("hiking",hikingTag);
        LocationTags.put("food",foodTag);
        LocationTags.put("views",viewsTag);
        LocationTags.put("scenic",scenicTag);
        LocationTags.put("family friendly",familyfriendlyTag);
        LocationTags.put("running",runningTag);
        LocationTags.put("mexican",mexicanTag);
        LocationTags.put("coffee",coffeeTag);
//        myRef.child("Berkeley").child("Grocery Shopping").setValue(itin1)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Write was successful!
//                        myRef.push();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Write failed
//                    }
//                });
//        myRef.child("Berkeley").child("A day off in West Berkeley").setValue(itin2)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Write was successful!
//                        myRef.push();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Write failed
//                    }
//                });
//        myRef.child("Berkeley").child("Places with a View").setValue(itin3)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Write was successful!
//                        myRef.push();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Write failed
//                    }
//                });
        myRef.child("Berkeley").child("Tags").setValue(LocationTags)
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

//
////



    }

}
