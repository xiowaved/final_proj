package cs160.final_proj_drawer.ui.posted;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PostedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is posted fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}