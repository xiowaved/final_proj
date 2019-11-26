package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cs160.final_proj_drawer.R;

public class DisplaySingleItinFragment extends Fragment {
    private NavController navController;

    // Declare any UI elements that need to be interactive here, as private variables, like ImageButtons, TextViews, etc.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_single_itin, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        return root;
    }
}
