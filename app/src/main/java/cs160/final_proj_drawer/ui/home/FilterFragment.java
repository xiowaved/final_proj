package cs160.final_proj_drawer.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import cs160.final_proj_drawer.R;
import cs160.final_proj_drawer.logic.SearchQueryObject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Page showing the filter options for a search query.
 *
 */
public class FilterFragment extends Fragment {

    private NavController childNavController;

    private Button resetButton;
    private Button cancelButton;

    private TextView priceLastClicked = null;
    private RadioButton distanceLastClicked = null;

    private TextView freePrice;
    private TextView onePrice;
    private TextView twoPrice;
    private TextView threePrice;

    private boolean freePriceBool;
    private boolean onePriceBool;
    private boolean twoPriceBool;
    private boolean threePriceBool;

    private RadioGroup distanceGroup;

    private RadioButton anyDistance;
    private RadioButton blocksDistance;
    private RadioButton mileDistance;
    private RadioButton milesDistance;

    private boolean anyDistanceBool;
    private boolean blocksDistanceBool;
    private boolean mileDistanceBool;
    private boolean milesDistanceBool;

    private CheckBox adaTag;
    private CheckBox quietTag;
    private CheckBox lowAllergenTag;

    private boolean adaTagBool = false;
    private boolean quietTagBool = false;
    private boolean lowAllergenTagBool = false;

    private Button applyButton;

    private String initialTagQuery;
    private String locationQuery;
    private SearchQueryObject searchQueryObject;
    private View root;

    private SeekBar priceSeekBar;
    private int pricePosition;

    // Declare any UI elements that need to be interactive here, as private variables, like ImageButtons, TextViews, etc.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_filter, container, false);

        childNavController = Navigation.findNavController(getActivity(), R.id.home_child_nav_host_fragment);
        if (childNavController == null) {
            Log.i("IN FILTER", "didnt find kiddo");
        } else {
            Log.i("IN FILTER", "======== FOUND KIDDO =========");
        }

        Bundle b = getArguments();
//        initialTagQuery = b.getString("initialTagQuery");
//        locationQuery = b.getString("locationQuery");
        searchQueryObject = (SearchQueryObject) b.getSerializable("searchQueryObject");
        initialTagQuery = searchQueryObject.getTags()[0];
        locationQuery = searchQueryObject.getLocation();

        Log.i("filter frag", initialTagQuery + " " + locationQuery);

        return root;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        freePrice = (TextView) getView().findViewById(R.id.price_free);
        onePrice = (TextView) getView().findViewById(R.id.price_1);
        twoPrice = (TextView) getView().findViewById(R.id.price_2);
        threePrice = (TextView) getView().findViewById(R.id.price_3);
        // set listeners for price options, change ui to indicate they've been clicked
        freePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceLastClicked != null) {
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey_outline));
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
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey_outline));
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
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey_outline));
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
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey_outline));
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


