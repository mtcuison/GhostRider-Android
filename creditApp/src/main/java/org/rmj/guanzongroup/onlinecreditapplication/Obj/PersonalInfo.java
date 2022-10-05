package org.rmj.guanzongroup.onlinecreditapplication.Obj;

import org.json.JSONObject;
import org.rmj.gocas.pojo.AddressInfo;

public class PersonalInfo {

    private static final String TAG = PersonalInfo.class.getSimpleName();

    private JSONObject params = new JSONObject();

    private final AddressInfo poAddxx = new AddressInfo();

    private String message;

    public PersonalInfo(){
        try {
            params.put("stxtLastNm", "");
            params.put("stxtFrstNm", "");
            params.put("stxtMiddNm", "");
            params.put("stxtSuffixx", "");
            params.put("stxtNickNm", "");
            params.put("stxtBirthDt", "");
            params.put("stxtProvince", "");
            params.put("stxtTown", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
