package cs160.final_proj_drawer.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.R;

//this is the user's profile
public class ProfileFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ItinAdapter mItinAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
/*
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList itineraries = new ArrayList<ItineraryObject>();
        //make json call, find the length and that is your for loop upper bound
        for (int i = 0; i < 4; i++)
        {
            ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName", 0,
                    "coverPhoto", 1, null, new ArrayList<String>(), new ArrayList<String>());

            itineraries.add(itinerary);
        }

        mItinAdapter = new ItinAdapter(getContext(), itineraries);
        mRecyclerView.setAdapter(mItinAdapter);
*/
        return view;
    }
}