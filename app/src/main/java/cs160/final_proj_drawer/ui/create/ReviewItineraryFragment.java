package cs160.final_proj_drawer.ui.create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.StopAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

/**
 *  this is the UI page to review
 *  an itinerary user created in CreateStopsFragment
 */
public class ReviewItineraryFragment extends Fragment {

    private RecyclerView stops;
    private StopAdapter stopAdapter;
    private TextView errorMsg;
    private ItineraryObject itin;
    private Button submit;

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

        Bundle bundle=getArguments();
        itin = (ItineraryObject) bundle.getSerializable("itinerary");

        if (itin.getStops().isEmpty()) {
            // something to catch potential null exceptions later
            errorMsg.setText("No stops found.");
            stopAdapter = new StopAdapter(new ArrayList<Stop>());
            stops.setAdapter(stopAdapter);
            return root;
        } else {
            ArrayList<Stop> itinStops = (ArrayList<Stop>) itin.getStops();

            // populate cardviews with itinerary's stops from createFragment
            stopAdapter = new StopAdapter(itinStops);
            stops.setAdapter(stopAdapter);

            // set the name and location
            EditText itinName = (EditText) root.findViewById(R.id.name);
            itinName.setText(itin.getItineraryName());
            return root;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // set itinerary info in UI
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        itinName.setText(itin.getItineraryName());

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
}


