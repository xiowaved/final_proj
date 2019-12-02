package cs160.final_proj_drawer.ui.create;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;
import cs160.final_proj_drawer.adapters.StopAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

/**
 *  this is the UI page to review
 *  an itinerary user created in CreateStopsFragment
 */
public class ReviewItineraryFragment extends Fragment implements OnRecyclerCardListener {

    private RecyclerView stops;
    private StopAdapter stopAdapter;
    private TextView errorMsg;
    private ItineraryObject itineraryObject;
    private Button submit;
    private ArrayList<Stop> itinStops;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_review_itinerary, container, false);

        // get UI stuff
        errorMsg = (TextView) root.findViewById(R.id.errorMsg);

        //recycler view setup
        stops = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager stopsLayoutManager = new LinearLayoutManager(getActivity());
        stopsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stops.setLayoutManager(stopsLayoutManager);

        // get itinerary object from previous fragment
        Bundle bundle=getArguments();
        itineraryObject = (ItineraryObject) bundle.getSerializable("itinerary");

        if (itineraryObject.getStops().isEmpty()) {
            // something to catch potential null exceptions later
            errorMsg.setText("No stops found.");
            itinStops = new  ArrayList<Stop>();
            stopAdapter = new StopAdapter(itinStops, this);
            stops.setAdapter(stopAdapter);
            return root;
        } else {
            // get user's stops and populate cardviews in UI
            itinStops = (ArrayList<Stop>) itineraryObject.getStops();
            stopAdapter = new StopAdapter(itinStops, this);
            stops.setAdapter(stopAdapter);
            // set the itinerary name and location editText's entries in UI
            EditText itinName = (EditText) root.findViewById(R.id.name);
            itinName.setText(itineraryObject.getItineraryName());
            return root;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // set itinerary info in UI
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        itinName.setText(itineraryObject.getItineraryName());

        // set listener for button when user submits itin
        submit = (Button) getView().findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!errorMsg.getText().toString().equals("")) {
                    // do NOT write to firebase if itinerary has no stops
                    //todo do we want to give some personalized error message? or just do nothing
                } else {
                    // write itinerary to firebase

                    // navigate back to home splash screen
                }
            }
        });



    }



    @Override
    public void onCardClick(int position) {
        Stop selectedStop = itinStops.get(position);
        Toast toast = Toast.makeText(getContext(), "clicked on stop #" + String.valueOf(position),
                Toast.LENGTH_SHORT);
        toast.show();
        // TODO
        /** if click on edit button
         *      pass itinStops, position to navController.navigate(R.id.fragment_create_stops)
         *
         *  if click on delete button
         *      remove this stop from private itin's stops
         *      remove this card from recycler view
         *      (might need to shift other cards to fill gap? unless it does it automatically)
        */

    }
}


