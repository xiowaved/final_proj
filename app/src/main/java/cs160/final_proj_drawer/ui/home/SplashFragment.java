package cs160.final_proj_drawer.ui.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.HorizAdapter;
import cs160.final_proj_drawer.adapters.OnRecyclerCardListener;

/* this UI element displays categories, holds some
 cool text about the location, and has a displayMultItinsFragment
 at the bottom with most popular stuff for that location
 */
public class SplashFragment extends Fragment implements OnRecyclerCardListener {
    public String currentLocation = "Berkeley";

    //category stuff
    private RecyclerView homeCats;
    private HorizAdapter catAdapter;
    private TypedArray categoryPics;
    private OnRecyclerCardListener listener;
    ArrayList cats;
    ArrayList<String> categories;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_splash, container, false);
        listener = this;
        //category recycler view setup
        homeCats = (RecyclerView) root.findViewById(R.id.home_cats);
        final LinearLayoutManager catLayoutManager = new LinearLayoutManager(getActivity());
        catLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeCats.setLayoutManager(catLayoutManager);

        // get info for category data
        categoryPics = getResources().obtainTypedArray(R.array.category_pics);

        //this is just some hardcoded categories for now
        cats = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            int drawableID = categoryPics.getResourceId(i,0);
            cats.add("cat "+drawableID);
        }
        catAdapter = new HorizAdapter(cats, this);
        homeCats.setAdapter(catAdapter);

        categories = new ArrayList<String>();
        categories.add("food");
        categories.add("music");
        categories.add("hike");
        categories.add("art");


        return root;
    }

    @Override
    public void onCardClick(int position, cardAction action) {
        Log.i("splash", "click");
        //todo clean - string is being saved with category drawable id so it needs to be parsed here
        String category = ((String) categories.get(position)).split(" ")[0];
        Log.i("card click splash", category);
        // colby todo execute a firebase search based on the category title

    }

}