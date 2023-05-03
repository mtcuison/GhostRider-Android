package org.rmj.g3appdriver.Panalo;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.graphics.Bitmap;
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
import org.rmj.g3appdriver.lib.Panalo.Obj.GPanalo;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class GPanaloTest {
    private static final String TAG = GPanaloTest.class.getSimpleName();

    private Application instance;

    private EmployeeMaster poUser;
    private GPanalo poSys;

    private boolean isSuccess = false;

    @Before
    public void setup() throws Exception{
        instance = ApplicationProvider.getApplicationContext();
        poUser = new EmployeeMaster(instance);
        poSys = new GPanalo(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);

        EmployeeMaster.UserAuthInfo loAuth = new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
        assertTrue(poUser.AuthenticateUser(loAuth));
    }

    @Test
    public void test01ImportForClaimPanaloRewards() throws Exception{
        List<PanaloRewards> loList = poSys.GetRewards("0");
        if(loList != null){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02ImportRedeemedPanaloRewards() throws Exception{
        List<PanaloRewards> loList = poSys.GetRewards("1");
        if(loList != null){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test03ViewPanalReward() {
        List<PanaloRewards> loList = poSys.GetRewards("0");
        if(loList != null){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        Bitmap loBmp = poSys.RedeemReward(loList.get(0));

        if(loBmp != null){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }
}
