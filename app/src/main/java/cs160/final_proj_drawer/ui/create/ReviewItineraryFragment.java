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
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.StopAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

/**
 *  this is the UI page to review
 *  an itinerary user created in CreateFragment
 */
public class ReviewItineraryFragment extends Fragment {

    private RecyclerView stops;
    private StopAdapter stopAdapter;
    private TextView errorMsg;
    private ItineraryObject itin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_review_itinerary, container, false);

        errorMsg = (TextView) root.findViewById(R.id.errorMsg);
        //recycler view setup
        stops = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager stopsLayoutManager = new LinearLayoutManager(getActivity());
        stopsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stops.setLayoutManager(stopsLayoutManager);

        Bundle bundle=getArguments();
        Object input = bundle.getSerializable("itinerary");

        if (input == null) {
            // something to catch potential null exceptions later
            errorMsg.setText("No stops found.");
            stopAdapter = new StopAdapter(new ArrayList<Stop>());
            stops.setAdapter(stopAdapter);
            return root;
        } else {
            itin = (ItineraryObject) input;
            ArrayList<Stop> itinStops = (ArrayList<Stop>) itin.getStops();

            // populate cardviews with itinerary's stops from createFragment
            stopAdapter = new StopAdapter(itinStops);
            stops.setAdapter(stopAdapter);

            return root;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        itinName.setText(itin.getItineraryName());
//        itinLocation.setText(itin.get);
    }
}


