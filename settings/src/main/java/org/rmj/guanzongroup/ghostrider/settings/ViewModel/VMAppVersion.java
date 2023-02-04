package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import java.util.List;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.Version.AppVersion;
import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.g3appdriver.utils.ConnectionUtil;


public class VMAppVersion extends AndroidViewModel {

    private AppVersion poSys;
    private ConnectionUtil poconn;

    public interface onDownloadVersionList{
        void onDownload();
        void onSuccess(List<VersionInfo> list);
        void onFailed(String message);
    }

    public VMAppVersion(@NonNull Application application) {
        super(application);
        poSys = new AppVersion(application);
        poconn = new ConnectionUtil(application);
    }
    public void getVersionList(onDownloadVersionList listener){
        new getVersionTask(listener).execute();
    }
    private class getVersionTask extends AsyncTask<Void, Void, List<VersionInfo>>{
        private final onDownloadVersionList listener;
        private String message;
        private getVersionTask(onDownloadVersionList listener) {
            this.listener = listener;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.onDownload();
        }
        @Override
        protected List<VersionInfo> doInBackground(Void... voids) {
            try {
                if(!poconn.isDeviceConnected()){
                    message = poconn.getMessage();
                    return null;
                }

                List<VersionInfo> list = poSys.GetVersionInfo();

                if(list == null){
                    message = poSys.getMessage();
                    return null;
                }

                return list;
            }catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }
        @Override
        protected void onPostExecute(List<VersionInfo> versionInfos) {
            super.onPostExecute(versionInfos);
            if(versionInfos == null){
                listener.onFailed(message);
            }else {
                listener.onSuccess(versionInfos);
            }
        }
    }
}
