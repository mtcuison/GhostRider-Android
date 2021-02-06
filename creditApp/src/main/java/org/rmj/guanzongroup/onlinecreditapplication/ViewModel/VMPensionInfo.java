package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PensionInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

public class VMPensionInfo extends AndroidViewModel {
    private static final String TAG = VMPersonalInfo.class.getSimpleName();
    private final RCreditApplicant poCreditApp;
    private final GOCASApplication poGoCas;

    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> poJson = new MutableLiveData<>();

    public VMPensionInfo(@NonNull Application application) {
        super(application);
        poCreditApp = new RCreditApplicant(application);
        poGoCas = new GOCASApplication();
    }

    public void setTransNox(String fsTransNox){
        this.TransNox.setValue(fsTransNox);
    }

    public LiveData<ECreditApplicantInfo> getApplicationInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

    public void setDetailInfo(String fsDetailInfo){
        try{
            poGoCas.setData(fsDetailInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMeansInfos(String foJson){
        try{
            poJson.setValue(new JSONObject(foJson));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public LiveData<ArrayAdapter<String>> getPensionSector(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.PENSION_SECTOR);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void SavePensionInfo(PensionInfoModel infoModel, ViewModelCallBack callBack){
        try{
            if(infoModel.isPensionInfoValid()) {
                poGoCas.MeansInfo().PensionerInfo().setSource(infoModel.getPensionSector());
                poGoCas.MeansInfo().PensionerInfo().setAmount(infoModel.getPensionIncomeRange());
                poGoCas.MeansInfo().PensionerInfo().setYearRetired(infoModel.getRetirementYear());
                poGoCas.MeansInfo().setOtherIncomeNature(infoModel.getNatureOfIncome());
                poGoCas.MeansInfo().setOtherIncomeAmount(infoModel.getRangeOfIncome());
                callBack.onSaveSuccessResult(infoModel.getMessage());
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}