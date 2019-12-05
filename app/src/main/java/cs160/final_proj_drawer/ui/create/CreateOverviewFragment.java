package cs160.final_proj_drawer.ui.create;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;


public class CreateOverviewFragment extends Fragment {

    private NavController navController;
    private Button addStopsButton;
    private ImageButton coverPhotoButton;
    private String itineraryName;
    private String itineraryLocation;
    private final int IMG_REQUEST = 1;

    private String coverPhoto;
    private Bitmap bitmapsimg;

    private String apiUrl = "http://192.168.1.12/api/index.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_overview, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // get the EditTexts so you can query user's itinerary info inputs
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        final TextView errorMsg = (TextView) getView().findViewById(R.id.errorMsg);
        coverPhoto = "";
        // set onClick listener for the buttons
        coverPhotoButton = (ImageButton) getView().findViewById(R.id.coverPhoto);
        coverPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "clicked on plus button",
                        Toast.LENGTH_LONG); toast.show();
                //todo -- Chaitanya this is where you add the code that you want to execute when user
                // clicks on the button to add a cover photo
                switch (v.getId()) {
                    case R.id.coverPhoto:
                        selectimage();
                        uploadimage();
                }
            }



        });



        addStopsButton = (Button) getView().findViewById(R.id.button);
        addStopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryName = itinName.getText().toString();
                itineraryLocation = itinLocation.getText().toString();

                // if user left name, location field, or cover photo blank, do not let them navigate to next page yet
//                if (itineraryName.isEmpty() || itineraryLocation.isEmpty() || coverPhoto.isEmpty()) {
                // todo IGNORING NO COVER PHOTO SET FOR NOW TO TEST LATER PARTS OF CREATION PROCESS
                //  use the commented out conditional that checks all 3 fields in final version of app
                if (itineraryName.isEmpty() || itineraryLocation.isEmpty()) {

                // user did not fill in required fields
                    errorMsg.setText("Fill in all fields to continue.");
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    // pass a partially filled-out itinerary object to the createStops fragment
                    ItineraryObject itinerary = new ItineraryObject("creatorName", itineraryName,
                            0, coverPhoto, itineraryLocation, 0, new ArrayList<Stop>(),
                            new ArrayList<String>(), new ArrayList<String>(), false);
                    bundle.putSerializable("itinerary", itinerary);

//                    bundle.putString("name", itineraryName);
//                    bundle.putString("location", itineraryLocation);
                    navController.navigate(R.id.fragment_create_stops,bundle);
                }
            }
        });

    }

    //helper function

    private void uploadimage(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject  = new JSONObject(response);

                            String Response = jsonObject.getString("response");
                            Toast.makeText(getActivity(), Response,Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                return params;
            }
        };
        PhotoHelper.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    public void selectimage(){
        Intent imgintent = new Intent();
        imgintent.setType("image/*");
        imgintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(imgintent,IMG_REQUEST);

    }

}
