package cs160.final_proj_drawer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class SearchFragment extends Fragment {

    private ImageButton searchButton;

    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        searchButton = root.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_search_to_itin);
                //Toast.makeText(root.getContext(),"show that itin",Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }

}
