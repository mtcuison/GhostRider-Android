package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Business;
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

public class BusinessInfo implements CreditApp {
    private static final String TAG = BusinessInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;

    private Business poDetail;

    private String message;

    public BusinessInfo(Application instance) {
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

            Business loDetail = new Business();
            if(args.getBusnInfo() != null){
                String lsDetail = args.getBusnInfo();
//            Log.e("busnInfo",lsDetail.toString());
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.MeansInfo().SelfEmployedInfo().setData(joDetail);


                loDetail.setNatureOfBusiness(gocas.MeansInfo().SelfEmployedInfo().getNatureOfBusiness());
                loDetail.setNameOfBusiness(gocas.MeansInfo().SelfEmployedInfo().getNameOfBusiness());
                loDetail.setBusinessAddress(gocas.MeansInfo().SelfEmployedInfo().getBusinessAddress());

                String lsTown = gocas.MeansInfo().SelfEmployedInfo().getBusinessTown();

                DTownInfo.TownProvinceName loTown = poTown.getTownProvinceName(lsTown);

                loDetail.setTown(lsTown);
                loDetail.setTypeOfBusiness(gocas.MeansInfo().SelfEmployedInfo().getBusinessType());
                loDetail.setSizeOfBusiness(gocas.MeansInfo().SelfEmployedInfo().getOwnershipSize());

                double lnLength = gocas.MeansInfo().SelfEmployedInfo().getBusinessLength();

                if(lnLength % 1 == 0){
                    loDetail.setIsYear("1");
                } else {
                    loDetail.setIsYear("0");
                }

                loDetail.setLengthOfService(gocas.MeansInfo().SelfEmployedInfo().getBusinessLength());
                loDetail.setMonthlyExpense(gocas.MeansInfo().SelfEmployedInfo().getMonthlyExpense());
                loDetail.setMonthlyIncome(gocas.MeansInfo().SelfEmployedInfo().getIncome());

                poDetail = loDetail;
            }

            return loDetail;
        }catch (NullPointerException e){
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
        Business loDetail = (Business) args;

        if(poDetail == null){
            if(loDetail.isPrimary()){
                if(!loDetail.isDataValid()){
                    message = loDetail.getMessage();
                    return 0;
                }
            }else{
                return 1;
            }
        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.
            if(loDetail.isPrimary()){
                if(!loDetail.isDataValid()){
                    message = loDetail.getMessage();
                    return 0;
                }
            }else{
                return 1;
            }
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
            Business loDetail = (Business) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();
            gocas.MeansInfo().SelfEmployedInfo().setNatureOfBusiness(loDetail.getNatureOfBusiness());
            gocas.MeansInfo().SelfEmployedInfo().setNameOfBusiness(loDetail.getNameOfBusiness());
            gocas.MeansInfo().SelfEmployedInfo().setBusinessAddress(loDetail.getBusinessAddress());
            gocas.MeansInfo().SelfEmployedInfo().setCompanyTown(loDetail.getTown());
            gocas.MeansInfo().SelfEmployedInfo().setBusinessType(loDetail.getTypeOfBusiness());
            gocas.MeansInfo().SelfEmployedInfo().setOwnershipSize(loDetail.getSizeOfBusiness());
            gocas.MeansInfo().SelfEmployedInfo().setBusinessLength(loDetail.getLenghtOfService());
            gocas.MeansInfo().SelfEmployedInfo().setMonthlyExpense(loDetail.getMonthlyExpense());
            gocas.MeansInfo().SelfEmployedInfo().setIncome(loDetail.getMonthlyIncome());

            loApp.setBusnInfo(gocas.MeansInfo().SelfEmployedInfo().toJSONString());
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
