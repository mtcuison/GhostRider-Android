package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EMcBrand;
import org.rmj.g3appdriver.dev.Database.Entities.EMcModel;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;

import java.util.List;

public class VMIntroductoryQuestion extends AndroidViewModel implements CreditAppUI{
    private static final String TAG = VMIntroductoryQuestion.class.getSimpleName();

    private final CreditOnlineApplication poApp;

    private final MutableLiveData<String> psBrandID = new MutableLiveData<>();
    private final MutableLiveData<String> psModelID = new MutableLiveData<>();

    public VMIntroductoryQuestion(@NonNull Application instance) {
        super(instance);
        this.poApp = new CreditOnlineApplication(instance);
    }

    public void setBrandID(String args){
        this.psBrandID.setValue(args);
    }

    public LiveData<String> GetBrandID(){
        return psBrandID;
    }

    public void setModelID(String args){
        this.psModelID.setValue(args);
    }

    public LiveData<String> GetModelID(){
        return psModelID;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poApp.GetUserInfo();
    }

    public LiveData<List<EBranchInfo>> GetAllBranchInfo(){
        return poApp.getAllBranchInfo();
    }

    public LiveData<List<EMcBrand>> GetAllMcBrand(){
        return poApp.getAllMcBrand();
    }

    public LiveData<List<EMcModel>> GetAllBrandModelInfo(String args){
        return poApp.getAllBrandModelInfo(args);
    }

    public LiveData<DMcModel.McAmortInfo> GetMonthlyPayment(String ModelID, int Term){
        return poApp.GetMonthlyPayment(ModelID, Term);
    }

    public double GetMonthlyPayment(DMcModel.McAmortInfo args, double fnDPxx){
        return poApp.CalculateAmortization(args, fnDPxx);
    }

    public LiveData<ArrayAdapter<String>> GetApplicationType(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.APPLICATION_TYPE));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> GetCustomerType(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.CUSTOMER_TYPE));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> GetInstallmentTerm(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INSTALLMENT_TERM));
        return liveData;
    }

    @Override
    public void InitializeApplication(Intent params) {
        Log.d(TAG, "No data to initialize on introductory question");
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication() {
        return null;
    }

    @Override
    public void ParseData(ECreditApplicantInfo args, OnParseListener listener) {
        Log.d(TAG, "No data to parse on introductory question");
    }

    @Override
    public void Validate(Object args) {

    }

    @Override
    public void SaveData(Object args, OnSaveInfoListener listener) {

    }
}
