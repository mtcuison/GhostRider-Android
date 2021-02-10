package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DatePickerFragment;

import static java.security.AccessController.getContext;

public class VMPromiseToPay extends AndroidViewModel {
    private static final String TAG = VMPromiseToPay.class.getSimpleName();
    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    MutableLiveData<Boolean> timePickerDialogData = new MutableLiveData<>();
    private Context context;


    public VMPromiseToPay(@NonNull Application application) {
        super(application);
        this.poBranch = new RBranch(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.context = application;
    }



}