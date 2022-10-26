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
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientBusiness;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class BusinessInfo implements CreditApp {
    private static final String TAG = BusinessInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;

    private ClientBusiness poDetail;

    private String message;

    public BusinessInfo(Application instance) {
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
            String lsDetail = args.getBusnInfo();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.MeansInfo().SelfEmployedInfo().setData(joDetail);

            ClientBusiness loDetail = new ClientBusiness();

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

            loDetail.setLengthOfService(String.valueOf(gocas.MeansInfo().SelfEmployedInfo().getBusinessLength()));
            loDetail.setMonthlyExpense(String.valueOf(gocas.MeansInfo().SelfEmployedInfo().getMonthlyExpense()));
            loDetail.setMonthlyIncome(String.valueOf(gocas.MeansInfo().SelfEmployedInfo().getIncome()));

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
        ClientBusiness loDetail = (ClientBusiness) args;

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
            ClientBusiness loDetail = (ClientBusiness) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
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
