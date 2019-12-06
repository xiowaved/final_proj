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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

public class CreateStopsFragment extends Fragment {

    private NavController navController;

    ItineraryObject createdItin;
    String itinName;
    String itinLocation;

    EditText stopNameTextView;
    EditText stopLocationTextView;
    EditText stopDescriptionTextView;

    Button addNewPhoto;
    Button viewPreviousStop;
    Button addNewStop;
    Button reviewItinerary;
    TextView itineraryNameTextView;
    //list of itineraries
    int currentStopIndex; // the itinerary index of what is displayed on the screen




    ArrayList<Stop> stops;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Locations");

    // store the itinerary object that is passed into this fragment
    private ItineraryObject itineraryObject;

    private boolean editing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create_stops, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        // Get itinerary object passed into this fragment
        Bundle bundle=getArguments();
        itineraryObject = (ItineraryObject) bundle.getSerializable("itinerary");
        currentStopIndex = bundle.getInt("stopIndex");
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // set itin name and location from itinerary object that was passed into this fragment
        itineraryNameTextView = (TextView) getView().findViewById(R.id.itineraryName);
        itineraryNameTextView.setText(itineraryObject.getItineraryName());

        // index to keep track of which stop we're on; used to populate/clear text in UI
//        currentStopIndex = 0;

        // get UI elements
        stopNameTextView = (EditText) getView().findViewById(R.id.name);
        stopLocationTextView = (EditText) getView().findViewById(R.id.location);
        stopDescriptionTextView = (EditText) getView().findViewById(R.id.description);
        addNewPhoto = (Button) getView().findViewById(R.id.photos);
        viewPreviousStop = (Button) getView().findViewById(R.id.viewPreviousStop);
        addNewStop = (Button) getView().findViewById(R.id.addNewStop);
        reviewItinerary = (Button) getView().findViewById(R.id.reviewItinerary);


//        stops = new ArrayList<Stop>();
        // get the list of stops from the itinerary object passed into this fragment
        stops = itineraryObject.getStops();
        if (stops.isEmpty()) {
            // if fragment received an itinerary with NO stops, then navigated here from CREATEOVERVIEW
            Log.i("Create Stops", "navigated from CreateOverview");
            editing = false;
//            currentStopIndex = 0;
        } else {
            // if fragment received an itinerary WITH stops, then navigated here from REVIEWITINERARY
            Log.i("Create Stops", "navigated from ReviewItin");
            editing = true;
            // get current stop index from fragment bundle
//            currentStopIndex = ;
            // populate the editTexts
            Stop currStop = stops.get(currentStopIndex);
            stopNameTextView.setText(currStop.getName());
            stopLocationTextView.setText(currStop.getLocation());
            stopDescriptionTextView.setText(currStop.getDescription());
        }

        Log.i("create stops", "" + currentStopIndex);
        // add onClick listeners for the buttons in the UI
        addNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo add new photo logic here!
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
        String savedName = stopNameTextView.getText().toString();
        String savedLocation = stopLocationTextView.getText().toString();
        String savedDescription = stopDescriptionTextView.getText().toString();
            // before navigating to reviewItin page, save the current stop entry in the UI
        if (currentStopIndex > stops.size() - 1) { // if you are adding a new stop
//            String savedName = stopNameTextView.getText().toString();
//            String savedLocation = stopLocationTextView.getText().toString();
//            String savedDescription = stopDescriptionTextView.getText().toString();
        // if no fields left blank, add this stop to the global list of stops
            if (!(savedName.equals("")) && !(savedLocation.equals("")) && !(savedDescription.equals(""))) {
                Stop stop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
                stops.add(stop);

    //                    if (creating) {
    //                        itineraryObject.addStop(stop);
    //                    } else {
    //                        // replace stop at position you are editing
    //                        itineraryObject.replaceStop(currentStopIndex,stop);
    //                    }
            } else {
                // some fields left blank in UI by user
            }
        }
            // replace currentStopIndex'th entry in stops to reflect user's updates in textviews
            //todo breaking @ oldStop w index out of bounds error
//            String savedName = stopNameTextView.getText().toString();
//            String savedLocation = stopLocationTextView.getText().toString();
//            String savedDescription = stopDescriptionTextView.getText().toString();
        if (!(savedName.equals("")) && !(savedLocation.equals("")) && !(savedDescription.equals(""))) {
            Stop oldStop = stops.get(currentStopIndex);
            Stop revisedStop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
            stops.set(currentStopIndex, revisedStop);
        }


