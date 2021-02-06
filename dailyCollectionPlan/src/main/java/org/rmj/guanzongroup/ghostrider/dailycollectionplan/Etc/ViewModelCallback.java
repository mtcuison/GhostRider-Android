package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

public interface ViewModelCallback {
    void OnLoadRequest(String Title, String Message, boolean Cancellable);
    void OnRequestSuccess(String args);
    void OnRequestFailed(String message);
}
