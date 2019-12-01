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

import cs160.final_proj_drawer.R;


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
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        final TextView errorMsg = (TextView) getView().findViewById(R.id.errorMsg);
        addStopsButton = (Button) getView().findViewById(R.id.button);
        addStopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itineraryName = itinName.getText().toString();
                itineraryLocation = itinLocation.getText().toString();
                if (itineraryName.isEmpty() || itineraryLocation.isEmpty()) {
                    // user did not fill in required fields
                    errorMsg.setText("Fill in all fields to continue.");
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", itineraryName);
                    bundle.putString("location", itineraryLocation);
                    navController.navigate(R.id.fragment_create_stops,bundle);
                    Toast toast = Toast.makeText(getContext(), "clicked on add new stops",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
