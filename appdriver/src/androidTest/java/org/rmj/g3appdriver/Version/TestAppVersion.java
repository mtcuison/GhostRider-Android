package org.rmj.g3appdriver.Version;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Version.AppVersion;
import org.rmj.g3appdriver.lib.Version.VersionInfo;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestAppVersion {
    private static final String TAG = TestAppVersion.class.getSimpleName();

    private Application instance;

    private EmployeeMaster poMaster;

    private AppVersion poVersion;

    private boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
        poVersion = new AppVersion(instance);
        poMaster = new EmployeeMaster(instance);
    }

    @Test
    public void test01LoginAccount() throws Exception {
        if(poMaster.AuthenticateUser(new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"))){
            isSuccess = true;
        } else {
            Log.e(TAG, poMaster.getMessage());
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
        isSuccess = false;
    }

    @Test
    public void test02SubmitAppVersion() throws Exception {
        if(poVersion.SubmitUserAppVersion()){
            isSuccess = true;
        } else {
            Log.e(TAG, poVersion.getMessage());
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
        isSuccess = false;
    }

    @Test
    public void test03RetrieveUpdates() throws Exception{
        List<VersionInfo> loResult = poVersion.GetVersionInfo();

        if(loResult != null){
            isSuccess = true;
        } else {
            Log.e(TAG, poVersion.getMessage());
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
        isSuccess = false;
    }

    @Test
    public void test04PreviewUpdates() throws Exception{
        List<VersionInfo> loResult = poVersion.GetVersionInfo();

        try {
            for (int x = 0; x < loResult.size(); x++) {
                VersionInfo loInfo = loResult.get(x);
                Log.d(TAG, "Version Code: " + loInfo.getsVrsionCd());
                Log.d(TAG, "Version Name: " + loInfo.getsVrsionNm());

                if (loInfo.hasNewUpdate()) {
                    for (int i = 0; i < loInfo.getNewFeatures().size(); i++) {
                        VersionInfo.NewFeature loFeature = loInfo.getNewFeatures().get(i);
                        Log.d(TAG, "New Feature: " + loFeature.getsFeaturex());
                        Log.d(TAG, "Description: " + loFeature.getsDescript());
                    }
                }

                if (loInfo.hasFixes()) {
                    for (int i = 0; i < loInfo.getOthers().size(); i++) {
                        String lsFixes = loInfo.getOthers().get(i);
                        Log.d(TAG, "Fixed: " + lsFixes);
                    }
                }
            }
            isSuccess = true;
        } catch (Exception e){
            e.printStackTrace();
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
        isSuccess = false;
    }

    @Test
    public void test05CheckUpdate() throws Exception{
        VersionInfo loResult = poVersion.CheckUpdate();

        if(loResult != null){
            isSuccess = true;
        } else {
            Log.e(TAG, poVersion.getMessage());
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
        isSuccess = false;
    }
}
