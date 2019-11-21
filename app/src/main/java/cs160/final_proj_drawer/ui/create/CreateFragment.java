package cs160.final_proj_drawer.ui.create;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cs160.final_proj_drawer.R;

public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;

    EditText name;
    EditText location;
    EditText description;
    Button viewPreviousStop;
    Button addNewStop;
    Button reviewItinerary;
    //list of itineraries
    int currentStopIndex;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        createViewModel =
//                ViewModelProviders.of(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create, container, false);
//        final TextView textView = root.findViewById(R.id.text_create);
//        createViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        currentStopIndex = 0;

        name = (EditText) getView().findViewById(R.id.name);
        location = (EditText) getView().findViewById(R.id.location);
        description = (EditText) getView().findViewById(R.id.description);

        viewPreviousStop = (Button) getView().findViewById(R.id.viewPreviousStop);
        addNewStop = (Button) getView().findViewById(R.id.addNewStop);
        reviewItinerary = (Button) getView().findViewById(R.id.reviewItinerary);

        addNewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddNewStop(v);
            }
        });

        reviewItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReviewItinerary(v);
            }
        });
    }

    public void onReviewItinerary(View view) {
        String savedName = name.getText().toString();
        String savedLocation = location.getText().toString();
        String savedDescription = description.getText().toString();
        Log.i("savedName", savedName);
        Log.i("savedLocation", savedLocation);
        Log.i("savedDescription", savedDescription);
        //make itinerary object
    }

    public void onAddNewStop(View view) {
        currentStopIndex++;
        String savedName = name.getText().toString();
        String savedLocation = location.getText().toString();
        String savedDescription = description.getText().toString();
        Log.i("savedName", savedName);
        Log.i("savedLocation", savedLocation);
        Log.i("savedDescription", savedDescription);
        Log.i("currentStopIndex", "" + currentStopIndex);
        //make itinerary object
    }

    public void onViewPreviousStop(View view) {
        currentStopIndex--;
        Log.i("currentStopIndex", "" + currentStopIndex);
    }
}