package cs160.final_proj_drawer.ui.profile;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs160.final_proj_drawer.adapters.HorizAdapter;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.HorizItinAdapter;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.SearchQueryObject;
import cs160.final_proj_drawer.ui.itin.DisplayMultItinsViewModel;

//this is the UI for the user's profile
public class ProfileFragment extends Fragment implements OnRecyclerCardListener {

    //stuff for architecture
    private NavController navController;
    private ProfileViewModel viewModel;
    private OnRecyclerCardListener listener;

    private SearchQueryObject searchQueryObject;
    private SearchQueryObject searchQueryObjectCreated;

    private RecyclerView savedItins;
    private RecyclerView postedItins;
    private HorizItinAdapter savedAdapter;
    private HorizItinAdapter postedAdapter;
    private TypedArray defaultPics;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        listener = this;
        savedItins = (RecyclerView) root.findViewById(R.id.profile_saved);
        final LinearLayoutManager savedLayoutManager = new LinearLayoutManager(getActivity());
        savedLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        savedItins.setLayoutManager(savedLayoutManager);

        postedItins = (RecyclerView) root.findViewById(R.id.profile_posted);
        final LinearLayoutManager postedLayoutManager = new LinearLayoutManager(getActivity());
        postedLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        postedItins.setLayoutManager(postedLayoutManager);

        // populate saved itins
        searchQueryObject = new SearchQueryObject(new String[]{"bookmarked"}, "Berkeley");
        viewModel.getItineraries(searchQueryObject).observe(this, new Observer<ArrayList<ItineraryObject>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ItineraryObject> s) {
                savedAdapter = new HorizItinAdapter(viewModel.getLoadedItins().getValue(), listener);
                savedItins.setAdapter(savedAdapter);
                postedAdapter = new HorizItinAdapter(viewModel.getLoadedItins().getValue(), listener);
                postedItins.setAdapter(postedAdapter);

            }
        });
        // populated crated itins
        searchQueryObjectCreated = new SearchQueryObject(new String[]{"created"}, "Berkeley");

        return root;
    }

    @Override
    public void onCardClick(int position, cardAction action) {
        Log.i("profile", "click");

    }
}