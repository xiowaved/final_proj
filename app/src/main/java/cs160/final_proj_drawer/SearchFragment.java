package cs160.final_proj_drawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import cs160.final_proj_drawer.ui.home.HomeViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class SearchFragment extends Fragment {
    private HomeViewModel homeViewModel;

    private Button search;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        search = root.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_search_to_itin);
                //Toast.makeText(root.getContext(),"show that itin",Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }

}
