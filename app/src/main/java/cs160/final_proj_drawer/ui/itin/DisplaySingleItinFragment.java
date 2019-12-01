package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;
import cs160.final_proj_drawer.adapters.VertStopAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

/*  This UI element displays a single itinerary
 */
public class DisplaySingleItinFragment extends Fragment implements OnRecyclerCardListener {
    //IMPORTANT do some quality control. make sure you can handle if the itinerary handed to it
    //is null, or is missing stops, or other issues.
    //dont let the app crash if this is handed garbage

    //ui elements
    private TextView title;
    private TextView body;
    private Boolean bookmarked;
    private TextView numLikes;
    //stuff for the recycler
    private RecyclerView itinStops;
    private VertStopAdapter stopAdapter;

    //logic data
    private ItineraryObject itin;
    private ArrayList<Stop> stops;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itin, container, false);

        //find stuff
        title = root.findViewById(R.id.Title);
        body = root.findViewById(R.id.body);
        numLikes = root.findViewById(R.id.numLikes);

        //recycler view setup
        itinStops = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager stopLayoutManager = new LinearLayoutManager(getActivity());
        stopLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itinStops.setLayoutManager(stopLayoutManager);


        //get args, exit if there wasn't an itin object handed to it
        Bundle bundle=getArguments();
        Object input = bundle.getSerializable("itinerary");
        if (input == null) {
            title.setText("No Itinerary Found");
            return root;
        }

        //get overarching itin stuff set up
        itin = (ItineraryObject) input;
        stops = itin.getStops();
        title.setText(itin.getItineraryName());
        numLikes.setText(Integer.toString(itin.getNumLikes()));

        //check if the itin object has stops, exit if it doesn't
        if (stops == null || stops.size() == 0) {
            title.setText(itin.getItineraryName() + " has no stops");
            return root;
        }

        //stopAdapter = new VertStopAdapter(stops, this);
        //itinStops.setAdapter(stopAdapter);

        //delete later
        /*
        String testInfo  = "";
        for (Stop stop:stops) {
            testInfo+= "\n" + stop.getName();
        }
        body.setText(testInfo);*/


        return root;
    }

    @Override
    public void onCardClick(int position) {
        return;
        //Log.i("NOTE", "stop clicked in displaySingleItin "+position);
    }
}
