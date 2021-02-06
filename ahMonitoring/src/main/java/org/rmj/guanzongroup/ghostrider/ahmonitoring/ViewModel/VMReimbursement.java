package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.ReimburseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMReimbursement extends AndroidViewModel {
    private static final String TAG = VMReimbursement.class.getSimpleName();

    private final MutableLiveData<List<ReimburseInfo>> plExpInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnTotlExp = new MutableLiveData<>();

    public VMReimbursement(@NonNull Application application) {
        super(application);
        plExpInfo.setValue(new ArrayList<>());
        pnTotlExp.setValue(0);
    }

    public LiveData<List<ReimburseInfo>> getExpensesList(){
        return plExpInfo;
    }

    public LiveData<Integer> getTotalAmount(){
        return pnTotlExp;
    }

    public void addExpDetail(ReimburseInfo foInfo, ExpActionListener listener){
        if(foInfo.isDataValid()) {
            Objects.requireNonNull(this.plExpInfo.getValue()).add(foInfo);
            listener.onSuccess("Success");
            calculateTotal();
        } else {
            listener.onFailed(foInfo.getMessage());
        }
    }

    public void updateExpDetail(int position, ReimburseInfo foInfo, ExpActionListener listener){
        if(foInfo.isDataValid()) {
            Objects.requireNonNull(this.plExpInfo.getValue()).set(position, foInfo);
            listener.onSuccess("Success");
            calculateTotal();
        } else {
            listener.onFailed(foInfo.getMessage());
        }
    }

    public void deleteExpDetail(int position){
        Objects.requireNonNull(this.plExpInfo.getValue()).remove(position);
        calculateTotal();
    }

    private void calculateTotal(){
        int total = 0;
        for(int x = 0; x < Objects.requireNonNull(plExpInfo.getValue()).size(); x++){
            total = total + plExpInfo.getValue().get(x).getsAmountxx();
        }
        pnTotlExp.setValue(total);
    }

    public interface ExpActionListener{
        void onSuccess(String message);
        void onFailed(String message);
    }
}