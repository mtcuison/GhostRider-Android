    /*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

    import android.app.Application;
    import android.util.Log;

    import androidx.annotation.NonNull;
    import androidx.lifecycle.AndroidViewModel;
    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.MutableLiveData;

    import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
    import org.rmj.gocas.base.GOCASApplication;
    import org.rmj.guanzongroup.onlinecreditapplication.Etc.GOCASHolder;
    import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseResidenceInfoModel;
    import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

    import java.util.List;

    public class VMSpouseResidenceInfo extends AndroidViewModel {
        private static final String TAG = VMSpouseResidenceInfo.class.getSimpleName();
        private final GOCASApplication poGoCas; //Java
        private final RCreditApplicant poCreditApp; //Insert Update
        private final RProvince poProvRepo; //Province Repository
        private final RTown poTownRepo; //Town Repository
        private final RBarangay poBarangay;
        private ECreditApplicantInfo poInfo;


        private final MutableLiveData<String> TransNox = new MutableLiveData<>();
        private final MutableLiveData<String> psProvID = new MutableLiveData<>();
        private final MutableLiveData<String> psTownID = new MutableLiveData<>();
        private final MutableLiveData<String> psBrgyID = new MutableLiveData<>();

        private String landmark;
        private String houseNox;
        private String address1;
        private String address2;
        private String provName;
        private String townName;
        private String brgyName;

        public VMSpouseResidenceInfo(@NonNull Application application) {
            super(application);
            this.poGoCas = new GOCASApplication();
            poCreditApp = new RCreditApplicant(application);
            poProvRepo = new RProvince(application);
            poTownRepo = new RTown(application);
            poBarangay = new RBarangay(application);
        }

        // Set TransNox to be saved in applicant info entity
        public boolean setTransNox(String fsTransNox){
            this.TransNox.setValue(fsTransNox);
            if(!this.TransNox.getValue().equalsIgnoreCase(fsTransNox)) {
                return false;
            }
            return true;
        }

        public void setProvinceID(String fsID){
            this.psProvID.setValue(fsID);
        }
        public void setTownID(String fsID){
            this.psTownID.setValue(fsID);
        }
        public void setBrgyID(String fsID) {this.psBrgyID.setValue(fsID);}

        public MutableLiveData<String> getPsProvID() {
            return psProvID;
        }

        public MutableLiveData<String> getPsTownID() {
            return psTownID;
        }

        public MutableLiveData<String> getPsBrgyID() {
            return psBrgyID;
        }

        public String getLandmark() {
            return landmark;
        }

        public String getHouseNox() {
            return houseNox;
        }

        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public String getProvName() {
            return provName;
        }

        public String getTownName() {
            return townName;
        }

        public String getBrgyName() {
            return brgyName;
        }

        // Get single and current input record based from transNox
        public LiveData<ECreditApplicantInfo> getActiveGOCasApplication(){
            return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
        }

        //  Set Detail info to GoCas
        public boolean setDetailInfo(ECreditApplicantInfo fsDetailInfo){
            try{
                poInfo = fsDetailInfo;
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

        // Get all Province Names in a form of LiveData<String[]> ~> For suggest/dropdown in fragment field
        public LiveData<String[]> getProvinceNames() {
            return poProvRepo.getAllProvinceNames();
        }

        // Get all of the Province table fields ~> For province ID  saving and town selection reference
        public LiveData<List<EProvinceInfo>> getProvinceInfos() {
            return poProvRepo.getAllProvinceInfo();
        }

        // Get all Town Names in a form of LiveData<String[]> depending in the provided Province ID ~> For suggest/dropdown in fragment field
        public LiveData<String[]> getAllTownNames() {
            return poTownRepo.getTownNamesFromProvince(psProvID.getValue());
        }

        // Get all of the Town table fields ~> For Town ID saving and town selection reference
        public LiveData<List<ETownInfo>> getAllTownInfo() {
            return poTownRepo.getTownInfoFromProvince(psProvID.getValue());
        }

        public LiveData<String[]> getPermanentBarangayNameList(){
            return poBarangay.getBarangayNamesFromTown(psTownID.getValue());
        }

        public LiveData<List<EBarangayInfo>> getPermanentBarangayInfoList(){
            return poBarangay.getAllBarangayFromTown(psTownID.getValue());
        }

        public LiveData<ETownInfo> getTownNameAndProvID(String townID) {
            return poTownRepo.getTownNameAndProvID(townID);
        }

        public LiveData<String> getProvinceNameFromProvID(String provID) {
            return poProvRepo.getProvinceNameFromProvID(provID);
        }

        public LiveData<String> getBarangayInfoFromID(String fsID) {
            return poBarangay.getBarangayInfoFromID(fsID);
        }

        public boolean Save(SpouseResidenceInfoModel infoModel, ViewModelCallBack callBack){
            try {
                infoModel.setProvince(psProvID.getValue());
                infoModel.setTown(psTownID.getValue());
                infoModel.setBarangay(psBrgyID.getValue());
                if(infoModel.isSpouseResidenceInfoValid()) {
                    poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setLandMark(infoModel.getLandmark());
                    poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setHouseNo(infoModel.getHouseNox());
                    poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setAddress1(infoModel.getAddress1());
                    poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setAddress2(infoModel.getAddress2());
                    poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setTownCity(infoModel.getTown());
                    poGoCas.SpouseInfo().ResidenceInfo().PresentAddress().setBarangay(infoModel.getBarangay());

                    poInfo.setTransNox(TransNox.getValue());
                    poInfo.setSpsResdx(poGoCas.SpouseInfo().ResidenceInfo().toJSONString());
                    poCreditApp.updateGOCasData(poInfo);

                    //Added by sir mike
                    Log.e(TAG, poGoCas.SpouseInfo().ResidenceInfo().toJSONString());
                    Log.e(TAG, "GOCAS Full JSON String : " + poGoCas.toJSONString());
                    callBack.onSaveSuccessResult(TransNox.getValue());
                    return true;
                } else {
                    callBack.onFailedResult(infoModel.getMessage());
                    return false;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                callBack.onFailedResult(e.getMessage());
                return false;
            }
        }


        public void setAddress() {
            landmark=poGoCas.ResidenceInfo().PresentAddress().getLandMark();
            houseNox=poGoCas.ResidenceInfo().PresentAddress().getHouseNo();
            address1=poGoCas.ResidenceInfo().PresentAddress().getAddress1();
            address2=poGoCas.ResidenceInfo().PresentAddress().getAddress2();
            townName=poGoCas.ResidenceInfo().PresentAddress().getTownCity();
            brgyName = poGoCas.ResidenceInfo().PresentAddress().getBarangay();

            setTownID(poGoCas.ResidenceInfo().PresentAddress().getTownCity());
            setBrgyID(poGoCas.ResidenceInfo().PresentAddress().getBarangay());
        }

    }