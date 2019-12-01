package cs160.final_proj_drawer.ui.create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
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
            ArrayList<Stop> stopsList = (ArrayList<Stop>) itin.getStops();

            for (int i = 0; i < stopsList.size(); i++) {
//                Stop stop = new Stop(new ArrayList<String>(), "Oakland Zoo", "9777 Golf Links Rd. Oakland, CA",
//                        "Lots of fields for outdoor picnics with the kids. Food...", i);
                Stop stop = stopsList.get(i);
                stopsList.add(stop);
            }
            // put them in
            stopAdapter = new StopAdapter(stopsList);
            stops.setAdapter(stopAdapter);


            return root;
        }
    }
}
