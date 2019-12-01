package cs160.final_proj_drawer.ui.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.adapters.HorizAdapter;

/* this displays categories, holds some
 cool text about the location, and has a displayMultItinsFragment
 at the bottom with most popular stuff for that location
 */
public class SplashFragment extends Fragment {
    public String currentLocation = "Berkeley";

    //category stuff
    private RecyclerView homeCats;
    private HorizAdapter catAdapter;
    private TypedArray categoryPics;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_splash, container, false);

        //category recycler view setup
        homeCats = (RecyclerView) root.findViewById(R.id.home_cats);
        final LinearLayoutManager catLayoutManager = new LinearLayoutManager(getActivity());
        catLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeCats.setLayoutManager(catLayoutManager);

        // get info for category data
        categoryPics = getResources().obtainTypedArray(R.array.category_pics);

        //this is just some hardcoded categories for now
        ArrayList cats = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            int drawableID = categoryPics.getResourceId(i,0);
            cats.add("cat "+drawableID);
        }
        catAdapter = new HorizAdapter(getContext(), cats);
        homeCats.setAdapter(catAdapter);

        return root;
    }

}