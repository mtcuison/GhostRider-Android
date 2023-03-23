package org.rmj.g3appdriver.lib.BullsEye;

public interface OnImportPerformanceListener {

    void OnImport();
    void OnSuccess();
    void OnFailed(String message);
}
