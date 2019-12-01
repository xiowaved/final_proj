package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.util.Log;
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
public class DisplayMultItinsFragment extends Fragment implements ItinAdapter.onItinListener {

    public String urlRoot = "https://travelr-7feac.firebaseio.com/Locations";
    public JSONObject Tags;


    //stuff for the recycler
    private RecyclerView searchItins;
    private ItinAdapter itinAdapter;
    private ArrayList itineraries;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itins, container, false);

        //recycler view setup
        searchItins = (RecyclerView) root.findViewById(R.id.search_itins);
        final LinearLayoutManager itinLayoutManager = new LinearLayoutManager(getActivity());
        itinLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchItins.setLayoutManager(itinLayoutManager);

        //eventually wanna modularize this out of this func. just wanna call this
            itineraries = new ArrayList<ItineraryObject>();

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

    @Override
    public void onItinClick(int position) {
        itineraries.get(position);
        Log.i("Note", " was clicked! " + position);
    }
}
