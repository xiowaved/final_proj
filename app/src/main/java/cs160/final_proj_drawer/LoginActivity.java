package cs160.final_proj_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
    public ItineraryObject createTestItinerary(){
//        create a test itinerary with my information
        List<String> photolist = new ArrayList<>();
        List<Stop> stoplist = new ArrayList<>();
        List<String> taglist = new ArrayList<>();
        List<String> acclist = new ArrayList<>();
        Stop latimer = new Stop(photolist, "Latimer Hall", "UC Berkeley", "Mercury is stored here", 0);
        stoplist.add(latimer);
        taglist.add("chemistry");
        acclist.add("elevator");
        ItineraryObject itin = new ItineraryObject("Colby", "Chemistry lyfe", -1,
                "blackness", 1, stoplist,taglist ,
                acclist);
        return itin;
    }

    public void saveText(final View view) {
        EditText username = findViewById(R.id.editText);
        String user = username.getText().toString();
//        I made a little Hashmap to test the functionality of it for later use for tags
        HashMap<String,String> testmap = new HashMap<>();
        testmap.put("test","successful");
//        The URL is pretty hardcoded right now but easily changeable
        ItineraryObject chem = createTestItinerary();
        myRef.child("Berkeley").child("Chemistry").setValue(chem)
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
                        Snackbar.make(view, e.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });


//
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void readData(final View view) {
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