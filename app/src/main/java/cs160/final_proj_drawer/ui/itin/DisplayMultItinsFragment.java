package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;
import cs160.final_proj_drawer.logic.FirebaseFuncs;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.Stop;

/*
**  Displays a recyclerView with multiple Itineraries
**  NOTE: this uses the parent navController to navigate,
**  because no matter from where it was clicked, it pulls
**  open the full page itinerary that was clicked.
 */
public class DisplayMultItinsFragment extends Fragment implements OnRecyclerCardListener{

    public String urlRoot = "https://travelr-7feac.firebaseio.com/Locations";
    public JSONObject Tags;

    //stuff for nav
    private NavController navController;

    //stuff for the recycler
    private RecyclerView searchItins;
    private ItinAdapter itinAdapter;
    private ArrayList<ItineraryObject> itineraries;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itins, container, false);

        //find navController
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        //recycler view setup
        searchItins = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager itinLayoutManager = new LinearLayoutManager(getActivity());
        itinLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchItins.setLayoutManager(itinLayoutManager);

        //this array list is where the itins are put after firebase call
        itineraries = new ArrayList<>();

        for (int i = 0; i < 2; i++)
        {
            //placeholder stops and itins
            // so we can practice displaying single itins from here.

            ArrayList<String> photolist = new ArrayList<>();
            ArrayList<Stop> stoplist = new ArrayList<>();
            ArrayList<String> taglist = new ArrayList<>();
            ArrayList<String> acclist = new ArrayList<>();
            Stop Safeway = new Stop(photolist, "Safeway", "6310 College Ave, Oakland, CA 94618", "I stopped here to pickup some meat. " +
                    "They have pretty good deals here and I walked away with some pork loin that was on sale. They're also open 24 hours!", 0);
            Stop BerkeleyBowl = new Stop(photolist, "Berkeley Bowl", "2020 Oregon St, Berkeley, CA 94703", "Very diverse set of produce. " +
                    "Large selection and good prices on fruits/veggies not so great prices on everything else.", 1);
            Stop WholeFoods = new Stop(photolist, "Whole Foods", "3000 Telegraph Ave, Berkeley, CA 94705", "Wide selection of organic food" +
                    "a little pricey, but they have a good selection of exotic food/drinks, such as kombucha", 2);
            stoplist.add(Safeway);
            stoplist.add(BerkeleyBowl);
            stoplist.add(WholeFoods);
            taglist.add("shopping");
            taglist.add("grocery");
            taglist.add("food");
            acclist.add("elevator");


            ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName " +i, 11*i,
                    "coverPhoto", "berk", 1, stoplist, taglist, acclist);
                itineraries.add(itinerary);

        }

        // todo put more itins in here form firebase
        /*
        CountDownLatch done = new CountDownLatch(5);
        FirebaseFuncs.getItineraries(itineraries, FirebaseFuncs.url, getContext());
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            Log.i("ERROR", "got interuptedException");
        }
*/
        itinAdapter = new ItinAdapter(itineraries, this);
        searchItins.setAdapter(itinAdapter);
        return root;
    }

    @Override
    public void onCardClick(int position, boolean editMode) {
        ItineraryObject selectedItin = itineraries.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", selectedItin);

        Log.i("Note", " was clicked! " + position);

        navController.navigate(R.id.fragment_display_single_itin, bundle);
    }

}
