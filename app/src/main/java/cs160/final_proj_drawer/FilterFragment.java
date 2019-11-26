package cs160.final_proj_drawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import cs160.final_proj_drawer.ui.home.HomeViewModel;


public class FilterFragment extends Fragment {
    private HomeViewModel homeViewModel;

    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_filter, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        return root;
    }



}
