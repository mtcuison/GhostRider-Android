package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONArray;
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
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.OtherReference;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Reference;
import org.rmj.gocas.base.GOCASApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OtherInfo implements CreditApp {
    private static final String TAG = OtherInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;

    private OtherReference poDetail;

    private String message;

    public OtherInfo(Application instance) {
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
            OtherReference loDetail = new OtherReference();
            if (args.getOthrInfo() != null){
                String lsDetail = args.getOthrInfo();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.OtherInfo().setData(joDetail);

                loDetail.setSource(gocas.OtherInfo().getSourceInfo());
                loDetail.setsUnitUser(gocas.OtherInfo().getUnitUser());
                loDetail.setsUsr2Buyr(gocas.OtherInfo().getOtherUser());
                loDetail.setsUnitPayr(gocas.OtherInfo().getUnitPayor());
                loDetail.setsPyr2Buyr(gocas.OtherInfo().getPayorRelation());
                loDetail.setsPurposex(gocas.OtherInfo().getPurpose());
                loDetail.setSource(gocas.OtherInfo().getSourceInfo());

                JSONArray reference = (JSONArray) joDetail.get("personal_reference");

                for(int x = 0; x < reference.size(); x++){
                    JSONObject joRef = (JSONObject) reference.get(x);
                    loDetail.AddReference(new Reference(
                            (String) joRef.get("sRefrNmex"),
                            (String) joRef.get("sRefrAddx"),
                            (String) joRef.get("sRefrTown"),
                            (String) joRef.get("sTownName"),
                            (String) joRef.get("sRefrMPNx")));
                }

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
        OtherReference loDetail = (OtherReference) args;

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
            OtherReference loDetail = (OtherReference) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            JSONObject loJson = new JSONObject();

            if (loDetail.getSource().equalsIgnoreCase("Others")){
                loJson.put("sSrceInfo", loDetail.getCompanyInfoSource());
            }else{
                loJson.put("sSrceInfo", loDetail.getSource());
            }

            JSONArray reference = new JSONArray();

            loJson.put("sUnitUser", loDetail.getsUnitUser());
            loJson.put("sUsr2Buyr", loDetail.getsUsr2Buyr());
            loJson.put("sUnitPayr", loDetail.getsUnitPayr());
            loJson.put("sPyr2Buyr", loDetail.getsPyr2Buyr());
            loJson.put("sPurposex", loDetail.getsPurposex());

            List<Reference> loList = loDetail.getReferences();
            for(int x = 0; x < loList.size(); x++){
                JSONObject jsonObject = new JSONObject();
                Reference loInfo = loList.get(x);
                jsonObject.put("sRefrNmex", loInfo.getFullname());
                jsonObject.put("sRefrTown", loInfo.getTownCity());
                jsonObject.put("sTownName", loInfo.getTownName());
                jsonObject.put("sRefrMPNx", loInfo.getContactN());
                jsonObject.put("sRefrAddx", loInfo.getAddress1());
                reference.add(x,jsonObject);

            }
            loJson.put("personal_reference", reference);
            gocas.OtherInfo().setData(loJson);
            loApp.setOthrInfo(gocas.OtherInfo().toJSONString());

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
