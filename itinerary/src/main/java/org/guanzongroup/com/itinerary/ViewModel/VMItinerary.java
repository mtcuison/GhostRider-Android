package org.guanzongroup.com.itinerary.ViewModel;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.Itinerary.Obj.EmployeeItinerary;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EItinerary;
import org.rmj.g3appdriver.lib.Branch.Branch;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMItinerary extends AndroidViewModel {
    private static final String TAG = VMItinerary.class.getSimpleName();

    private final Application instance;
    private final EmployeeItinerary poSys;
    private final Branch poBranch;
    private final EmployeeMaster poUser;
    private final ConnectionUtil poConn;
    private String message;

    public interface OnActionCallback {
        void OnLoad(String title, String message);

        void OnSuccess(String args);

        void OnFailed(String message);
    }

    public interface OnImportUsersListener {
        void OnImport(String title, String message);

        void OnSuccess(List<JSONObject> args);

        void OnFailed(String message);
    }

    public VMItinerary(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new EmployeeItinerary(instance);
        this.poBranch = new Branch(instance);
        this.poUser = new EmployeeMaster(instance);
        this.poConn = new ConnectionUtil(instance);
    }

    public LiveData<List<EItinerary>> GetItineraryListForCurrentDay() {
        return poSys.GetItineraryListForCurrentDay();
    }

    public LiveData<List<EItinerary>> GetItineraryForFilteredDate(String fsArgs1, String fsArgs2) {
        return poSys.GetItineraryListForFilteredDate(fsArgs1, fsArgs2);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo() {
        return poUser.GetEmployeeBranch();
    }

    public void SaveItinerary(EmployeeItinerary.ItineraryEntry foVal, OnActionCallback callback) {
        TaskExecutor.Execute(foVal, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnLoad("Employee Itinerary", "Saving entry. Please wait...");
            }

            @Override
            public Object DoInBackground(Object args) {
                try {
                    EmployeeItinerary.ItineraryEntry loVal = (EmployeeItinerary.ItineraryEntry) args;
                    String lsResult = poSys.SaveItinerary(loVal);
                    if (lsResult == null) {
                        Log.e(TAG, "Unable to save itinerary. Message: " + poSys.getMessage());
                        message = poSys.getMessage();
                        return false;
                    }

                    Log.d(TAG, "Data for upload transaction no. : " + lsResult);
                    if (!poConn.isDeviceConnected()) {
                        Log.e(TAG, "Unable to connect to upload entry.");
                        message = "Your entry has been save locally and will be uploaded later for if device has reconnected.";
                        return true;
                    }

                    if (!poSys.UploadItinerary(lsResult)) {
                        message = poSys.getMessage();
                        return false;
                    } else {
                        message = "Your entry has been save. You may now download the itinerary to other device.";
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message = getLocalMessage(e);
                    return false;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean lsResult = (Boolean) object;
                if (lsResult) {
                    callback.OnSuccess(message);
                } else {
                    callback.OnFailed(message);
                }
            }
        });
    }

//

    public void DownloadItinerary(String args1, String args2, OnActionCallback callback) {
        TaskExecutor.Execute(null, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnLoad("Employee Itinerary", "Downloading entries. Please wait...");
            }

            @Override
            public Object DoInBackground(Object args) {

                try {
                    if (!poConn.isDeviceConnected()) {
                        Log.e(TAG, "Unable to connect to download entries.");
                        message = "Unable to connect. Please check internet connectivity";
                        return true;
                    } else {
                        Log.d(TAG, "Argument 1: " + args1);
                        Log.d(TAG, "Argument 2: " + args2);
                        boolean isDownloaded = poSys.DownloadItinerary(
                                args1
                                , args2);
                        if (!isDownloaded) {
                            message = poSys.getMessage();
                            return false;
                        } else {
                            message = "Itinerary entries downloaded successfully";
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message = getLocalMessage(e);
                    return false;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean lsResult = (Boolean) object;
                if (lsResult) {
                    callback.OnSuccess(message);
                } else {
                    callback.OnFailed(message);
                }
            }
        });
    }


    public void ImportUsers(OnImportUsersListener listener) {
        TaskExecutor.Execute(null, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                listener.OnImport("Employee Itinerary", "Importing employee details. Please wait...");
            }

            @Override
            public Object DoInBackground(Object args) {
                try {
                    if (!poConn.isDeviceConnected()) {
                        message = poConn.getMessage();
                        return null;
                    }

                    List<JSONObject> loList = poSys.GetEmployeeList();
                    if (loList == null) {
                        message = poSys.getMessage();
                        return null;
                    }

                    return loList;
                } catch (Exception e) {
                    e.printStackTrace();
                    message = getLocalMessage(e);
                    return null;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                List<JSONObject> lsArgs = (List<JSONObject>) object;
                if (lsArgs == null) {
                    listener.OnFailed(message);
                } else {
                    listener.OnSuccess(lsArgs);
                }
            }
        });
    }
}