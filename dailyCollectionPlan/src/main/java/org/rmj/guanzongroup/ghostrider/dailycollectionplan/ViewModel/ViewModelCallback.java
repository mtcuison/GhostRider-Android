package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

public interface ViewModelCallback {
    void OnStartSaving();
    void OnSuccessResult(String[] args);
    void OnFailedResult(String message);
}
