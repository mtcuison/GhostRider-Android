package org.rmj.guanzongroup.ghostrider.griderscanner.viewModel;

public interface ViewModelCallBack {
    void OnStartSaving();
    void onSaveSuccessResult(String args);
    void onFailedResult(String message);

    void OnSuccessResult(String[] strings);

    void OnFailedResult(String message);
}
