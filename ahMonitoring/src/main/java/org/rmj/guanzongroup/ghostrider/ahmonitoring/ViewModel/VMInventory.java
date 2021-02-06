package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RandomItem;

import java.util.ArrayList;
import java.util.List;


public class VMInventory extends AndroidViewModel {

    private final MutableLiveData<List<RandomItem>> psRandom = new MutableLiveData<>();

    public VMInventory(@NonNull Application application) {
        super(application);

        List<RandomItem> randomItems = new ArrayList<>();
        randomItems.add(new RandomItem("1000438", "N0842736853", "Tmx 155 Cam Follower"));
        randomItems.add(new RandomItem("1005049", "N0740345581", "Tmx 125 Alpha Side Cover"));
        randomItems.add(new RandomItem("1007564", "N0722848986", "Honda Beat Headlight Fairing"));
        randomItems.add(new RandomItem("1007572", "N0722848989", "Honda Beat Grab Bar"));
        randomItems.add(new RandomItem("1007580", "N0722848998", "Honda Click Exhaust"));
        psRandom.setValue(randomItems);
    }

    public LiveData<List<RandomItem>> getRandomItemList() {
        return psRandom;
    }
}