        // clear the UI textviews
        stopNameTextView.getText().clear();
        stopLocationTextView.getText().clear();
        stopDescriptionTextView.getText().clear();

        itineraryObject.setStops(stops);
        itineraryObject.setNumStops(stops.size());
        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", itineraryObject);
        navController.navigate(R.id.fragment_review, bundle);

    }

    public void onAddNewStop(View view) {
        if (currentStopIndex < stops.size() - 1) { // if before add is not the last added stop
            String savedName = stopNameTextView.getText().toString();
            String savedLocation = stopLocationTextView.getText().toString();
            String savedDescription = stopDescriptionTextView.getText().toString();
            Stop oldStop = stops.get(currentStopIndex);
            Stop revisedStop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
            stops.set(currentStopIndex, revisedStop);

            currentStopIndex++;
            Stop existingStop = stops.get(currentStopIndex);
            stopNameTextView.setText(existingStop.getName());
            stopLocationTextView.setText(existingStop.getLocation());
            stopDescriptionTextView.setText(existingStop.getDescription());
        } else if (currentStopIndex == stops.size() - 1) { // if before add is the last stop

            String savedName = stopNameTextView.getText().toString();
            String savedLocation = stopLocationTextView.getText().toString();
            String savedDescription = stopDescriptionTextView.getText().toString();
            Stop oldStop = stops.get(currentStopIndex);
            Stop revisedStop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
            stops.set(currentStopIndex, revisedStop);

            currentStopIndex++;
            stopNameTextView.getText().clear();
            stopLocationTextView.getText().clear();
            stopDescriptionTextView.getText().clear();
        } else { // if you are adding a new stop (currentStopIndex > stops.size() - 1)
            String savedName = stopNameTextView.getText().toString();
            String savedLocation = stopLocationTextView.getText().toString();
            String savedDescription = stopDescriptionTextView.getText().toString();
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
            stopNameTextView.getText().clear();
            stopLocationTextView.getText().clear();
            stopDescriptionTextView.getText().clear();
        }
        Log.i("currentStopIndex", "" + currentStopIndex);
    }

    public void onViewPreviousStop(View view) {
        // todo this func always errors out with outofbounds exception with oldStop=...
        //hide and unhide previous stop button
        if (currentStopIndex > 0) {
            if (editing) {
                String savedName = stopNameTextView.getText().toString();
                String savedLocation = stopLocationTextView.getText().toString();
                String savedDescription = stopDescriptionTextView.getText().toString();
                Stop oldStop = stops.get(currentStopIndex);
                Stop revisedStop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
                stops.set(currentStopIndex, revisedStop);
            }
//            String savedName = stopNameTextView.getText().toString();
//            String savedLocation = stopLocationTextView.getText().toString();
//            String savedDescription = stopDescriptionTextView.getText().toString();
//            Stop oldStop = stops.get(currentStopIndex);
//            Stop revisedStop = new Stop(new ArrayList<String>(), savedName, savedLocation, savedDescription, currentStopIndex);
//            stops.set(currentStopIndex, revisedStop);


            currentStopIndex--;
            Stop previousStop = stops.get(currentStopIndex);
            stopNameTextView.setText(previousStop.getName());
            stopLocationTextView.setText(previousStop.getLocation());
            stopDescriptionTextView.setText(previousStop.getDescription());
            Log.i("currentStopIndex", "" + currentStopIndex);
        }
    }
}