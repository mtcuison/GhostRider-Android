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
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientPension;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class PensionInfo implements CreditApp {
    private static final String TAG = PensionInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private ClientPension poDetail;

    private String message;

    public PensionInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getResidnce();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.MeansInfo().PensionerInfo().setData(joDetail);

            ClientPension loDetail = new ClientPension();

            loDetail.setPensionSector(gocas.MeansInfo().PensionerInfo().getSource());
            loDetail.setPensionIncomeRange(String.valueOf(gocas.MeansInfo().PensionerInfo().getAmount()));
//            loDetail.setRetirementYear(gocas.MeansInfo().PensionerInfo().get);
            loDetail.setNatureOfIncome(gocas.MeansInfo().getOtherIncomeNature());
            loDetail.setRangeOfIncom(String.valueOf(gocas.MeansInfo().getOtherIncomeAmount()));

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
        ClientPension loDetail = (ClientPension) args;

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
            ClientPension loDetail = (ClientPension) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.MeansInfo().PensionerInfo().setSource(loDetail.getPensionSector());
            gocas.MeansInfo().PensionerInfo().setAmount(loDetail.getPensionIncomeRange());
            gocas.MeansInfo().PensionerInfo().setYearRetired(loDetail.getRetirementYear());

            //TODO refactor saving other income nature and range of income to gocas
            gocas.MeansInfo().setOtherIncomeNature(loDetail.getNatureOfIncome());
            gocas.MeansInfo().setOtherIncomeAmount(loDetail.getRangeOfIncome());
            loApp.setPensionx(gocas.MeansInfo().toJSONString());
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
