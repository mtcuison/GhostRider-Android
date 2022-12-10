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
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpouseResidence;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpouseResidenceInfo implements CreditApp {
    private static final String TAG = SpouseResidenceInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RBarangay poBrgy;

    private SpouseResidence poDetail;

    private String message;

    public SpouseResidenceInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
        this.poBrgy = new RBarangay(instance);
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
            gocas.ResidenceInfo().setData(joDetail);

            SpouseResidence loDetail = new SpouseResidence();
            loDetail.setLandMark(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getLandMark());
            loDetail.setHouseNox(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getHouseNo());
            loDetail.setAddress1(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getAddress1());
            loDetail.setAddress2(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getAddress2());
            loDetail.setMunicipalID(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getTownCity());
            loDetail.setBarangayID(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getBarangay());

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
        SpouseResidence loDetail = (SpouseResidence) args;

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
            SpouseResidence loDetail = (SpouseResidence) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.SpouseInfo().ResidenceInfo().PresentAddress().setLandMark(loDetail.getLandMark());
            gocas.SpouseInfo().ResidenceInfo().PresentAddress().setHouseNo(loDetail.getHouseNox());
            gocas.SpouseInfo().ResidenceInfo().PresentAddress().setAddress1(loDetail.getAddress1());
            gocas.SpouseInfo().ResidenceInfo().PresentAddress().setAddress2(loDetail.getAddress2());
            gocas.SpouseInfo().ResidenceInfo().PresentAddress().setTownCity(loDetail.getMunicipalID());
            gocas.SpouseInfo().ResidenceInfo().PresentAddress().setBarangay(loDetail.getBarangayID());

            loApp.setSpsResdx(gocas.SpouseInfo().ResidenceInfo().toJSONString());
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
        return poBrgy.getAllBarangayFromTown(args);
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
