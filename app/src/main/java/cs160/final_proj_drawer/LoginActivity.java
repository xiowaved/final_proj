package cs160.final_proj_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.MySingleton;
import cs160.final_proj_drawer.logic.Stop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Locations");
//    note this is hardcoded for now, but since the URL is a string its easily modifiable
    String url = "https://travelr-7feac.firebaseio.com/Locations/Berkeley/Chemistry.json";


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mStorageRef = FirebaseStorage.getInstance().getReference();
        //context = getApplicationContext();

    }
    public void createTestItinerary(View view){
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


    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void readData(final View view) {
        // kind of robust JSON reading but we can change it as needed.
        // Right now this method isn't called but you can make one of the buttons in loginActivity to call this.
//        it'll show my itinerary name at the bottom of the screen when called.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        String name = null;
                        try {
                            name = response.getString("itineraryName");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Snackbar.make(view, name, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        MySingleton mySingleton = new MySingleton(this);
        mySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }
}