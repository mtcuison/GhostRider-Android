package org.rmj.guanzongroup.ghostrider.epacss.ui.HomeContainer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VMHomeContainer extends ViewModel {

    private MutableLiveData<String> mText;

    public VMHomeContainer() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}