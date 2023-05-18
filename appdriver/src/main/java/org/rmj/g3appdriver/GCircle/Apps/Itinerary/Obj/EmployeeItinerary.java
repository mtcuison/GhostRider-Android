package org.rmj.g3appdriver.GCircle.Apps.Itinerary.Obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DItinerary;
import org.rmj.g3appdriver.GCircle.room.Entities.EItinerary;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeeItinerary {
    private static final String TAG = EmployeeItinerary.class.getSimpleName();

    private final Application instance;

    private final DItinerary poDao;
    private final EmployeeMaster poEmployee;

    private String message;

    public EmployeeItinerary(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GCircleDB.getInstance(instance).itineraryDao();
        this.poEmployee = new EmployeeMaster(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poEmployee.GetEmployeeBranch();
    }

    public String SaveItinerary(ItineraryEntry foVal){
        try{
            String lsTransno = CreateUniqueID();
            Log.d(TAG, "Itinerary record id: " + lsTransno);
            if(!foVal.isDataValid()){
               message = foVal.getMessage();
               return null;
            } else if(lsTransno.isEmpty()){
                message = "Unable to create unique id";
                return null;
            } else {
                String lsEmployID = poEmployee.getEmployeeID();
                EItinerary loDetail = new EItinerary();
                loDetail.setTransNox(lsTransno);
                loDetail.setEmployID(lsEmployID);
                loDetail.setTransact(foVal.getTransact());
                loDetail.setTimeFrom(foVal.getTimeStrt());
                loDetail.setTimeThru(foVal.getTimeEndx());
                loDetail.setLocation(foVal.getLocation());
                loDetail.setRemarksx(foVal.getRemarksx());
                loDetail.setTimeStmp(AppConstants.DATE_MODIFIED());
                poDao.Save(loDetail);
                return lsTransno;
            }
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean UploadItinerary(String TransNox){
        try{
            JSONObject params = new JSONObject();
            GCircleApi loApi = new GCircleApi(instance);
            EItinerary loDetail = poDao.GetItineraryForUpload(TransNox);
            Log.d(TAG, "Employee ID: " + loDetail.getEmployID());
            if(loDetail == null){
                message = "Itinerary record not found.";
                Log.d(TAG, message);
                return false;
            }
            params.put("sTransNox", loDetail.getTransNox());
            params.put("sEmployID", loDetail.getEmployID());
            params.put("dTransact", loDetail.getTransact());
            params.put("dTimeFrom", loDetail.getTimeFrom());
            params.put("dTimeThru", loDetail.getTimeThru());
            params.put("sLocation", loDetail.getLocation());
            params.put("sRemarksx", loDetail.getRemarksx());

            String lsResponse = WebClient.sendRequest(loApi.getUrlSaveItinerary(),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(!lsResult.equalsIgnoreCase("success")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = getErrorMessage(loError);
                    return false;
                } else {
                    String lsTransNox = loResponse.getString("sTransNox");
                    Log.d(TAG, "Upload success new transaction no. : " + lsTransNox);
                    poDao.UpdateSentItinerary(TransNox, lsTransNox);
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UploadUnsentItinerary(){
        try{
            List<EItinerary> loList = poDao.GetUnsentItinerary();

            if(loList == null){
                this.message = "No record to upload.";
                return false;
            }

            if(loList.size() == 0){
                this.message = "No record to upload.";
                return false;
            }

            for(int x = 0; x < loList.size(); x++) {
                if(!UploadItinerary(loList.get(x).getTransNox())){
                    Log.e(TAG, "Unable to upload entry. Message: " + getMessage());
                } else {
                    Log.d(TAG, "Itinerary entry has been uploaded successfully.");
                }
                Thread.sleep(1000);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean DownloadItinerary(String fsArgs1, String fsArgs2){
        try {
            String lsEmployID = poEmployee.getEmployeeID();
            GCircleApi loApi = new GCircleApi(instance);
            JSONObject params = new JSONObject();
            params.put("sEmployID", lsEmployID);
            params.put("dDateFrom", fsArgs1);
            params.put("dDateThru", fsArgs2);

            String lsResponse = WebClient.sendRequest(loApi.getUrlDownloadItinerary(),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(!lsResult.equalsIgnoreCase("success")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray jaDetail = loResponse.getJSONArray("detail");
            for(int x = 0; x < jaDetail.length(); x++){
                EItinerary loDetail = new EItinerary();
                loDetail.setSendStat(2);
                JSONObject joDetail = jaDetail.getJSONObject(x);
                EItinerary exist = poDao.GetItineraryForUpload(joDetail.getString("sTransNox"));
                if(exist == null) {
                    loDetail.setTransNox(joDetail.getString("sTransNox"));
                    loDetail.setEmployID(joDetail.getString("sEmployID"));
                    loDetail.setTransact(joDetail.getString("dTransact"));
                    loDetail.setTimeFrom(joDetail.getString("dTimeFrom"));
                    loDetail.setTimeThru(joDetail.getString("dTimeThru"));
                    loDetail.setLocation(joDetail.getString("sLocation"));
                    loDetail.setRemarksx(joDetail.getString("sRemarksx"));
                    loDetail.setTimeStmp(joDetail.getString("dTimeStmp"));
                    poDao.Save(loDetail);
                    Log.d(TAG, "New record save!");
                } else {
                    Date ldDate1 = SQLUtil.toDate(exist.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) joDetail.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)){
                        poDao.UpdateItineraryStatus(joDetail.getString("sTransNox"));
                        Log.d(TAG, "Record updated for preview!");
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public List<JSONObject> GetEmployeeList(){
        try {
            GCircleApi loApi = new GCircleApi(instance);
            String lsResponse = WebClient.sendRequest(loApi.getUrlDownloadItineraryUsers(),
                    new JSONObject().toString(),
                    HttpHeaders.getInstance(instance).getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            List<JSONObject> loDetail= new ArrayList<>();
            JSONArray jaDetail = loResponse.getJSONArray("detail");
            for(int x = 0; x < jaDetail.length(); x++){
                loDetail.add(jaDetail.getJSONObject(x));
            }

            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public List<EItinerary> GetItineraryListForEmployee(String args, String fsArgs1, String fsArgs2){
        try {
            GCircleApi loApi = new GCircleApi(instance);
            JSONObject params = new JSONObject();
            params.put("sEmployID", args);
            params.put("dDateFrom", fsArgs1);
            params.put("dDateThru", fsArgs2);

            String lsResponse = WebClient.sendRequest(loApi.getUrlDownloadItinerary(),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(!lsResult.equalsIgnoreCase("success")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            JSONArray jaDetail = loResponse.getJSONArray("detail");
            List<EItinerary> loDetails = new ArrayList<>();
            for(int x = 0; x < jaDetail.length(); x++){
                JSONObject joDetail = jaDetail.getJSONObject(x);
                EItinerary loDetail = new EItinerary();
                loDetail.setTransNox(joDetail.getString("sTransNox"));
                loDetail.setEmployID(joDetail.getString("sEmployID"));
                loDetail.setTransact(joDetail.getString("dTransact"));
                loDetail.setTimeFrom(joDetail.getString("dTimeFrom"));
                loDetail.setTimeThru(joDetail.getString("dTimeThru"));
                loDetail.setLocation(joDetail.getString("sLocation"));
                loDetail.setRemarksx(joDetail.getString("sRemarksx"));
                loDetail.setTimeStmp(joDetail.getString("dTimeStmp"));
                loDetail.setSendStat(2);
                loDetails.add(loDetail);
            }
            return loDetails;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public LiveData<List<EItinerary>> GetItineraryListForCurrentDay(){
        return poDao.GetItineraryListForCurrentDay();
    }

    public LiveData<List<EItinerary>> GetItineraryListForFilteredDate(String fsArgs1, String fsArgs2){
        return poDao.GetItineraryListForFilteredDate(fsArgs1, fsArgs2);
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetRowsCountForID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, lsUniqIDx);
        return lsUniqIDx;
    }

    public static class ItineraryEntry {
        private String dTransact;
        private String dTimeStrt;
        private String dTimeEndx;
        private String sLocation;
        private String sRemarksx;

        private String message;

        public ItineraryEntry() {
        }

        public String getMessage() {
            return message;
        }

        public String getTransact() {
            return dTransact;
        }

        public void setTransact(String dTransact) {
            this.dTransact = dTransact;
        }

        public String getTimeStrt() {
            return dTimeStrt;
        }

        public void setTimeStrt(String dTimeStrt) {
            this.dTimeStrt = dTimeStrt;
        }

        public String getTimeEndx() {
           return dTimeEndx;
        }

        public void setTimeEndx(String dTimeEndx) {
            this.dTimeEndx = dTimeEndx;
        }

        public String getLocation() {
            return sLocation;
        }

        public void setLocation(String sLocation) {
            this.sLocation = sLocation;
        }

        public String getRemarksx() {
            return sRemarksx;
        }

        public void setRemarksx(String sRemarksx) {
            this.sRemarksx = sRemarksx;
        }

        public boolean isDataValid(){
            if(dTransact == null){
                message = "Please enter date of trip";
                return false;
            } else if(dTransact.trim().isEmpty()){
                message = "Please enter date of trip";
                return false;
            } else if(dTimeStrt == null){
                message = "Please enter time start";
                return false;
            } else if (dTimeStrt.trim().isEmpty()){
                message = "Please enter time start";
                return false;
            } else if(dTimeEndx == null){
                message = "Please enter time end";
                return false;
            } else if(dTimeEndx.trim().isEmpty()){
                message = "Please enter time end";
                return false;
            } else if(sLocation == null){
                message = "Please enter location";
                return false;
            } else if(sLocation.trim().isEmpty()){
                message = "Please enter location";
                return false;
            } else if(sRemarksx == null){
                message = "Please enter remarks";
                return false;
            } else if(sRemarksx.trim().isEmpty()){
                message = "Please enter remarks";
                return false;
            } else {
                return true;
            }
        }
    }
}
