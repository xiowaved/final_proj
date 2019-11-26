package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.ui.home.HomeViewModel;
import cs160.final_proj_drawer.ui.saved.SavedViewModel;

public class ItinFragment extends Fragment {

    private ViewModel itinViewModel;

    private Button filterButton;
    private NavController navController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itinViewModel =
                ViewModelProviders.of(this).get(SavedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_itin, container, false);


        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        filterButton = root.findViewById(R.id.filter);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_itin_to_filter);
                //Toast.makeText(root.getContext(),"show that itin",Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }
}
