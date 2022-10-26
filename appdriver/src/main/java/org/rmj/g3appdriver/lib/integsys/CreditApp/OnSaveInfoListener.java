package org.rmj.g3appdriver.lib.integsys.CreditApp;

import android.content.Intent;

public interface OnSaveInfoListener {
    void OnSave(Intent intent);
    void OnFailed(String message);
}
