package cs160.final_proj_drawer.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.ItineraryObject;
import cs160.final_proj_drawer.R;

public class ProfileFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ItinAdapter mItinAdapter;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        mTextViewEmpty = (TextView)view.findViewById(R.id.textViewEmpty);
//        mImageViewEmpty = (ImageView)view.findViewById(R.id.imageViewEmpty);
//        mProgressBarLoading = (ProgressBar)view.findViewById(R.id.progressBarLoading);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //go to json and pull whatever we get from firebase (basically the itineraries)
        //arraylist of itinerary objects
//        ArrayList data = new ArrayList<DataNote>();
//        for (int i = 0; i < DataNoteInformation.id.length; i++)
//        {
//            data.add(
//                    new DataNote
//                            (
//                                    DataNoteInformation.id[i],
//                                    DataNoteInformation.textArray[i],
//                                    DataNoteInformation.dateArray[i]
//                            ));
//        }

        ArrayList itineraries = new ArrayList<ItineraryObject>();
        //make json call, find the length and that is your for loop upper bound
        for (int i = 0; i < 4; i++)
        {
            ItineraryObject itinerary = new ItineraryObject("creatorName", "itineraryName", 0,
                    "coverPhoto", 1, null, new ArrayList<String>(), new ArrayList<String>());

            itineraries.add(itinerary);
        }

        mItinAdapter = new ItinAdapter(getContext(), itineraries, navController);
        mRecyclerView.setAdapter(mItinAdapter);

        return view;
    }
}