package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Pension;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class PensionInfo implements CreditApp {
    private static final String TAG = PensionInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private Pension poDetail;

    private String message;

    public PensionInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            Pension loDetail = new Pension();
            if(args.getPensionx() != null){
                String lsDetail = args.getPensionx();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.MeansInfo().PensionerInfo().setData(joDetail);

                loDetail.setPensionSector(gocas.MeansInfo().PensionerInfo().getSource());
                loDetail.setPensionIncomeRange(gocas.MeansInfo().PensionerInfo().getAmount());
//            loDetail.setRetirementYear(gocas.MeansInfo().PensionerInfo().get);

                lsDetail = args.getOtherInc();
                gocas = new GOCASApplication();
                loJson = new JSONParser();
                joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.MeansInfo().setData(joDetail);

                loDetail.setNatureOfIncome(gocas.MeansInfo().getOtherIncomeNature());
//            loDetail.setRangeOfIncom(gocas.MeansInfo().getOtherIncomeAmount());

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
        Pension loDetail = (Pension) args;
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
        try {
            Pension loDetail = (Pension) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.MeansInfo().PensionerInfo().setSource(loDetail.getPensionSector());
            gocas.MeansInfo().PensionerInfo().setAmount(loDetail.getPensionIncomeRange());
            gocas.MeansInfo().PensionerInfo().setYearRetired(loDetail.getRetirementYear());

            //TODO refactor saving other income nature and range of income to gocas
            gocas.MeansInfo().setOtherIncomeNature(loDetail.getNatureOfIncome());
            gocas.MeansInfo().setOtherIncomeAmount(loDetail.getRangeOfIncome());
            loApp.setPensionx(gocas.MeansInfo().PensionerInfo().toJSONString());
            loApp.setOtherInc(gocas.MeansInfo().toJSONString());
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
