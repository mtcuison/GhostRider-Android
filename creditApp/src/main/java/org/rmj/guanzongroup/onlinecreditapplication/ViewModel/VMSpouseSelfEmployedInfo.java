package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseSelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;

public class VMSpouseSelfEmployedInfo extends AndroidViewModel {
    public static final String TAG = VMSpouseSelfEmployedInfo.class.getSimpleName();
    private final GOCASApplication poGoCas;
    private final RCreditApplicant poCreditApp;
    private final RProvince poProvRepo;
    private final RTown poTownRepo;

    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();

    public VMSpouseSelfEmployedInfo(@NonNull Application application) {
        super(application);
        this.poGoCas = new GOCASApplication();
        this.poCreditApp = new RCreditApplicant(application);
        this.poProvRepo = new RProvince(application);
        this.poTownRepo = new RTown(application);
    }

    public void setTransNox(String transNox){
        this.psTransNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getActiveGOCasApplication(){
        return poCreditApp.getCreditApplicantInfoLiveData(psTransNo.getValue());
    }

    public void setDetailInfo(String fsDetailInfo){
        try{
            poGoCas.setData(fsDetailInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<String[]> getProvinceName(){
        return poProvRepo.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfo(){
        return poProvRepo.getAllProvinceInfo();
    }

    public void setProvinceID(String ID){
        this.psProvID.setValue(ID);
    }

    public LiveData<String[]> getTownNameList(){
        return poTownRepo.getTownNamesFromProvince(psProvID.getValue());
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return poTownRepo.getTownInfoFromProvince(psProvID.getValue());
    }

    public void setTownID(String ID){
        this.psTownID.setValue(ID);
    }

    public LiveData<ArrayAdapter<String>> getNatureOfBusiness(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.BUSINESS_NATURE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getTypeOfBusiness(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.BUSINESS_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSizeOfBusiness(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.BUSINESS_SIZE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getLenghtOfService(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.LENGTH_OF_STAY);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void Save(SpouseSelfEmployedInfoModel infoModel, ViewModelCallBack callBack) {
        try {
            if(infoModel.isSpouseInfoValid()) {
                // TODO: SAVING to Local Database
//                poGoCas.SpouseMeansInfo().SelfEmployedInfo()
            }
            else {
                callBack.onFailedResult(infoModel.getsMsg());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }

}