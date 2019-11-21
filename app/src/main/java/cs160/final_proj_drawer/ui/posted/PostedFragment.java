package cs160.final_proj_drawer.ui.posted;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cs160.final_proj_drawer.R;

public class PostedFragment extends Fragment {

    private PostedViewModel postedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        postedViewModel =
                ViewModelProviders.of(this).get(PostedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_posted, container, false);
        final TextView textView = root.findViewById(R.id.text_posted);
        postedViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}