package org.rmj.guanzongroup.ghostrider.approvalcode.Etc;

public interface ViewModelCallback {
    void OnLoadData(String Title, String Message);
    void OnSuccessResult(String args);
    void OnFailedResult(String message);
}
