/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 10/22/21, 3:14 PM
 * project file last modified : 10/22/21, 3:14 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.lib.Etc.Branch;
import org.rmj.g3appdriver.GCircle.room.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.DcpManager;

import java.util.List;

public class VMPostDcp extends AndroidViewModel {
    private static final String TAG = VMPostDcp.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDcpRepo;
    private final Branch poBranchx;

    public VMPostDcp(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcpRepo = new RDailyCollectionPlan(instance);
        this.poBranchx = new Branch(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranchx.getUserBranchInfo();
    }

    public LiveData<List<EDCPCollectionDetail>> getUnpostedCollectionList() {
        return poDcpRepo.getCollectionDetailLog();
    }

    public void PostLRDCPCollection(OnPostCollection foCallBck) {
        new PostLRDCPCollectionTask(instance, foCallBck).execute();
    }

    public void PostLRDCPTransaction(EDCPCollectionDetail foDcpInfo, OnPostCollection foCallBck) {
        new PostLRDCPTransactionTask(instance, foCallBck).execute(foDcpInfo);
    }

    /*private static class PostLRDCPCollectionTask extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnPostCollection poCallBck;
        private boolean isSuccess = false;

        private PostLRDCPCollectionTask(Application foAppsxxx, OnPostCollection foCallBck) {
            this.poConnect = new ConnectionUtil(foAppsxxx);
            this.poDcpMngr = new DcpManager(foAppsxxx);
            this.poCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallBck.onLoading();
        }

        @Override
        protected String doInBackground(Void... voids) {
            final String[] lsResult = {""};

            try {
                if(poConnect.isDeviceConnected()) {
                    poDcpMngr.PostLRDCPCollection(new DcpManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            isSuccess = true;
                            lsResult[0] = args;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResult[0] = message;
                        }
                    });
                    poDcpMngr.PostDcpMaster(new DcpManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {

                        }

                        @Override
                        public void OnFailed(String message) {

                        }
                    });
                } else {
                    lsResult[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResult[0] = e.getMessage();
            }

            return lsResult[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                poCallBck.onSuccess(s);
            } else {
                poCallBck.onFailed(s);
            }
        }

    }*/
    private static class PostLRDCPCollectionTask{
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnPostCollection poCallBck;
        private boolean isSuccess = false;

        private PostLRDCPCollectionTask(Application foAppsxxx, OnPostCollection foCallBck) {
            this.poConnect = new ConnectionUtil(foAppsxxx);
            this.poDcpMngr = new DcpManager(foAppsxxx);
            this.poCallBck = foCallBck;
        }
        public void execute(){
            TaskExecutor.Execute(null, new OnTaskExecuteListener() {
                @Override
                public void OnPreExecute() {
                    poCallBck.onLoading();
                }

                @Override
                public Object DoInBackground(Object args) {
                    final String[] lsResult = {""};

                    try {
                        if(poConnect.isDeviceConnected()) {
                            poDcpMngr.PostLRDCPCollection(new DcpManager.OnActionCallback() {
                                @Override
                                public void OnSuccess(String args) {
                                    isSuccess = true;
                                    lsResult[0] = args;
                                }
                                @Override
                                public void OnFailed(String message) {
                                    lsResult[0] = message;
                                }
                            });
                            poDcpMngr.PostDcpMaster(new DcpManager.OnActionCallback() {
                                @Override
                                public void OnSuccess(String args) {

                                }
                                @Override
                                public void OnFailed(String message) {

                                }
                            });
                        } else {
                            lsResult[0] = AppConstants.SERVER_NO_RESPONSE();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        lsResult[0] = e.getMessage();
                    }

                    return lsResult[0];
                }

                @Override
                public void OnPostExecute(Object object) {
                    if(isSuccess) {
                        poCallBck.onSuccess((String) object);
                    } else {
                        poCallBck.onFailed((String) object);
                    }
                }
            });
        }
    }

    /*private static class PostLRDCPTransactionTask extends AsyncTask<EDCPCollectionDetail, Void, String> {
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnPostCollection poCallBck;
        private boolean isSuccess = false;

        private PostLRDCPTransactionTask(Application foAppsxxx, OnPostCollection foCallBck) {
            this.poConnect = new ConnectionUtil(foAppsxxx);
            this.poDcpMngr = new DcpManager(foAppsxxx);
            this.poCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallBck.onLoading();
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... edcpCollectionDetails) {
            EDCPCollectionDetail loDcpInfo = edcpCollectionDetails[0];
            final String[] lsResultx = {""};

            try {
                if(poConnect.isDeviceConnected()) {
                    poDcpMngr.PostLRDCPTransaction(loDcpInfo.getTransNox(), loDcpInfo.getAcctNmbr(),
                            new DcpManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            isSuccess = true;
                            lsResultx[0] = args;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResultx[0] = message;
                        }
                    });
                } else {
                    lsResultx[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResultx[0] = e.getMessage();
            }

            return lsResultx[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                poCallBck.onSuccess(s);
            } else {
                poCallBck.onFailed(s);
            }
        }

    }*/
    private static class PostLRDCPTransactionTask{
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnPostCollection poCallBck;
        private boolean isSuccess = false;

        private PostLRDCPTransactionTask(Application foAppsxxx, OnPostCollection foCallBck) {
            this.poConnect = new ConnectionUtil(foAppsxxx);
            this.poDcpMngr = new DcpManager(foAppsxxx);
            this.poCallBck = foCallBck;
        }
        public void execute(EDCPCollectionDetail foDcpInfo){
            TaskExecutor.Execute(foDcpInfo, new OnTaskExecuteListener() {
                @Override
                public void OnPreExecute() {
                    poCallBck.onLoading();
                }

                @Override
                public Object DoInBackground(Object args) {
                    EDCPCollectionDetail loDcpInfo = (EDCPCollectionDetail) args;
                    final String[] lsResultx = {""};

                    try {
                        if(poConnect.isDeviceConnected()) {
                            poDcpMngr.PostLRDCPTransaction(loDcpInfo.getTransNox(), loDcpInfo.getAcctNmbr(),
                                    new DcpManager.OnActionCallback() {
                                        @Override
                                        public void OnSuccess(String args) {
                                            isSuccess = true;
                                            lsResultx[0] = args;
                                        }

                                        @Override
                                        public void OnFailed(String message) {
                                            lsResultx[0] = message;
                                        }
                                    });
                        } else {
                            lsResultx[0] = AppConstants.SERVER_NO_RESPONSE();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        lsResultx[0] = e.getMessage();
                    }
                    return lsResultx[0];
                }

                @Override
                public void OnPostExecute(Object object) {
                    if(isSuccess) {
                        poCallBck.onSuccess((String) object);
                    } else {
                        poCallBck.onFailed((String) object);
                    }
                }
            });
        }
    }

    public interface OnPostCollection {
        void onLoading();
        void onSuccess(String fsMessage);
        void onFailed(String fsMessage);
    }

}
