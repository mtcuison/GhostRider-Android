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
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Pension;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpousePension;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpousePensionInfo implements CreditApp {
    private static final String TAG = SpousePensionInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private Pension poDetail;

    private String message;


    public SpousePensionInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getPensionx();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.SpouseMeansInfo().PensionerInfo().setData(joDetail);

            Pension loDetail = new Pension();

            loDetail.setPensionSector(gocas.SpouseMeansInfo().PensionerInfo().getSource());
            loDetail.setPensionIncomeRange(gocas.SpouseMeansInfo().PensionerInfo().getAmount());
//            loDetail.setRetirementYear(gocas.MeansInfo().PensionerInfo().get);

            lsDetail = args.getSpOthInc();
            gocas = new GOCASApplication();
            loJson = new JSONParser();
            joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.SpouseMeansInfo().setData(joDetail);

            loDetail.setNatureOfIncome(gocas.SpouseMeansInfo().getOtherIncomeNature());
//            loDetail.setRangeOfIncom(gocas.MeansInfo().getOtherIncomeAmount());



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
        SpousePension loDetail = (SpousePension) args;

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
        try {
            SpousePension loDetail = (SpousePension) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.SpouseMeansInfo().PensionerInfo().setSource(loDetail.getPensionSector());
            gocas.SpouseMeansInfo().PensionerInfo().setAmount(loDetail.getPensionIncomeRange());
            gocas.SpouseMeansInfo().PensionerInfo().setYearRetired(loDetail.getRetirementYear());

            //TODO refactor saving other income nature and range of income to gocas
            gocas.SpouseMeansInfo().setOtherIncomeNature(loDetail.getNatureOfIncome());
            gocas.SpouseMeansInfo().setOtherIncomeAmount(loDetail.getRangeOfIncome());
            loApp.setSpsPensn(gocas.SpouseMeansInfo().PensionerInfo().toJSONString());
            loApp.setSpOthInc(gocas.SpouseMeansInfo().toJSONString());
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
