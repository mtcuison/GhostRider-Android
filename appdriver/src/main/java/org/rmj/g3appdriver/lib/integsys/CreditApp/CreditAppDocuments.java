package org.rmj.g3appdriver.lib.integsys.CreditApp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.CreditAppDocs;

import java.util.List;

public class CreditAppDocuments {
    private static final String TAG = CreditAppDocuments.class.getSimpleName();

    private final Application instance;
    private final DCreditApplicationDocuments poDao;

    private final RImageInfo poImage;

    //This variable stores transaction no of current initialize credit application
    private String TransNox;

    private String message;

    public CreditAppDocuments(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).DocumentInfoDao();
        this.poImage = new RImageInfo(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean InitializeApplicantDocuments(String args){
        try{
            TransNox = args;
            ECreditApplicationDocuments loDocs = poDao.GetCreditAppDocs(args);

            if(loDocs == null){
                poDao.insertDocumentByTransNox(args);
            } else {
                poDao.updateDocumentsInfos(args);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> GetApplicantDocumentsList(){
        return poDao.GetCreditAppDocuments(TransNox);
    }

    public String SaveDocumentInfo(CreditAppDocs args){
        try{
            String lsTransNo = args.getTransNox();
            String lsFileCde = args.getFileCode();
            String lsFileNme = args.getFileName();
            String lsFilePth = args.getFileLoct();
            String lsLatitde = args.getLatitude();
            String lsLongtde = args.getLongtude();

            String lsResult = poImage.SaveCreditAppDocumentsImage(lsTransNo, lsFileCde, lsFileNme, lsFilePth, lsLatitde, lsLongtde);

            if(lsResult == null){
                message = poImage.getMessage();
                return null;
            }

            poDao.updateDocumentsInfo(lsTransNo, lsFileCde);

            return lsResult;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadDocument(String args){
        try{
            if(!poImage.UploadImage(args)){
                message = poImage.getMessage();
                return false;
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
