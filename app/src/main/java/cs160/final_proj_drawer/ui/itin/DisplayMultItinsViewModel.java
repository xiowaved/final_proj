package cs160.final_proj_drawer.ui.itin;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.AndroidViewModel;
import cs160.final_proj_drawer.logic.FirebaseFuncs;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.SearchQueryObject;
import cs160.final_proj_drawer.logic.Stop;

import static cs160.final_proj_drawer.logic.FirebaseFuncs.getNestedItineraries;

public class DisplayMultItinsViewModel extends AndroidViewModel {

    private FirebaseFuncs firebaseFuncs = new FirebaseFuncs();
    private MutableLiveData<ArrayList<ItineraryObject>> loadedItins;
    //SearchQueryObject searchQueryObject;

    public DisplayMultItinsViewModel(@NonNull Application application) {
        super(application);
        //String[] tags = searchQueryObject.getTags();
        //String locationQuery = searchQueryObject.getLocation();
        //ArrayList<String> tagsList = (ArrayList<String>) Arrays.asList(tags);
        //getNestedItineraries(new ArrayList<ItineraryObject>(), "https://travelr-7feac.firebaseio.com/Locations/"
         //       + locationQuery + ".json" , getApplication().getApplicationContext(), tagsList);
/*
        String[] defaultTags = {"Food"};
        searchQueryObject = new SearchQueryObject();
        searchQueryObject.setLocation("Berkeley");
        searchQueryObject.setTags(defaultTags);

        Log.i("NOTE", searchQueryObject.getLocation());
        Log.i("NOTE", searchQueryObject.getTags()[0]);
*/
       // FirebaseFuncs.getNestedItineraries(itins, getApplication().getApplicationContext(),searchQueryObject);
    }

    public LiveData<ArrayList<ItineraryObject>> getItineraries() {
        if (loadedItins == null) {
            loadedItins = new MutableLiveData<>();
            getFirebaseData();
        }
        return loadedItins;
    }

    @Override
    protected void onCleared() {
        firebaseFuncs.removeListener();
    }

    public LiveData<ArrayList<ItineraryObject>> getFirebaseData() {
        firebaseFuncs.addListener(new FirebaseFuncs.FirebaseFuncsCallback<ItineraryObject>() {
            @Override
            public void onSuccess(ArrayList<ItineraryObject> result) {

                loadedItins.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                loadedItins.setValue(null);
            }
        });

        return loadedItins;
    }

    public MutableLiveData<ArrayList<ItineraryObject>> getLoadedItins() {
        return this.loadedItins;
    }


}
