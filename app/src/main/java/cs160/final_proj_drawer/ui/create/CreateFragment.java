package cs160.final_proj_drawer.ui.create;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import cs160.final_proj_drawer.adapters.StopAdapter;

import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

public class CreateFragment extends Fragment {

    private NavController navController;

    ItineraryObject createdItin;
    String itinName;
    String itinLocation;
    EditText name;
    EditText location;
    EditText description;
    Button addNewPhoto;
    Button viewPreviousStop;
    Button addNewStop;
    Button reviewItinerary;
    TextView itineraryName;
    //list of itineraries
    int currentStopIndex; // the itinerary index of what is displayed on the screen

    List<Stop> stops;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Locations");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create, container, false);
//        final TextView textView = root.findViewById(R.id.text_create);
////        createViewModel.getText().observe(this, new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        // Get itinerary name and location provided by user on initial creation page
        Bundle bundle=getArguments();
        // these are checked for validity before they're passed to the bundle into this fragment
        itinName = bundle.getString("name");
        itinLocation = bundle.getString("location");

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        itineraryName = (TextView) getView().findViewById(R.id.itineraryName);
        itineraryName.setText(itinName);

        currentStopIndex = 0;

        name = (EditText) getView().findViewById(R.id.name);
        location = (EditText) getView().findViewById(R.id.location);
        description = (EditText) getView().findViewById(R.id.description);

        addNewPhoto = (Button) getView().findViewById(R.id.photos);
        viewPreviousStop = (Button) getView().findViewById(R.id.viewPreviousStop);
        addNewStop = (Button) getView().findViewById(R.id.addNewStop);
        reviewItinerary = (Button) getView().findViewById(R.id.reviewItinerary);

        stops = new ArrayList<Stop>();

        addNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new photo logic here!
                Toast toast = Toast.makeText(getContext(), "clicked on add new photo",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        viewPreviousStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewPreviousStop(v);
            }
        });

        addNewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddNewStop(v);
            }
        });

        reviewItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReviewItinerary(v);
            }
        });
    }

    public void onReviewItinerary(View view) {
        if (currentStopIndex > stops.size() - 1) { // if you are adding a new stop
            String savedName = name.getText().toString();
            String savedLocation = location.getText().toString();
            String savedDescription = description.getText().toString();
            if (!(savedName.equals("")) && !(savedLocation.equals("")) && !(savedDescription.equals(""))) {
                Stop stop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
                stops.add(stop);
            } else {
                //incomplete fields
            }
            Log.i("savedName", savedName);
            Log.i("savedLocation", savedLocation);
            Log.i("savedDescription", savedDescription);
        }
        name.getText().clear();
        location.getText().clear();
        description.getText().clear();


        createdItin = new ItineraryObject("creatorName", name.getText().toString(), 0,
                "coverPhoto",location.getText().toString(), stops.size(), stops, new ArrayList<String>(), new ArrayList<String>());


        Log.i("TAG", "load review fragment");
        //load review fragment

        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", createdItin);

        navController.navigate(R.id.action_addStop_to_review, bundle);


        myRef.child(location.getText().toString()).child(name.getText().toString()).setValue(createdItin)
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

    }

    public void onAddNewStop(View view) {
        if (currentStopIndex < stops.size() - 1) { // if before add is not the last added stop
            currentStopIndex++;
            Stop existingStop = stops.get(currentStopIndex);
            name.setText(existingStop.getName());
            location.setText(existingStop.getLocation());
            description.setText(existingStop.getDescription());
        } else if (currentStopIndex == stops.size() - 1) { // if before add is the last stop
            currentStopIndex++;
            name.getText().clear();
            location.getText().clear();
            description.getText().clear();
        } else { // if you are adding a new stop (currentStopIndex > stops.size() - 1)
            String savedName = name.getText().toString();
            String savedLocation = location.getText().toString();
            String savedDescription = description.getText().toString();
            if (!(savedName.equals("")) && !(savedLocation.equals("")) && !(savedDescription.equals(""))) {
                Stop stop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
                stops.add(stop);
                currentStopIndex++;
            } else {
                //incomplete fields
            }
            Log.i("savedName", savedName);
            Log.i("savedLocation", savedLocation);
            Log.i("savedDescription", savedDescription);
            name.getText().clear();
            location.getText().clear();
            description.getText().clear();
        }
        Log.i("currentStopIndex", "" + currentStopIndex);
    }

    public void onViewPreviousStop(View view) {
        //hide and unhide previous stop button
        if (currentStopIndex > 0) {
            currentStopIndex--;
            Stop previousStop = stops.get(currentStopIndex);
            name.setText(previousStop.getName());
            location.setText(previousStop.getLocation());
            description.setText(previousStop.getDescription());
            Log.i("currentStopIndex", "" + currentStopIndex);
        }
    }
}