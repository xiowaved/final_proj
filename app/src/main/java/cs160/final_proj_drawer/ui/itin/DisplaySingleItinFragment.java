package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

/*  This UI element displays a single itinerary
 */
public class DisplaySingleItinFragment extends Fragment {
    //IMPORTANT do some quality control. make sure you can handle if the itinerary handed to it
    //is null, or is missing stops, or other issues.
    //dont let the app crash if this is handed garbage
    private TextView body;
    //stuff for the recycler
    private RecyclerView itinStops;
    private ItinAdapter stopAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itin, container, false);
        body = root.findViewById(R.id.body);

        Bundle bundle=getArguments();
        Object input = bundle.getSerializable("itinerary");
        if (input == null) {
            body.setText("NO ITIN");
            return root;
        }

        ItineraryObject itin = (ItineraryObject) input;
        body.setText(itin.getItineraryName());

        ArrayList<Stop> stops = itin.getStops();


        return root;
    }
}
