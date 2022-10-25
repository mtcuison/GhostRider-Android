package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.content.Intent;

public interface OnSaveInfoListener {
    void OnSave(Intent intent);
    void OnFailed(String message);
}
