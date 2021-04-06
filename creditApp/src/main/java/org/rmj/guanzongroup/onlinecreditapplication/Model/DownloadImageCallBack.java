package org.rmj.guanzongroup.onlinecreditapplication.Model;

public interface DownloadImageCallBack {
    void OnStartSaving();
    void onSaveSuccessResult(String args);
    void onFailedResult(String message);
}
