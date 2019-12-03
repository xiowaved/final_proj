package cs160.final_proj_drawer.ui.itin;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.AndroidViewModel;
import cs160.final_proj_drawer.logic.FirebaseFuncs;
import cs160.final_proj_drawer.logic.ItineraryObject;
import cs160.final_proj_drawer.logic.Stop;

public class DisplayMultItinsViewModel extends AndroidViewModel {

    //holds the itins that are drawn to the view
    ArrayList<ItineraryObject> itins = new ArrayList<>();

    private FirebaseFuncs firebaseFuncs = new FirebaseFuncs();

    //all code below this comment is some boiler plate stuff i pulled
    // from the autogenerated viewModels this project came with
    private MutableLiveData<ArrayList<ItineraryObject>> loadedItins;

    public DisplayMultItinsViewModel(@NonNull Application application) {
        super(application);

        //loadedItins = new MutableLiveData<ArrayList<ItineraryObject>>();
        String url = FirebaseFuncs.url+"Berkeley.json";
        FirebaseFuncs.getItineraries(itins, url, getApplication().getApplicationContext());
        //loadedItins.setValue(itins);
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
/*
*/
}
