package org.rmj.g3appdriver.lib.telecom;

import org.rmj.g3appdriver.lib.telecom.model.KnoxParams;

public interface SamsungKnox {
    String ExecuteRequest(KnoxParams args);
    String getMessage();
}
