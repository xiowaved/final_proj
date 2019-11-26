package cs160.final_proj_drawer.ui.create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cs160.final_proj_drawer.R;

/**
 *  this is the UI page to review
 *  an itinerary user created in CreateFragment
 */
public class ReviewItineraryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_review_itinerary, container, false);
        return root;
    }
}
