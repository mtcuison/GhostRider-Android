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
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Properties;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class PropertiesInfo implements CreditApp {
    private static final String TAG = PropertiesInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private Properties poDetail;

    private String message;

    public PropertiesInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            Properties loDetail = new Properties();
            if (args.getFinancex() != null){
                String lsDetail = args.getFinancex();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.DisbursementInfo().PropertiesInfo().setData(joDetail);

                loDetail.setPsLot1Addx(gocas.DisbursementInfo().PropertiesInfo().getLotName1());
                loDetail.setPsLot2Addx(gocas.DisbursementInfo().PropertiesInfo().getLotName2());
                loDetail.setPsLot3Addx(gocas.DisbursementInfo().PropertiesInfo().getLotName3());
                loDetail.setPs4Wheelsx(gocas.DisbursementInfo().PropertiesInfo().Has4Wheels());
                loDetail.setPs3Wheelsx(gocas.DisbursementInfo().PropertiesInfo().Has3Wheels());
                loDetail.setPs4Wheelsx(gocas.DisbursementInfo().PropertiesInfo().Has2Wheels());
                loDetail.setPsAirConxx(gocas.DisbursementInfo().PropertiesInfo().WithAirCon());
                loDetail.setPsFridgexx(gocas.DisbursementInfo().PropertiesInfo().WithRefrigerator());
                loDetail.setPsTelevsnx(gocas.DisbursementInfo().PropertiesInfo().WithTelevision());

                poDetail = loDetail;
            }
            return loDetail;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        return 0;
    }

    @Override
    public boolean Save(Object args) {
        try{
            Properties loDetail = (Properties) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.DisbursementInfo().PropertiesInfo().setLotName1(loDetail.getPsLot1Addx());
            gocas.DisbursementInfo().PropertiesInfo().setLotName2(loDetail.getPsLot2Addx());
            gocas.DisbursementInfo().PropertiesInfo().setLotName3(loDetail.getPsLot3Addx());
            gocas.DisbursementInfo().PropertiesInfo().Has4Wheels(loDetail.getPs4Wheelsx());
            gocas.DisbursementInfo().PropertiesInfo().Has3Wheels(loDetail.getPs3Wheelsx());
            gocas.DisbursementInfo().PropertiesInfo().Has2Wheels(loDetail.getPs4Wheelsx());
            gocas.DisbursementInfo().PropertiesInfo().WithAirCon(loDetail.getPsAirConxx());
            gocas.DisbursementInfo().PropertiesInfo().WithRefrigerator(loDetail.getPsFridgexx());
            gocas.DisbursementInfo().PropertiesInfo().WithTelevision(loDetail.getPsTelevsnx());
            loApp.setProperty(gocas.DisbursementInfo().PropertiesInfo().toJSONString());

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
