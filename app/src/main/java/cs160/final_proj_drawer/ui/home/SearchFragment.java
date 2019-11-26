package cs160.final_proj_drawer.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import cs160.final_proj_drawer.R;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class SearchFragment extends Fragment {

    private ImageButton searchButton;
    private EditText tagSearchBar;
    private EditText locationSearchBar;

    private NavController navController;

    private String tagQuery;
    private String locationQuery;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

       // detect when user has finished typing a query in the TagSearchBar, and save the string
        tagSearchBar = (EditText) root.findViewById(R.id.tagSearchBar);
        tagSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // When focus is lost check that the text field has valid values.

                if (!hasFocus) {
                    Log.i("tag query", tagSearchBar.getText().toString());
                    tagQuery=tagSearchBar.getText().toString();
                }
            }
        });
        // detect when user has finished typing a query in the locationSearchBar, and save the string
        locationSearchBar = (EditText) root.findViewById(R.id.locationSearchBar);
        locationSearchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // When focus is lost check that the text field has valid values.

                if (!hasFocus) {
                    Log.i("location query", locationSearchBar.getText().toString());
                    locationQuery=locationSearchBar.getText().toString();
                }
            }
        });

        // connect magnifying glass button to navigate to results page
        searchButton = root.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_search_to_itin);
                //Toast.makeText(root.getContext(),"show that itin",Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }



}
