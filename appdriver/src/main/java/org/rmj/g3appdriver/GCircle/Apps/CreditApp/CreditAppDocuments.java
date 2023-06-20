package org.rmj.g3appdriver.GCircle.Apps.CreditApp;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.CreditAppDocs;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.GCircle.room.Repositories.RImageInfo;

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
        this.poDao = GGC_GCircleDB.getInstance(instance).DocumentInfoDao();
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
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> GetApplicantDocumentsList(String args){
        return poDao.GetCreditAppDocuments(args);
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
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean UploadDocument(String args){
        try{
            String lsImageID = poImage.UploadImage(args);
            if(lsImageID == null){
                message = poImage.getMessage();
                return false;
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
