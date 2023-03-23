package org.rmj.g3appdriver.lib.PetManager;

public interface OnCheckEmployeeApplicationListener {
    void OnCheck();
    void OnSuccess();
    void OnFailed(String message);
}
