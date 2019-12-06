package cs160.final_proj_drawer.ui.profile;

import android.app.Application;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cs160.final_proj_drawer.logic.FirebaseFuncs;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.SearchQueryObject;

/*
    This class displays multiple itineraries
    It takes in a SearchQuery and Executes that search in our firebase database.
 */
public class ProfileViewModel extends AndroidViewModel {

    private FirebaseFuncs firebaseFuncs = new FirebaseFuncs();
    private MutableLiveData<ArrayList<ItineraryObject>> postedItins;
    private MutableLiveData<ArrayList<ItineraryObject>> savedItins;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<ItineraryObject>> getSavedItineraries() {
        SearchQueryObject searchQueryObjectBookmark = new SearchQueryObject(new String[]{"bookmarked"}, "Berkeley");
        if (savedItins != null) {
            savedItins = new MutableLiveData<>();
            getSavedFirebaseData(searchQueryObjectBookmark);
        }
        return savedItins;
    }

    public LiveData<ArrayList<ItineraryObject>> getPostedItineraries() {

        SearchQueryObject searchQueryObjectCreated = new SearchQueryObject(new String[]{"created"}, "Berkeley");
        if (postedItins != null) {
            postedItins = new MutableLiveData<>();
            getPostedFirebaseData(searchQueryObjectCreated);
        }
        return postedItins;
    }

    @Override
    protected void onCleared() {
        firebaseFuncs.removeListener();
    }

    public LiveData<ArrayList<ItineraryObject>> getSavedFirebaseData(SearchQueryObject searchQueryObject) {
        firebaseFuncs.addListener(new FirebaseFuncs.FirebaseFuncsCallback<ItineraryObject>() {
            @Override
            public void onSuccess(ArrayList<ItineraryObject> result) {
                savedItins.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                savedItins.setValue(null);
            }
        }, searchQueryObject);

        return savedItins;
    }

    public LiveData<ArrayList<ItineraryObject>> getPostedFirebaseData(SearchQueryObject searchQueryObject) {
        firebaseFuncs.addListener(new FirebaseFuncs.FirebaseFuncsCallback<ItineraryObject>() {
            @Override
            public void onSuccess(ArrayList<ItineraryObject> result) {
                postedItins.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                postedItins.setValue(null);
            }
        }, searchQueryObject);

        return postedItins;
    }

    public MutableLiveData<ArrayList<ItineraryObject>> getPostedItins() {
        return postedItins;
    }
    public MutableLiveData<ArrayList<ItineraryObject>> getSavedItins() {
        return savedItins;
    }

}
