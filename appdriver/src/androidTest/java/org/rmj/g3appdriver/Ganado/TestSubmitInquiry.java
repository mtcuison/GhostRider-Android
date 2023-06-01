package org.rmj.g3appdriver.Ganado;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.lib.Ganado.Obj.Ganado;
import org.rmj.g3appdriver.lib.Ganado.pojo.ClientInfo;
import org.rmj.g3appdriver.lib.Ganado.pojo.InquiryInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestSubmitInquiry {
    private static final String TAG = TestSubmitInquiry.class.getSimpleName();

    private Application instance;
    private Ganado poSys;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new Ganado(instance);
    }

    @Test
    public void test01CreateInquiry() {
        boolean isSuccess = false;
        InquiryInfo loInfo = new InquiryInfo();
        loInfo.setBrandIDx("M0W1001");
        loInfo.setModelIDx("M00117046");
        loInfo.setGanadoTp("0");
        loInfo.setTargetxx("2023-05-31");
        loInfo.setDownPaym("50000");
        loInfo.setColorIDx("M001003");
        loInfo.setPaymForm("1");
        loInfo.setRelatnID("00");
        loInfo.setTermIDxx("36");
        String lsResult = poSys.CreateInquiry(loInfo);

        if(lsResult == null){
            Log.d(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02PersonalInfo() {
        boolean isSuccess = false;
        InquiryInfo loInfo = new InquiryInfo();
        loInfo.setBrandIDx("M0W1001");
        loInfo.setModelIDx("M00117046");
        loInfo.setGanadoTp("0");
        loInfo.setTargetxx("2023-05-31");
        loInfo.setDownPaym("50000");
        loInfo.setColorIDx("M001003");
        loInfo.setPaymForm("1");
        loInfo.setRelatnID("00");
        loInfo.setTermIDxx("36");
        String lsResult = poSys.CreateInquiry(loInfo);

        if(lsResult == null){
            Log.d(TAG, poSys.getMessage());
            return;
        } else {
            isSuccess = true;
        }

        ClientInfo loClient = new ClientInfo();
        loClient.setsTransNox(lsResult);
        loClient.setLastName("Garcia");
        loClient.setFrstName("Michael");
        loClient.setMiddName("Permison");
        loClient.setGenderCd("0");
        loClient.setBirthDte("1996-11-26");
        loClient.setBirthPlc("0346");
        loClient.setHouseNox("123");
        loClient.setAddressx("Sitio Tawi-Tawi");
        loClient.setTownIDxx("0346");
        loClient.setMobileNo("09123456789");
        loClient.setEmailAdd("sampleEmail@domain.com");

        if(poSys.SaveClientInfo(loClient)){

            if(poSys.SaveInquiry(lsResult)){
                isSuccess = true;
            } else {
                Log.e(TAG, poSys.getMessage());
            }
        } else {
            Log.e(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
    }
}
