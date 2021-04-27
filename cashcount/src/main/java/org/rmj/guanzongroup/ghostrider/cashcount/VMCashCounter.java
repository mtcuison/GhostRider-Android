/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.cashcount
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.cashcount;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class VMCashCounter extends AndroidViewModel {

    private final MutableLiveData<Integer> Php = new MutableLiveData<>();
    private final MutableLiveData<String> Ttl = new MutableLiveData<>();

    public VMCashCounter(@NonNull Application application) {
        super(application);
        Php.setValue(1000);
    }

    public void setQuantity(int Quantity){
        int lnTotal = Php.getValue() * Quantity;
        Ttl.setValue(String.valueOf(lnTotal));
    }

    public LiveData<String> getTotal(){
        return Ttl;
    }
}