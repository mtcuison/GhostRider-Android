package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.SpouseBusiness;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Town;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpouseBusinessInfo implements CreditApp {
    private static final String TAG = SpouseBusinessInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;

    private SpouseBusiness poDetail;

    private String message;

    public SpouseBusinessInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new Town(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            SpouseBusiness loDetail = new SpouseBusiness();
            if(args.getSpsBusnx() != null){
                String lsDetail = args.getSpsBusnx();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.SpouseMeansInfo().SelfEmployedInfo().setData(joDetail);


                loDetail.setNatureOfBusiness(gocas.SpouseMeansInfo().SelfEmployedInfo().getNatureOfBusiness());
                loDetail.setNameOfBusiness(gocas.SpouseMeansInfo().SelfEmployedInfo().getNameOfBusiness());
                loDetail.setBusinessAddress(gocas.SpouseMeansInfo().SelfEmployedInfo().getBusinessAddress());

                String lsTown = gocas.SpouseMeansInfo().SelfEmployedInfo().getBusinessTown();

                DTownInfo.TownProvinceName loTown = poTown.getTownProvinceName(lsTown);

                loDetail.setTown(lsTown);
                loDetail.setTypeOfBusiness(gocas.SpouseMeansInfo().SelfEmployedInfo().getBusinessType());
                loDetail.setSizeOfBusiness(gocas.SpouseMeansInfo().SelfEmployedInfo().getOwnershipSize());

                double lnLength = gocas.SpouseMeansInfo().SelfEmployedInfo().getBusinessLength();

                if(lnLength % 1 == 0){
                    loDetail.setIsYear("1");
                } else {
                    loDetail.setIsYear("0");
                }

                loDetail.setLengthOfService(gocas.SpouseMeansInfo().SelfEmployedInfo().getBusinessLength());
                loDetail.setMonthlyExpense(gocas.SpouseMeansInfo().SelfEmployedInfo().getMonthlyExpense());
                loDetail.setMonthlyIncome(gocas.SpouseMeansInfo().SelfEmployedInfo().getIncome());

                poDetail = loDetail;

            }
            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        SpouseBusiness loDetail = (SpouseBusiness) args;

        if(poDetail == null){

            if(!loDetail.isDataValid()){
                message = loDetail.getMessage();
                return 0;
            }

        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.

//            if(!poDetail.isEqual(loDetail)){
//                return 2;
//            } else {
//                return 1;
//            }
        }

        return 1;
    }

    @Override
    public String Save(Object args) {
        try{
            SpouseBusiness loDetail = (SpouseBusiness) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.SpouseMeansInfo().SelfEmployedInfo().setNatureOfBusiness(loDetail.getNatureOfBusiness());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setNameOfBusiness(loDetail.getNameOfBusiness());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setBusinessAddress(loDetail.getBusinessAddress());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setCompanyTown(loDetail.getTown());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setBusinessType(loDetail.getTypeOfBusiness());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setOwnershipSize(loDetail.getSizeOfBusiness());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setBusinessLength(loDetail.getLenghtOfService());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setMonthlyExpense(loDetail.getMonthlyExpense());
            gocas.SpouseMeansInfo().SelfEmployedInfo().setIncome(loDetail.getMonthlyIncome());

            loApp.setSpsBusnx(gocas.SpouseMeansInfo().SelfEmployedInfo().toJSONString());

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
        return poTown.getTownProvinceInfo();
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
