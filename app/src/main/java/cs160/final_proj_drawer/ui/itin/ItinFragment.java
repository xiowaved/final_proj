package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.ui.saved.SavedViewModel;

public class ItinFragment extends Fragment {

    private ViewModel itinViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itinViewModel =
                ViewModelProviders.of(this).get(SavedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_itin, container, false);


        return root;
    }
}
