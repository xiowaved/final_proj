package cs160.final_proj_drawer.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is create itinerary fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}