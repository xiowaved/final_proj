package cs160.final_proj_drawer.ui.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.HorizAdapter;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;

public class SplashFragment extends Fragment {

    private NavController navController;
    public String urlRoot = "https://travelr-7feac.firebaseio.com/Locations";
    public String currentLocation = "Berkeley";
    public JSONObject Tags;

    //recyclerView stuffs
    private RecyclerView homeCats;
    private HorizAdapter catAdapter;
    private TypedArray categoryPics;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_splash, container, false);

        //find stuff
        navController = Navigation.findNavController(getActivity(), R.id.home_child_nav_host_fragment);

        //recycler views setup

        homeCats = (RecyclerView) root.findViewById(R.id.home_cats);
        final LinearLayoutManager catLayoutManager = new LinearLayoutManager(getActivity());
        catLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeCats.setLayoutManager(catLayoutManager);

        // get info for category data
        categoryPics = getResources().obtainTypedArray(R.array.category_pics);

        //eventually wanna modularize this out of this func. just wanna call this
            ArrayList cats = new ArrayList<String>();
            //make json call, find the length and that is your for loop upper bound
            String url = urlRoot + "/" + currentLocation + ".json";

        for (int i = 0; i < 4; i++)
            {
                int drawableID = categoryPics.getResourceId(i,0);
                cats.add("cat "+drawableID);

            }

            catAdapter = new HorizAdapter(getContext(), cats);
            homeCats.setAdapter(catAdapter);

        return root;
    }

}