package cs160.final_proj_drawer.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.Stop;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Page showing the filter options for a search query.
 *
 */
public class FilterFragment extends Fragment {

    private NavController navController;

    private TextView priceLastClicked = null;

    private TextView freePrice;
    private TextView onePrice;
    private TextView twoPrice;
    private TextView threePrice;

    private boolean freePriceBool;
    private boolean onePriceBool;
    private boolean twoPriceBool;
    private boolean threePriceBool;


    private RadioButton anyDistance;
    private RadioButton blocksDistance;
    private RadioButton mileDistance;
    private RadioButton milesDistance;

    private boolean anyDistanceBool;
    private boolean blocksDistanceBool;
    private boolean mileDistanceBool;
    private boolean milesDistanceBool;

    // Declare any UI elements that need to be interactive here, as private variables, like ImageButtons, TextViews, etc.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_filter, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        freePrice = (TextView) getView().findViewById(R.id.price1);
        onePrice = (TextView) getView().findViewById(R.id.price2);
        twoPrice = (TextView) getView().findViewById(R.id.price3);
        threePrice = (TextView) getView().findViewById(R.id.price4);

        freePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceLastClicked != null) {
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price));
                    priceLastClicked.setTextColor(getResources().getColorStateList(R.color.colorDarkGrey));
                }
                freePrice.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price_filled));
                freePrice.setTextColor(getResources().getColorStateList(R.color.colorOffWhite));
                priceLastClicked = freePrice;
                freePriceBool = true;
                onePriceBool = false;
                twoPriceBool = false;
                threePriceBool = false;
                Toast toast = Toast.makeText(getContext(), "clicked on free price",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        onePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceLastClicked != null) {
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price));
                    priceLastClicked.setTextColor(getResources().getColorStateList(R.color.colorDarkGrey));
                }
                onePrice.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price_filled));
                onePrice.setTextColor(getResources().getColorStateList(R.color.colorOffWhite));
                priceLastClicked = onePrice;
                freePriceBool = false;
                onePriceBool = true;
                twoPriceBool = false;
                threePriceBool = false;
                Toast toast = Toast.makeText(getContext(), "clicked on one price",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        twoPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceLastClicked != null) {
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price));
                    priceLastClicked.setTextColor(getResources().getColorStateList(R.color.colorDarkGrey));
                }
                twoPrice.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price_filled));
                twoPrice.setTextColor(getResources().getColorStateList(R.color.colorOffWhite));
                priceLastClicked = twoPrice;
                freePriceBool = false;
                onePriceBool = false;
                twoPriceBool = true;
                threePriceBool = false;
                Toast toast = Toast.makeText(getContext(), "clicked on two price",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        threePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceLastClicked != null) {
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price));
                    priceLastClicked.setTextColor(getResources().getColorStateList(R.color.colorDarkGrey));
                }
                threePrice.setBackground(getResources().getDrawable(R.drawable.rounded_corner_price_filled));
                threePrice.setTextColor(getResources().getColorStateList(R.color.colorOffWhite));
                priceLastClicked = threePrice;
                freePriceBool = false;
                onePriceBool = false;
                twoPriceBool = false;
                threePriceBool = true;
                Toast toast = Toast.makeText(getContext(), "clicked on three price",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        anyDistance = (RadioButton) getView().findViewById(R.id.radioButton1);
        blocksDistance = (RadioButton) getView().findViewById(R.id.radioButton2);
        mileDistance = (RadioButton) getView().findViewById(R.id.radioButton3);
        milesDistance = (RadioButton) getView().findViewById(R.id.radioButton4);

        anyDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyDistanceBool = true;
                blocksDistanceBool = false;
                mileDistanceBool = false;
                milesDistanceBool = false;
                Toast toast = Toast.makeText(getContext(), "clicked on any distance",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        blocksDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyDistanceBool = false;
                blocksDistanceBool = true;
                mileDistanceBool = false;
                milesDistanceBool = false;
                Toast toast = Toast.makeText(getContext(), "clicked on blocks distance",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        mileDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyDistanceBool = false;
                blocksDistanceBool = false;
                mileDistanceBool = true;
                milesDistanceBool = false;
                Toast toast = Toast.makeText(getContext(), "clicked on mile distance",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        milesDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyDistanceBool = false;
                blocksDistanceBool = false;
                mileDistanceBool = false;
                milesDistanceBool = true;
                Toast toast = Toast.makeText(getContext(), "clicked on miles distance",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

    }


}
