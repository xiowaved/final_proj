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
    private MutableLiveData<ArrayList<ItineraryObject>> loadedItins;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<ItineraryObject>> getSavedItineraries() {
        SearchQueryObject searchQueryObjectBookmark = new SearchQueryObject(new String[]{"bookmarked"}, "Berkeley");
        //if (loadedItins != null) {
            loadedItins = new MutableLiveData<>();
            getFirebaseData(searchQueryObjectBookmark);
        //}
        return loadedItins;
    }

    public LiveData<ArrayList<ItineraryObject>> getPostedItineraries() {

        SearchQueryObject searchQueryObjectCreated = new SearchQueryObject(new String[]{"created"}, "Berkeley");
        //if (loadedItins != null) {
            loadedItins = new MutableLiveData<>();
            getFirebaseData(searchQueryObjectCreated);
        //}
        return loadedItins;
    }

    @Override
    protected void onCleared() {
        firebaseFuncs.removeListener();
    }

    public LiveData<ArrayList<ItineraryObject>> getFirebaseData(SearchQueryObject searchQueryObject) {
        firebaseFuncs.addListener(new FirebaseFuncs.FirebaseFuncsCallback<ItineraryObject>() {
            @Override
            public void onSuccess(ArrayList<ItineraryObject> result) {
                loadedItins.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                loadedItins.setValue(null);
            }
        }, searchQueryObject);

        return loadedItins;
    }

    public MutableLiveData<ArrayList<ItineraryObject>> getLoadedItins() {
        return this.loadedItins;
    }


}
