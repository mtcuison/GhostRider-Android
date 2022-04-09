package org.rmj.g3appdriver.APITest;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.account.AccountAuthentication;
import org.rmj.g3appdriver.utils.WebApi;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class RequestNotificationsAPITest {
    private static final String TAG = RequestNotificationsAPITest.class.getSimpleName();

    private Application instance;
    private AccountAuthentication poAuth;

    private static boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poAuth = new AccountAuthentication(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01Login(){
        poAuth.LoginAccount("mikegarcia8748@gmail.com", "123456", "09171870011", new AccountAuthentication.OnActionCallback() {
            @Override
            public void OnSuccess(String message) {
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                isSuccess = false;
            }
        });
        assertTrue(isSuccess);
    }

    @Test
    public void test02DownloadNotifications() throws Exception{
        JSONObject params = new JSONObject();
        params.put("nLimitxxx", "10");
        String lsResponse = WebClient.sendRequest(
                new WebApi(true).getUrlRequestPreviousNotifications(),
                params.toString(),
                HttpHeaders.getInstance(instance).getHeaders());
        Log.e(TAG, lsResponse);
        if(lsResponse == null){

        } else {

        }
        assertTrue(isSuccess);
    }
}
