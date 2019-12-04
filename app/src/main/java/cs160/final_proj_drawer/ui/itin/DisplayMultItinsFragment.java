package cs160.final_proj_drawer.ui.itin;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener.cardAction;
import cs160.final_proj_drawer.logic.FirebaseFuncs;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.SearchQueryObject;
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
            searchQueryObject = new SearchQueryObject();
        } else {
            searchQueryObject = (SearchQueryObject) bundle.get("searchQueryObject");
        }

        //recycler view setup
        searchItins = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager itinLayoutManager = new LinearLayoutManager(getActivity());
        itinLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchItins.setLayoutManager(itinLayoutManager);

        // set the bookmark drawables so that you can switch between them when user clicks on one
        emptyBkmk = getResources().getDrawable(R.drawable.ic_bkmark);
        filledBkmk =  getResources().getDrawable(R.drawable.ic_bookmark_filled);

        //todo incorporate the searchQuery object into a firebase search
        String location = searchQueryObject.getLocation();
        if (searchQueryObject.getTags().length > 1) {
            // more than one query tag to search

        } else {
            // only one query tag to search

        }

        //puttin more itins in here from firebase
        viewModel.getItineraries().observe(this, new Observer<ArrayList<ItineraryObject>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ItineraryObject> s) {
                itinAdapter = new ItinAdapter(viewModel.getLoadedItins().getValue(), listener);
                searchItins.setAdapter(itinAdapter);
            }
        });


        return root;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        ArticleViewModel viewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
//        viewModel.getArticles().observe(this, new Observer<List<Article>>() {
//            @Override
//            public void onChanged(@Nullable List<Article> articles) {
//                recyclerView.setAdapter(new ArticleAdapter(articles));
//            }
//        });
//    }

    @Override
    public void onCardClick(int position, cardAction action) {
        ItineraryObject selectedItin = viewModel.getLoadedItins().getValue().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", selectedItin);

        Log.i("Note", " was clicked! " + position);
/*
        ImageView bookmark = getView().findViewById(R.id.bkmark);
        if (action == cardAction.BOOKMARK) {
            if (bookmark.getDrawable().getConstantState() == emptyBkmk.getConstantState()) {
                Log.i("empty", "here");
                bookmark.setImageResource(R.drawable.ic_bookmark_filled);
                // todo selectedItin should now be saved to the user's profile
            } else if (bookmark.getDrawable().getConstantState() == filledBkmk.getConstantState()){
                Log.i("filled", "here");
                bookmark.setImageResource(R.drawable.ic_bkmark);
                // todo selectedItin should now be removed from the user's profile

            }
        } else {*/
            navController.navigate(R.id.fragment_display_single_itin, bundle);
        //}
    }

    public void fillPlaceHolderItins() {
        for (int i = 0; i < 2; i++)
        {
            //placeholder stops and itins
            // so we can practice displaying single itins from here.

            ArrayList<String> photolist = new ArrayList<>();
            ArrayList<Stop> stoplist = new ArrayList<>();
            ArrayList<String> taglist = new ArrayList<>();
            ArrayList<String> acclist = new ArrayList<>();
            boolean isBookmarked = false;
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
                    "coverPhoto", "berk", 1, stoplist, taglist, acclist, isBookmarked);
            viewModel.getLoadedItins().getValue().add(itinerary);

        }
    }

}

