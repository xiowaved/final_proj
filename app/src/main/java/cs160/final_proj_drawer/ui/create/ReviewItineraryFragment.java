package cs160.final_proj_drawer.ui.create;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;
import cs160.final_proj_drawer.adapters.StopAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

import static cs160.final_proj_drawer.logic.FirebaseFuncs.writeSingleItin;

/**
 *  this is the UI page to review
 *  an itinerary user created in CreateStopsFragment
 */
public class ReviewItineraryFragment extends Fragment implements OnRecyclerCardListener {
    private NavController navController;

    private RecyclerView stops;
    private StopAdapter stopAdapter;
    private TextView errorMsg;
    private ItineraryObject itineraryObject;
    private Button submit;
    private EditText itinName;
    private EditText itinLoc;
    private ImageView coverPhoto;
    private SeekBar priceSeekBar;
    private int pricePosition;
    private ArrayList<Stop> itinStops;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_review_itinerary, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        // get UI stuff
        errorMsg = (TextView) root.findViewById(R.id.errorMsg);

        //recycler view setup
        stops = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager stopsLayoutManager = new LinearLayoutManager(getActivity());
        stopsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stops.setLayoutManager(stopsLayoutManager);

        // get itinerary object from previous fragment
        Bundle bundle = getArguments();
        itineraryObject = (ItineraryObject) bundle.getSerializable("itinerary");

        if (itineraryObject.getStops().isEmpty()) {
            // something to catch potential null exceptions later
            errorMsg.setText("No stops found.");
            itinStops = new ArrayList<Stop>();
            stopAdapter = new StopAdapter(itinStops, this);
            stops.setAdapter(stopAdapter);
            // set the itinerary name and location editText's entries in UI
            itinName = (EditText) root.findViewById(R.id.name);
            itinName.setText(itineraryObject.getItineraryName());
            itinLoc = (EditText) root.findViewById(R.id.location);
            itinLoc.setText(itineraryObject.getLocation());
            String image = itineraryObject.getCoverPhoto();
            Picasso.get().load(image).into(coverPhoto);
            return root;
        } else {
            // get user's stops and populate cardviews in UI
            itinStops = (ArrayList<Stop>) itineraryObject.getStops();
            stopAdapter = new StopAdapter(itinStops, this);
            stops.setAdapter(stopAdapter);
            // set the itinerary name and location editText's entries in UI
            itinName = (EditText) root.findViewById(R.id.name);
            itinName.setText(itineraryObject.getItineraryName());
            itinLoc = (EditText) root.findViewById(R.id.location);
            itinLoc.setText(itineraryObject.getLocation());
            coverPhoto = (ImageView) root.findViewById(R.id.coverPhoto);
            /** uncomment the two lines below once itineraryobject has the photo*/
            String image = itineraryObject.getCoverPhoto();
            Picasso.get().load(image).into(coverPhoto);
            return root;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // set itinerary info in UI
        final EditText itinName = (EditText) getView().findViewById(R.id.name);
        final EditText itinLocation = (EditText) getView().findViewById(R.id.location);
        itinName.setText(itineraryObject.getItineraryName());

        //get price seekbar
        final SeekBar priceSeekBar = (SeekBar) getView().findViewById(R.id.seekBar);

        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("progress change", "here");

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("start tracking", "here");

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("stopped tracking", "" + seekBar.getProgress());
                // this is where you detect where the user has left the pin on the sliding scale
                pricePosition = seekBar.getProgress();

            }
        });

        // set listener for button when user submits itin
        submit = (Button) getView().findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!errorMsg.getText().toString().equals("")) {
                    // do NOT write to firebase if itinerary has no stops
                    //todo do we want to give some personalized error message? or just do nothing
                } else {
                    String price;
                    // get price tag
                    if (pricePosition == 0) {
                        // user tagged 'free'
                         itineraryObject.addTags("free");

                    } else if (pricePosition == 1) {
                        itineraryObject.addTags("cheap");
                    } else if (pricePosition == 2) {
                        itineraryObject.addTags("moderate");
                    } else if (pricePosition == 3) {
                        itineraryObject.addTags("expensive");

                    }

                    // write itinerary to firebase
                    itineraryObject.setItineraryName(itinName.getText().toString());
                    itineraryObject.setItineraryLocation(itinLocation.getText().toString());

                    Log.i("itin name", itineraryObject.getItineraryName());
                    Log.i("itin loc", itineraryObject.getLocation());

                    writeSingleItin(itineraryObject);


                    // navigate back to home splash screen
                }
            }
        });



    }


    @Override
    public void onCardClick(int position, cardAction action) {
        Stop selectedStop = itinStops.get(position);
        Toast toast = Toast.makeText(getContext(), "clicked on stop #" + "" + (position),
                Toast.LENGTH_SHORT);
        toast.show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("itinerary", itineraryObject);
        bundle.putInt("stopIndex", position);
        if (action == cardAction.EDIT) {
            navController.navigate(R.id.fragment_create_stops,bundle);
        } else if (action == cardAction.DELETE) {
            // remove this stop from the itinerary object itself
            itineraryObject.removeStop(position);
            // remove this card from recycler view
            stops.removeViewAt(position);
        }

    }
}


