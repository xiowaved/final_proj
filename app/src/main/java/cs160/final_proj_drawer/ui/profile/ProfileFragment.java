package cs160.final_proj_drawer.ui.profile;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs160.final_proj_drawer.adapters.HorizAdapter;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;

//this is the user's profile
public class ProfileFragment extends Fragment {

    private RecyclerView savedItins;
    private RecyclerView postedItins;
    private HorizAdapter savedAdapter;
    private HorizAdapter postedAdapter;
    private TypedArray defaultPics;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        savedItins = (RecyclerView) root.findViewById(R.id.profile_saved);
        final LinearLayoutManager savedLayoutManager = new LinearLayoutManager(getActivity());
        savedLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        savedItins.setLayoutManager(savedLayoutManager);

        postedItins = (RecyclerView) root.findViewById(R.id.profile_posted);
        final LinearLayoutManager postedLayoutManager = new LinearLayoutManager(getActivity());
        postedLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        postedItins.setLayoutManager(postedLayoutManager);

        //this stuff is all placeholder, needs to be replaced by firebase stuff
        defaultPics = getResources().obtainTypedArray(R.array.category_pics);
        ArrayList defaultItins = new ArrayList<String>();
        for (int i = 0; i < 4; i++)
        {
            int drawableID = defaultPics.getResourceId(i,0);
            defaultItins.add("default "+drawableID);
        }

        //attach stuff to the recyclerView
        savedAdapter = new HorizAdapter(getContext(), defaultItins);
        savedItins.setAdapter(savedAdapter);
        postedAdapter = new HorizAdapter(getContext(), defaultItins);
        postedItins.setAdapter(postedAdapter);
        return root;
    }
}