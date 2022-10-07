/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

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
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DownloadImageCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMApplicationHome extends AndroidViewModel {
    private static final String TAG = VMApplicationHome.class.getSimpleName();
    private final Application instance;
    private final RCreditApplication poCreditApp;
    private final SessionManager poUser;
    public VMApplicationHome(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCreditApp = new RCreditApplication(application);
        this.poUser = new SessionManager(application);
    }
    public LiveData<List<ECreditApplicantInfo>> getAllCreditApp(){
        return poCreditApp.getAllCreditApp();
    }
}
