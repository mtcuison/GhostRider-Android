package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

public interface OnDownloadApplicationsListener {
    void OnDownload(String title, String message);
    void OnSuccess(String message);
    void OnFailed(String message);
}
