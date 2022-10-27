package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Business;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpouseBusinessInfo implements CreditApp {
    private static final String TAG = SpouseBusinessInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;

    private Business poDetail;

    private String message;

    public SpouseBusinessInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getSpsBusnx();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.SpouseMeansInfo().SelfEmployedInfo().setData(joDetail);

            Business loDetail = new Business();
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

            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        Business loDetail = (Business) args;

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
    public boolean Save(Object args) {
        try{
            Business loDetail = (Business) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
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
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
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
