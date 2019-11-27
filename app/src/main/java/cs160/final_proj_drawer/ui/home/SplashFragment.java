package cs160.final_proj_drawer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import cs160.final_proj_drawer.R;

public class SplashFragment extends Fragment {

    private NavController navController;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_splash, container, false);

        //find stuff
        navController = Navigation.findNavController(getActivity(), R.id.home_child_nav_host_fragment);

        return root;
    }

}