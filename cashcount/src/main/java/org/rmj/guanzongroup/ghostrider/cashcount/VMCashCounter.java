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