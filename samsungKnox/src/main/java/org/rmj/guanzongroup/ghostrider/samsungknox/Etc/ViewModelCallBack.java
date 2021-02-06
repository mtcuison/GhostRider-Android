package org.rmj.guanzongroup.ghostrider.samsungknox.Etc;

public interface ViewModelCallBack {
    void OnLoadRequest(String Title, String Message, boolean Cancellable);
    void OnRequestSuccess(String args);
    void OnRequestFailed(String message);
}
