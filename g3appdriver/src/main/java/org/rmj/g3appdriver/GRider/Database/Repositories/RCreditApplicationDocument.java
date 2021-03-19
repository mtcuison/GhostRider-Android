package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

import java.util.List;

public class RCreditApplicationDocument {
    private static final String TAG = "DB_Document_Repository";
    private final DCreditApplicationDocuments documentsDao;
    private final Application application;
    public RCreditApplicationDocument(Application application){
        this.application = application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.documentsDao = appDatabase.DocumentInfoDao();
    }

    /**
     *
     * @return returns a LiveData List of all unsent image info...
     */
    public void insertDocumentInfo(ECreditApplicationDocuments documentsInfo){
        new InsertTask(documentsDao, "insert").execute(documentsInfo);
    }
    public void updateDocumentInfo(ECreditApplicationDocuments documentsInfo){
        new InsertTask(documentsDao, "update").execute(documentsInfo);
    }

    public LiveData<List<ECreditApplicationDocuments>> getDocumentInfo(){
        return documentsDao.getDocumentInfo();
    }
    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocument(String TransNox){
        return documentsDao.getDocument(TransNox);
    }


    private static class InsertTask extends AsyncTask<ECreditApplicationDocuments, Void, String>{
        private final DCreditApplicationDocuments documentsDao;
        private String transInfo;
        public InsertTask(DCreditApplicationDocuments documentsDao, String transInfo) {
            this.documentsDao = documentsDao;
            this.transInfo = transInfo;
        }

        @Override
        protected String doInBackground(ECreditApplicationDocuments... creditApplicationDocuments) {
            if (transInfo.equalsIgnoreCase("insert")){
                documentsDao.insert(creditApplicationDocuments[0]);
                Log.e(TAG, "Document info has been save in background!");
            }else{
                documentsDao.update(creditApplicationDocuments[0]);
                Log.e(TAG, "Document info has been update in background!");
            }
            return null;
        }
    }

    public String getImageNextCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Credit_Online_Application_Documents", "sDocTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }
}
