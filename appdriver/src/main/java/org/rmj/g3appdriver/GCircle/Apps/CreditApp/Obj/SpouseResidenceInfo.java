package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.SpouseResidence;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.ClientResidence;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Barangay;
import org.rmj.g3appdriver.lib.Etc.Town;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpouseResidenceInfo implements CreditApp {
    private static final String TAG = SpouseResidenceInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;
    private final Barangay poBrgy;

    private SpouseResidence poDetail;

    private String message;

    public SpouseResidenceInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new Town(instance);
        this.poBrgy = new Barangay(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            oResidence loDetail = new oResidence();
            SpouseResidence loSpouse = new SpouseResidence();
            ClientResidence loAppl = new ClientResidence();

            GOCASApplication gocas;
            JSONParser loJson;
            JSONObject joDetail;
            String lsDetail;
            if (args.getSpsResdx() != null){

                lsDetail = args.getSpsResdx();
                gocas = new GOCASApplication();
                loJson = new JSONParser();
                joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.SpouseInfo().ResidenceInfo().setData(joDetail);

                loSpouse.setLandMark(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getLandMark());
                loSpouse.setHouseNox(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getHouseNo());
                loSpouse.setAddress1(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getAddress1());
                loSpouse.setAddress2(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getAddress2());
                loSpouse.setMunicipalID(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getTownCity());
                loSpouse.setBarangayID(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getBarangay());

                String lsBrgy = gocas.SpouseInfo().ResidenceInfo().PresentAddress().getBarangay();
                DBarangayInfo.BrgyTownProvNames loBrgy = poBrgy.getBrgyTownProvName(lsBrgy);

                loSpouse.setMunicipalID(gocas.SpouseInfo().ResidenceInfo().PresentAddress().getTownCity());
                loSpouse.setBarangayID(lsBrgy);

                loSpouse.setMunicipalNm(loBrgy.sTownName + ", " + loBrgy.sProvName);
                loSpouse.setBarangayName(loBrgy.sBrgyName);
                poDetail = loSpouse;
            }

            lsDetail = args.getResidnce();
            gocas = new GOCASApplication();
            loJson = new JSONParser();
            joDetail = (JSONObject) loJson.parse(lsDetail);

            gocas.ResidenceInfo().setData(joDetail);

            loAppl.setLandMark(gocas.ResidenceInfo().PresentAddress().getLandMark());
            loAppl.setHouseNox(gocas.ResidenceInfo().PresentAddress().getHouseNo());
            loAppl.setAddress1(gocas.ResidenceInfo().PresentAddress().getAddress1());
            loAppl.setAddress2(gocas.ResidenceInfo().PresentAddress().getAddress2());

            String lsBrgy = gocas.ResidenceInfo().PresentAddress().getBarangay();
            DBarangayInfo.BrgyTownProvNames loBrgy = poBrgy.getBrgyTownProvName(lsBrgy);

            loAppl.setMunicipalID(gocas.ResidenceInfo().PresentAddress().getTownCity());
            loAppl.setBarangayID(lsBrgy);

            loAppl.setProvinceNm(loBrgy.sProvName);
            loAppl.setMunicipalNm(loBrgy.sTownName);
            loAppl.setBarangayName(loBrgy.sBrgyName);

            loDetail.setSpouseResidence(loSpouse);
            loDetail.setApplResidence(loAppl);

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
    public String Save(Object args) {
        try{
            SpouseResidence loDetail = (SpouseResidence) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
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

    public class oResidence{
        private SpouseResidence spouseResidence = new SpouseResidence();
        private ClientResidence applResidence = new ClientResidence();

        public oResidence() {
        }

        public SpouseResidence getSpouseResidence() {
            return spouseResidence;
        }

        public void setSpouseResidence(SpouseResidence spouseResidence) {
            this.spouseResidence = spouseResidence;
        }

        public ClientResidence getApplResidence() {
            return applResidence;
        }

        public void setApplResidence(ClientResidence applResidence) {
            this.applResidence = applResidence;
        }
    }
}
