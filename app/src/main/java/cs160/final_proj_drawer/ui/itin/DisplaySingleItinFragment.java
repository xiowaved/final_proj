package cs160.final_proj_drawer.ui.itin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;
import cs160.final_proj_drawer.adapters.VertStopAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

/*  This UI element displays a single itinerary
 */
public class DisplaySingleItinFragment extends Fragment implements OnRecyclerCardListener {
    //IMPORTANT do some quality control. make sure you can handle if the itinerary handed to it
    //is null, or is missing stops, or other issues.
    //dont let the app crash if this is handed garbage

    //ui elements
    private TextView title;
    private TextView location;
    private TextView numLikes;
    private ImageView cover;
    private ImageView bkmk;
    private ImageView like;
    //stuff for the recycler
    private RecyclerView itinStops;
    private VertStopAdapter stopAdapter;
    //logic data
    private ItineraryObject itin;
    private ArrayList<Stop> stops;
    private String coverImage;
    private boolean liked;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_itin, container, false);

        //find stuff
        title = root.findViewById(R.id.Title);
        location = root.findViewById(R.id.location);
        numLikes = root.findViewById(R.id.numLikes);
        cover = root.findViewById(R.id.cover_img);
        bkmk = root.findViewById(R.id.bkmark);
        like = root.findViewById(R.id.like);

        //recycler view setup
        itinStops = (RecyclerView) root.findViewById(R.id.stops);
        final LinearLayoutManager stopLayoutManager = new LinearLayoutManager(getActivity());
        stopLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itinStops.setLayoutManager(stopLayoutManager);


        //get args, exit if there wasn't an itin object handed to it
        Bundle bundle=getArguments();
        Object input = bundle.getSerializable("itinerary");
        if (input == null) {
            title.setText("No Itinerary Found");
            return root;
        }

        //find itin logic stuff
        itin = (ItineraryObject) input;
        stops = itin.getStops();
        coverImage = itin.getCoverPhoto();
        //this is placeholder until we add something to itins
        liked = false;

        //set overarching itin up
        title.setText(itin.getItineraryName());
        numLikes.setText(Integer.toString(itin.getNumLikes()));
        location.setText(itin.getLocation());
        Picasso.get().load(coverImage).into(cover);
        DisplayItinHelperFuncs.updateBookmarkView(itin.getBookmarked(), bkmk);
        DisplayItinHelperFuncs.updateLikeView(liked, like, numLikes);

        bkmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itin.clickBookmark();
                //todo tell firebase that bookmark was clicked for this

                DisplayItinHelperFuncs.updateBookmarkView(itin.getBookmarked(), bkmk);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itin.clickLiked();
                //todo tell firebase and the itin that like was clicked for this itin
                //todo add or subtract a like from numLikes, depending on the action taken.
                // communicate that to firebase, and update numLikes on the screen
                DisplayItinHelperFuncs.updateLikeView(liked, like, numLikes);
            }
        });

        //check if the itin object has stops, exit if it doesn't
        if (stops == null || stops.size() == 0) {
            title.setText(itin.getItineraryName() + " has no stops");
            return root;
        }

        stopAdapter = new VertStopAdapter(stops, this);
        itinStops.setAdapter(stopAdapter);

        return root;
    }

    @Override
    public void onCardClick(int position, cardAction action) {
        Log.i("NOTE", "stop clicked in displaySingleItin "+position);
    }

}