//        priceSeekBar = (SeekBar) getView().findViewById(R.id.seekBar);
//        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.i("progress change", "here");
//
//            }
//
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                Log.i("start tracking", "here");
//
//            }
//
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                Log.i("stopped tracking", "" + seekBar.getProgress());
//                // this is where you detect where the user has left the pin on the sliding scale
//                pricePosition = seekBar.getProgress();
//
//            }
//        });

        distanceGroup = (RadioGroup) getView().findViewById(R.id.RGroup);
        anyDistance = (RadioButton) getView().findViewById(R.id.radioButton1);
        blocksDistance = (RadioButton) getView().findViewById(R.id.radioButton2);
        mileDistance = (RadioButton) getView().findViewById(R.id.radioButton3);
        milesDistance = (RadioButton) getView().findViewById(R.id.radioButton4);

        // set listeners for distance filter radio buttons
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

        adaTag = (CheckBox) getView().findViewById(R.id.checkBox1_filter);
        quietTag = (CheckBox) getView().findViewById(R.id.checkBox2_filter);
        lowAllergenTag = (CheckBox) getView().findViewById(R.id.checkBox3_filter);

        // set listeners for tag check boxes
        adaTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaTagBool = !adaTagBool;
                Toast toast = Toast.makeText(getContext(), "clicked on ADA tag",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        quietTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quietTagBool = !quietTagBool;
                Toast toast = Toast.makeText(getContext(), "clicked on quiet tag",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        lowAllergenTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowAllergenTagBool = !lowAllergenTagBool;
                Toast toast = Toast.makeText(getContext(), "clicked on low allergen tag",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        // set on click listeners for reset and cancel buttons
        resetButton = (Button) getView().findViewById(R.id.reset);
        cancelButton = (Button) getView().findViewById(R.id.cancel);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset price UI
                if (priceLastClicked != null) {
                    priceLastClicked.setBackground(getResources().getDrawable(R.drawable.rounded_corner_grey_outline));
                    priceLastClicked.setTextColor(getResources().getColorStateList(R.color.colorDarkGrey));
                }
                //reset distance UI
                distanceGroup.clearCheck();
                //reset price value
                freePriceBool = false;
                onePriceBool = false;
                twoPriceBool = false;
                threePriceBool = false;
                //reset distance value
                anyDistanceBool = false;
                blocksDistanceBool = false;
                mileDistanceBool = false;
                milesDistanceBool = false;
                //reset tags value
                adaTagBool = false;
                quietTagBool = false;
                lowAllergenTagBool = false;
                //reset tags UI
                if (adaTag.isChecked()) {
                    adaTag.toggle();
                }
                if (quietTag.isChecked()) {
                    quietTag.toggle();
                }
                if (lowAllergenTag.isChecked()) {
                    lowAllergenTag.toggle();
                }
                Toast toast = Toast.makeText(getContext(), "clicked on reset",
                        Toast.LENGTH_SHORT); toast.show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear all values
                //reset distance UI
                distanceGroup.clearCheck();
                //reset price value
                freePriceBool = false;
                onePriceBool = false;
                twoPriceBool = false;
                threePriceBool = false;
                //reset distance value
                anyDistanceBool = false;
                blocksDistanceBool = false;
                mileDistanceBool = false;
                milesDistanceBool = false;
                //go back to search results

                //pass the initially received search args here
                Bundle bundle = new Bundle();
                String[] tags = searchQueryObject.getTags();
                String location = searchQueryObject.getLocation();
                SearchQueryObject filterQueryObject = new SearchQueryObject(tags, location);
                bundle.putSerializable("searchQueryObject", filterQueryObject);
                childNavController.navigate(R.id.fragment_display_itins, bundle);
            }
        });

        applyButton = (Button) getView().findViewById(R.id.apply);
        // set listener for apply button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "clicked on apply",
                        Toast.LENGTH_SHORT); toast.show();
                //String priceSelection = priceLastClicked.getText().toString();
                //based on user selection of price, set tag for firebase query
                ArrayList<String> tags = new ArrayList<>();
                if (freePriceBool) {
                    tags.add("free");
                } else if (onePriceBool) {
                    tags.add("cheap");
                } else if (twoPriceBool) {
                    tags.add("moderate");
                } else if (threePriceBool) {
                    tags.add("expensive");
                } else { //user did not select a price
                    //no code added as a tag
                }


//                String price;
//                // get price tag
//                if (pricePosition == 0) {
//                    // user tagged 'free'
//                    tags.add("free");
//                } else if (pricePosition == 1) {
//                    tags.add("cheap");
//                } else if (pricePosition == 2) {
//                    tags.add("moderate");
//                } else if (pricePosition == 3) {
//                    tags.add("expensive");
//                }


                //todo remove distance functionality?
                //based on user selection of distance, set tag for firebase query
                //int clickedButton = distanceGroup.getCheckedRadioButtonId();
//                if (anyDistanceBool) {
//                    //nothing, we want itins of any distance
//                } else if (blocksDistanceBool) {
//                    tags.add("block");
//                } else if (mileDistanceBool) {
//                    tags.add("mile");
//                } else if (milesDistanceBool) {
//                    tags.add("miles");
//                }

                if (adaTagBool) {
                    tags.add("ADA compliant");
                }
                if (quietTagBool) {
                    tags.add("quiet");
                }
                if (lowAllergenTagBool) {
                    tags.add("low allergen");
                }
                tags.add(initialTagQuery);

                Bundle bundle = new Bundle();
                String[] tagsArr = new String[tags.size()];
                tagsArr = tags.toArray(tagsArr);
                String location = searchQueryObject.getLocation();
                SearchQueryObject filterQueryObject = new SearchQueryObject(tagsArr, location);
                bundle.putSerializable("searchQueryObject", filterQueryObject);
                childNavController.navigate(R.id.fragment_display_itins, bundle);

//                This call does all the work,  but I don't think it's building the itinerary cards?
//                getNestedItineraries(new ArrayList<ItineraryObject>(), "https://travelr-7feac.firebaseio.com/Locations/" + locationQuery + ".json" , getContext(), tags);

                    // conduct a new firebase search query with getnesteditineraries?
                // initial tag searched before clicking filter button = initialTagQuery (defined in OnCreateView)
                // initial location query = locationQuery (defined in OnCreateView)
                // combine these two ^ with however we are going to pass the distance and price to firebase
            }
        });
    }


}
