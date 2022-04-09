package org.rmj.g3appdriver.lib.BullsEye;

import static org.junit.Assert.*;

import android.app.Application;

import androidx.annotation.UiThread;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.account.AccountAuthentication;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class RandomStockInventoryManagerTest {
    private static final String TAG = RandomStockInventoryManagerTest.class.getSimpleName();

    private Application instance;
    private AccountAuthentication poAuth;
    private RandomStockInventoryManager poRSI;

    private static boolean isSuccess = false;
    private static String psTransNo;
    List<EInventoryDetail> loDetails;

//    @Rule
//    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poAuth = new AccountAuthentication(instance);
        poRSI = new RandomStockInventoryManager(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01LoginAccount() throws Exception{
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
    public void test02SelfieLog() throws Exception{
        isSuccess = new SelfieLog(instance).SaveSelfieLog("M001", "121.0", "27.0", "Sample", "Sample");
        assertTrue(isSuccess);
    }

    @Test
    public void test03DownloadInventory() throws Exception{
        isSuccess = poRSI.RequestInventoryItems();
        assertTrue(isSuccess);
    }

    @Test @UiThread
    public void test04Sample() throws Exception{
        loDetails = poRSI.getInventoryDetailForLastLog();
        for(int x = 0; x < loDetails.size(); x++){
            psTransNo = loDetails.get(x).getTransNox();
            String BarCode = loDetails.get(x).getBarrCode();
            String PartID = loDetails.get(x).getPartsIDx();
            String ActualQty = "7";
            String Remarks = "Sample " + x;
            poRSI.UpdateInventoryItem(psTransNo,
                    BarCode,
                    PartID,
                    ActualQty,
                    Remarks);
        }
        assertTrue(loDetails.size() > 0);
    }

    @Test
    public void test05PostInventoryItems() throws Exception{
        isSuccess = poRSI.PostInventory(psTransNo, "Sample");
        assertTrue(isSuccess);
    }

//    @Test
    public void test06PostInventory() throws Exception{
    }
}