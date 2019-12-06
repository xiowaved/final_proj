package cs160.final_proj_drawer.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cs160.final_proj_drawer.R;

public class SettingsFragment extends Fragment {

    private Switch contrastSwitch;
    private Switch fontSwitch;
    private Button green;
    private Button blue;
    private Button purple;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        //find everything
        contrastSwitch = root.findViewById(R.id.contrast_switch);
        fontSwitch = root.findViewById(R.id.font_switch);
        green = root.findViewById(R.id.green_colors);
        blue = root.findViewById(R.id.blue_colors);
        purple = root.findViewById(R.id.purple_colors);

        //listen to the button clicks for the color pallets
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Log.i("NOTE", "green");
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Log.i("NOTE", "blue");
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root) {
                Log.i("NOTE", "purple");
            }
        });

        return root;
    }
}