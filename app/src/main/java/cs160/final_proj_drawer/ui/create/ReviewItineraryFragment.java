package cs160.final_proj_drawer.ui.create;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.ui.home.HomeViewModel;

/**

 */
public class ReviewItineraryFragment extends Fragment {
    private CreateViewModel createViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_review_itinerary, container, false);
        return root;
    }
}
