package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.apprdiver.util.WebFile;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMCIResidenceInfo  extends AndroidViewModel {
    private static final String TAG = VMCIResidenceInfo.class.getSimpleName();
    private final Application instance;
    private final RCreditApplication poCreditApp;
    private final Import_LoanApplications poImport;
    private final RImageInfo poImage;
    private final SessionManager poUser;
    public VMCIResidenceInfo(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCreditApp = new RCreditApplication(application);
        this.poImport = new Import_LoanApplications(application);
        this.poImage = new RImageInfo(application);
        this.poUser = new SessionManager(application);
    }
    public interface OnImportCallBack{
        void onStartImport();
        void onSuccessImport();
        void onImportFailed(String message);
    }

}