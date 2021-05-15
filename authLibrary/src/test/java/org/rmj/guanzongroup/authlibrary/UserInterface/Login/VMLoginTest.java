/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 5/15/21 8:12 AM
 * project file last modified : 5/15/21 8:12 AM
 */

package org.rmj.guanzongroup.authlibrary.UserInterface.Login;

import android.os.Build;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.*;

public class VMLoginTest {

    @Mock
    HashMap<String, String> headers;

    @Mock
    WebApi webApi;

    @Mock
    JSONObject params;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        webApi = Mockito.mock(WebApi.class);
        headers = new HashMap<>();
        params = new JSONObject();
    }

    @Test
    public void login() throws Exception {
        String response = "";
        Calendar calendar = Calendar.getInstance();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "gRider");
        headers.put("g-api-client", "");
        headers.put("g-api-imei", "TestDevice");
        headers.put("g-api-model", Build.MODEL);
//        headers.put("g-api-mobile", lsMobileN);
        headers.put("g-api-mobile", "09260375777");
        headers.put("g-api-token", "eUah-8zMSGyzo1OGx-C_yy:APA91bHkcT2rDXlvMwUImyXhv4UOF0ArwdPsM7Fj_Y2mv4Hxg9eewH5F_HcDpRdV0zQ9wWxucvTCNct2Qp3hj5spDgfETiZCeyWCamXDTdy46qBLR5fjBGD-KAk9WR-ow-oj3EPluPEp");
        headers.put("g-api-user", "");
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-log", "");

        params.put("user", "mikegarcia8748@gmail.com");
        params.put("pswd", "12345678");

        response = WebClient.httpsPostJSon("https://restgk.guanzongroup.com.ph/security/mlogin.php", params.toString(), headers);
        assertNotNull(response);
    }
}