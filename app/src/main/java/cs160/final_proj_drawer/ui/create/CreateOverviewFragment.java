package cs160.final_proj_drawer.ui.create;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;


public class CreateOverviewFragment extends Fragment {

    private NavController navController;
    private Button addStopsButton;
    private String itineraryName;
    private String itineraryLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_overview, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // get the EditTexts so you can query user's itinerary info inputs
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        final TextView errorMsg = (TextView) getView().findViewById(R.id.errorMsg);
        // set onClick listener for the add stops button
        addStopsButton = (Button) getView().findViewById(R.id.button);
        addStopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryName = itinName.getText().toString();
                itineraryLocation = itinLocation.getText().toString();
                // if user left name or location fields blank, do not let them navigate to next page yet
                if (itineraryName.isEmpty() || itineraryLocation.isEmpty()) {
                    // user did not fill in required fields
                    errorMsg.setText("Fill in all fields to continue.");
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    // pass a partially filled-out itinerary object to the createStops fragment
                    ItineraryObject itinerary = new ItineraryObject("creatorName", itineraryName,
                            0, "", itineraryLocation, 0, new ArrayList<Stop>(),
                            new ArrayList<String>(), new ArrayList<String>());
                    bundle.putSerializable("itinerary", itinerary);

//                    bundle.putString("name", itineraryName);
//                    bundle.putString("location", itineraryLocation);
                    navController.navigate(R.id.fragment_create_stops,bundle);
                }
            }
        });
    }

}
