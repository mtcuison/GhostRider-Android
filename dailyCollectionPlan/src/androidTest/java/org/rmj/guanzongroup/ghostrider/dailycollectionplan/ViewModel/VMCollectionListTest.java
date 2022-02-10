package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class VMCollectionListTest {


    private Context context;
    private HttpHeaders poHeaders;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        poHeaders = HttpHeaders.getInstance(context);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void postLRCollectionDetail() throws Exception {
        String lsResult = "";
        JSONObject loJson = new JSONObject();
        loJson.put("sTransNox", "M08921000137");

        String lsDateTme = SQLUtil.dateFormat(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", "gRider");
        headers.put("g-api-client", "GGC_BM001");
        headers.put("g-api-imei", "e2be38386f093d59");
        headers.put("g-api-model", "sdk_gphone_x86");
        headers.put("g-api-mobile", "09171870011");
        headers.put("g-api-token", "djPc_1YnRjyqEqd4y9Zn8h:APA91bGNp_HKpAEicuEn8S03v272fH1fEoZKy-a5uAWKp3NdIvZDuEbTbY-G1-HSoyed3sHfE8x2vlq0XFpSjQiQIjtnum5ydmMmnpykEm_R0TRxMoMLqBGQl3oH-vFA6jap26jZRgJn");
        headers.put("g-api-user", "GAP021002961");
        headers.put("g-api-key", lsDateTme);
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-log", "GAP021058075");

        String lsResponse1 = WebClient.sendRequest(WebApi.URL_POST_DCP_MASTER, loJson.toString(), headers);
        if (lsResponse1 == null) {
            lsResult = "error";
//            lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Server no response on posting DCP master detail. Tap 'Okay' to create dcp file for backup");
        } else {
            JSONObject loResponse = new JSONObject(lsResponse1);
            String result = loResponse.getString("result");
            if (result.equalsIgnoreCase("success")) {
//                poDcp.updateSentPostedDCPMaster(laCollDetl.get(0).sTransNox);
//                lsResult = loResponse.toString();
                lsResult = "success";
            } else {
                JSONObject loError = loResponse.getJSONObject("error");
//                lsResult = loError.toString();
                lsResult = "error";
            }
        }
        assertEquals("success", lsResult);
    }
}