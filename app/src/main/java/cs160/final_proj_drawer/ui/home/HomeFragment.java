package cs160.final_proj_drawer.ui.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs160.final_proj_drawer.adapters.CatAdapter;
import cs160.final_proj_drawer.adapters.ItinAdapter;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.MySingleton;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

/* this fragment holds the home page and the search function.
*  it's the first page displayed to the user.
*  top is search bar.
*  bottom is home_child_nav_host_fragment.
*  it will navigate between
*  - splash page
*  - display itins
*  - filters
*  based on different actions taken involving search bar above.
*  */
public class HomeFragment extends Fragment {

    private ImageButton searchButton;
    private Button filterButton;
    // top search bar -- query itinerary tags
    private EditText tagSearchBar;
    // lower search bar, appears after clicking inside top one (see Yelp as example);
    private EditText locationSearchBar;
    private NavController childNavController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         final View root = inflater.inflate(R.layout.fragment_home, container, false);
        //childNavController = Navigation.findNavController(getActivity(), R.id.home_child_nav_host_fragment);

        //find stuff
        tagSearchBar = root.findViewById(R.id.tagSearchBar);
        locationSearchBar = root.findViewById(R.id.locationSearchBar);
        searchButton = root.findViewById(R.id.searchButton);
        filterButton = root.findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //childNavController.navigate(R.id.fragment_display_itins);
                Toast.makeText(root.getContext(),"show that itin",Toast.LENGTH_SHORT).show();
            }
        });



        tagSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(root.getContext(),"in the search",Toast.LENGTH_SHORT).show();
                }
                locationSearchBar.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }


}