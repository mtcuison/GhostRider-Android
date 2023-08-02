package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Means;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class MeansSelectionInfo implements CreditApp {
    private static final String TAG = MeansSelectionInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private Means poDetail;

    private String message;

    public MeansSelectionInfo(Application instance){
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
    }


    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{

            String lsDetail = args.getAppMeans();
            if(lsDetail == null){
                message = "No source of income been set yet.";
                return null;
            }

            if(lsDetail.trim().isEmpty()){
                message = "No source of income  has been set yet.";
                return null;
            }
            GOCASApplication gocas = new GOCASApplication();
//            JSONParser loJson = new JSONParser();
//            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.MeansInfo().setIncomeSource(lsDetail);

            Means loDetail = new Means();

            loDetail.setIncmeSrc(gocas.MeansInfo().getIncomeSource());

            poDetail = loDetail;
            return loDetail;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        Means loDetail = (Means) args;
        if(!loDetail.isDataValid()){
            message = loDetail.getMessage();
            return 0;
        }

//        if(poDetail == null){
//
//            if(!loDetail.isDataValid()){
//                message = loDetail.getMessage();
//                return 0;
//            }
//
//        } else {
//
//            //TODO: if all information inside each old object and new object is not the same,
//            // return 2 to indicate validation needs confirmation from user to update the
//            // previous information being save.
//
////            if(!poDetail.isEqual(loDetail)){
////                return 2;
////            } else {
////                return 1;
////            }
//        }

        return 1;
    }

    @Override
    public String Save(Object args) {
        try{
            Means loDetail = (Means) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.MeansInfo().setIncomeSource(loDetail.getIncmeSrc());

            loApp.setAppMeans(gocas.MeansInfo().getIncomeSource());
            poDao.Update(loApp);
            return loDetail.getTransNox();
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LiveData<List<EBarangayInfo>> GetBarangayList(String args) {
        return null;
    }

    @Override
    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return null;
    }

    @Override
    public LiveData<List<ECountryInfo>> GetCountryList() {
        return null;
    }

    @Override
    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return null;
    }

}
