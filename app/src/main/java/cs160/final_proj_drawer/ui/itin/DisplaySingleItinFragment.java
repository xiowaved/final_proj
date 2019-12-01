package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cs160.final_proj_drawer.R;

/*  This UI element displays a single itinerary
 */
public class DisplaySingleItinFragment extends Fragment {
    //IMPORTANT do some quality control. make sure you can handle if the itinerary handed to it
    //is null, or is missing stops, or other issues.
    //dont let the app crash if this is handed garbage
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itin, container, false);
        return root;
    }
}
