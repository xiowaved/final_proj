package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.ItinAdapter;

/*
**  Displays a recyclerView with multiple Itineraries
**  NOTE: this uses the parent navController to navigate,
**  because no matter from where it was clicked, it pulls
**  open the full page itinerary that was clicked.
 */
public class DisplayMultItinsFragment extends Fragment {

    private Button filterButton;
    private NavController navController;
    public String urlRoot = "https://travelr-7feac.firebaseio.com/Locations";
    public String currentLocation = "Berkeley";
    public JSONObject Tags;


    //stuff for the recycler
    private RecyclerView searchItins;
    private ItinAdapter itinAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itins, container, false);

        //find stuff
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        filterButton = root.findViewById(R.id.filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_itin_to_filter);
                //Toast.makeText(root.getContext(),"show that itin",Toast.LENGTH_SHORT).show();
            }
        });

        //recycler views setup
        searchItins = (RecyclerView) root.findViewById(R.id.search_itins);
        final LinearLayoutManager itinLayoutManager = new LinearLayoutManager(getActivity());
        itinLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchItins.setLayoutManager(itinLayoutManager);

        //eventually wanna modularize this out of this func. just wanna call this
            ArrayList itineraries = new ArrayList<ItineraryObject>();

            for (int i = 0; i < 4; i++)
            {
                    ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName", 0,
                            "coverPhoto", 1, null, new ArrayList<String>(), new ArrayList<String>());
    //
                    itineraries.add(itinerary);
    //
            }
        // put them in
        itinAdapter = new ItinAdapter(getContext(), itineraries);
        searchItins.setAdapter(itinAdapter);

        return root;
    }
}
