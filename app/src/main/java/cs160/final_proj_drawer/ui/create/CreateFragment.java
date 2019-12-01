package cs160.final_proj_drawer.ui.create;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

public class CreateFragment extends Fragment {

    private NavController navController;

    ItineraryObject createdItin;
    EditText name;
    EditText location;
    EditText description;
    Button addNewPhoto;
    Button viewPreviousStop;
    Button addNewStop;
    Button reviewItinerary;
    //list of itineraries
    int currentStopIndex; // the itinerary index of what is displayed on the screen

    List<Stop> stops;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create, container, false);
//        final TextView textView = root.findViewById(R.id.text_create);
//        createViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        // SELMA passing fake stops to reviewFragment until we finish actual functionality of this page
        stops = new ArrayList<Stop>();
        stops.add(new Stop(new ArrayList<String>(), "Oakland Zoo", "1234 tst st. oakland,CA", "so fun so cool lots of" +
                "words awesome palce 10/10 i love it here", 0));
        stops.add(new Stop(new ArrayList<String>(), "Little Farm", "2395 Durant Ave. Berkeley,CA", "lots of animals" +
                "you can feed the cows come on a sunny day because lots of grass and muddy in rain", 1));
        createdItin = new ItineraryObject("creatorName", name.getText().toString(), 0,
                "coverPhoto", stops.size(), stops, new ArrayList<String>(), new ArrayList<String>());

        Log.i("TAG", "load review fragment");
        //load review fragment

        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", createdItin);

        navController.navigate(R.id.action_nav_create_to_review, bundle);

        //TODO commit to firebase
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