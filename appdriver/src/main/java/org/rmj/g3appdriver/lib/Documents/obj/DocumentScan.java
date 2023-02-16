package org.rmj.g3appdriver.lib.Documents.obj;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DFileCode;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.Repositories.AppTokenManager;
import org.rmj.g3appdriver.dev.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.lib.Documents.pojo.CreditAppDocs;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.List;

public class DocumentScan {
    private static final String TAG = DocumentScan.class.getSimpleName();

    private DFileCode poDao;

    private final AppConfigPreference poConfig;
    private final RImageInfo poImage;
    private final WebApi poApi;
    private final AppTokenManager poToken;
    private final HttpHeaders poHeaders;

    private String message;

    public DocumentScan(Application instance){
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poImage = new RImageInfo(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poToken = new AppTokenManager(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<List<DFileCode.ApplicationDocument>> GetCreditAppDocuments(String args){
        return poDao.GetCreditAppDocuments(args);
    }

    public boolean InitializeCreditAppDocumentsList(String args){
        try{
            List<ECreditApplicationDocuments> loDocs = poDao.GetDocumentsList(args);

            if(loDocs == null){
                poDao.InitializeDocumentsList(args);
                Log.d(TAG, "Credit app documents has been initialize.");
                return true;
            }

            if(loDocs.size() == 0){
                poDao.InitializeDocumentsList(args);
                Log.d(TAG, "Credit app documents has been initialize.");
                return true;
            }

            poDao.UpdateDocumentsList(args);
            Log.d(TAG, "Credit app documents has been updated.");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean SaveCapturedDocument(CreditAppDocs args){
        try{
            ECreditApplicationDocuments loDocs = poDao.GetDocument(args.getTransNox(), args.getFileCode());

            if(loDocs == null){
                message = "Unable to get document info for update. Please restart the app.";
                return false;
            }

            String lsImageID = poImage.SaveCreditAppDocument(
                    args.getTransNox(),
                    args.getFileCode(),
                    args.getImageNme(),
                    args.getFileLoct());

            if(lsImageID == null){
                message = poImage.getMessage();
                return false;
            }

            loDocs.setEntryNox(args.getEntryNox());
            loDocs.setImageNme(args.getImageNme());
            loDocs.setImageIDx(lsImageID);
            loDocs.setFileLoc(args.getFileLoct());

            poDao.update(loDocs);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadCreditAppDocument(String TransNox, String FileCode){
        try{
            ECreditApplicationDocuments loDocs = poDao.GetDocument(TransNox, FileCode);

            if(loDocs == null){
                message = "Unable to get document info for update. Please restart the app.";
                return false;
            }

            String lsResult = poImage.UploadImage(loDocs.getImageIDx());

            if(lsResult == null){
                message = poImage.getMessage();
                return false;
            }

            loDocs.setImageIDx(lsResult);
            loDocs.setSendStat("1");
            poDao.update(loDocs);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean CheckFile(String TransNox){
        try{
            String lsClient = poToken.GetClientToken();

            if(lsClient == null){
                message = poToken.getMessage();
                return false;
            }

            String lsAccess = poToken.GetAccessToken(lsClient);

            if(lsAccess == null){
                message = poToken.getMessage();
                return false;
            }

            org.json.simple.JSONObject lsResponse = WebFileServer.CheckFile(
                    lsAccess,
                    "",
                    "",
                    "",
                    TransNox);

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);

            if(loResponse.has("result")){
                String lsResult = loResponse.getString("result");
                Log.e(TAG, lsResult);
                if (lsResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    return false;
                }
            } else {
                String lsResult = loResponse.getString("rhsult");
                Log.e(TAG, lsResult);
                if (lsResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    return false;
                }
            }

            JSONArray laResult = loResponse.getJSONArray("detail");
            for(int x = 0; x < laResult.length(); x++){
                JSONObject loJson = laResult.getJSONObject(x);
                String lsFileCde = loJson.getString("sFileCode");
                poDao.UpdateCheckedFile(TransNox, lsFileCde);
                Log.d(TAG, "File code has been updated.");
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }


}
