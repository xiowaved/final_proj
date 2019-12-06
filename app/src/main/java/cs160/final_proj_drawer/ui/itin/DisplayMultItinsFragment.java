package cs160.final_proj_drawer.ui.itin;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.SearchQueryObject;

/*
**  Displays a recyclerView with multiple Itineraries
**  NOTE: this uses the parent navController to navigate,
**  because no matter from where it was clicked, it pulls
**  open the full page itinerary that was clicked.
 */
public class DisplayMultItinsFragment extends Fragment implements OnRecyclerCardListener{

    //stuff for architecture
    private NavController navController;
    private DisplayMultItinsViewModel viewModel;
    private OnRecyclerCardListener listener;

    //stuff for the recycler
    private RecyclerView searchItins;
    private ItinAdapter itinAdapter;
    //private ArrayList<ItineraryObject> itineraries; //i'm trying to move this to the viewModel
    Drawable emptyBkmk;
    Drawable filledBkmk;

    private SearchQueryObject searchQueryObject;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itins, container, false);

        //find architecture stuff
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        viewModel = ViewModelProviders.of(this).get(DisplayMultItinsViewModel.class);
        listener = this;

        //get searchQueryObject from splashFragment
        Bundle bundle = getArguments();
        if (bundle == null) {
            String[] defaultTags = {"default"};
            searchQueryObject = new SearchQueryObject();
            searchQueryObject.setLocation("Berkeley");
            searchQueryObject.setTags(defaultTags);
        } else {
            searchQueryObject = (SearchQueryObject) bundle.get("searchQueryObject");
        }
        Log.i("LOCATION", searchQueryObject.getLocation());
        String[] tags = searchQueryObject.getTags();
        for (int i = 0; i < tags.length; i++) {
            Log.i("TAGS", tags[i]);
        }

        //recycler view setup
        searchItins = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager itinLayoutManager = new LinearLayoutManager(getActivity());
        itinLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchItins.setLayoutManager(itinLayoutManager);

        // set the bookmark drawables so that you can switch between them when user clicks on one
        emptyBkmk = getResources().getDrawable(R.drawable.ic_bkmark);
        filledBkmk =  getResources().getDrawable(R.drawable.ic_bookmark_filled);

        //puttin more itins in here from firebase
        viewModel.getItineraries(searchQueryObject).observe(this, new Observer<ArrayList<ItineraryObject>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ItineraryObject> s) {
                itinAdapter = new ItinAdapter(viewModel.getLoadedItins().getValue(), listener);
                searchItins.setAdapter(itinAdapter);
            }
        });


        return root;
    }

    //this is when the image is clicked, and we navigate to a specific itinerary
    @Override
    public void onCardClick(int position, cardAction action) {
        ItineraryObject selectedItin = viewModel.getLoadedItins().getValue().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", selectedItin);

            navController.navigate(R.id.fragment_display_single_itin, bundle);
    }

}

