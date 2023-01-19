package org.rmj.guanzongroup.ghostrider.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.ghostrider.Model.PanaloReward;

import java.util.ArrayList;
import java.util.List;

public class VMPanaloRewards extends AndroidViewModel {

    private final List<PanaloReward> poList = new ArrayList<>();

    public VMPanaloRewards(@NonNull Application application) {
        super(application);
        PanaloReward loReward = new PanaloReward();
        loReward.setsRewardNm("T-Shirt");
        loReward.setsRewardCD("MX0123456789");
        loReward.setsRewardDt("2023-01-19");

        poList.add(loReward);

        loReward = new PanaloReward();
        loReward.setsRewardNm("Php. 50.00 Rebate");
        loReward.setsRewardCD("MX0123456780");
        loReward.setsRewardDt("2023-01-19");

        poList.add(loReward);
    }

    public List<PanaloReward> getList(){
        return poList;
    }
}
