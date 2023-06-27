package org.rmj.g3appdriver.GCard;


import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.graphics.Bitmap;
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
import org.rmj.g3appdriver.GConnect.GCard.DigitalGcard.GCard;
import org.rmj.g3appdriver.GConnect.GCard.DigitalGcard.pojo.GcardCredentials;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestGCard {
    private static final String TAG = TestGCard.class.getSimpleName();

    private Application instance;

    private GCard poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new GCard(instance);
    }

    @Test
    public void test01ImportGCard() {
        if(poSys.ImportGcard()){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02AddNewGCardNumber() throws Exception{
        int lnResult = poSys.AddGcard(new GcardCredentials("4500204358401", "1962-01-01"));
        if(lnResult == 1){
            isSuccess = true;
        } else if(lnResult == 2){
            Thread.sleep(1000);
            Log.d(TAG, poSys.getMessage());
            if(poSys.ConfirmGCard(new GcardCredentials("4500204358401", "1962-01-01"))){
                isSuccess = true;
            } else {
                Log.e(TAG, poSys.getMessage());
            }
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test03GenerateGCardQrCode() {
        Bitmap loResult = poSys.GenerateGCardQrCode();

        if(loResult != null){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test04ParseQrCode() {
        String lsQrCode = "B3A89CE2F6A538BCF076887F2926078765E79DBBD014572F9BC3691AD4C7F92A99B97768F421FFA4C93EB518B1067A71BA8EACFE597E85CE778605EF84D524C396850E40D2AECBFFA90F1B369458A432275BF197EAE5362DFFA9070A84DFBE235E9F94F3E3A65E800C0F9149FA44119E3A50050EAFA3656345E76CA0CDD4D1BA";

        GCard.QrCodeType loResult = poSys.ParseQrCode(lsQrCode);
        if(loResult != null){
            switch (loResult){
                case NEW_GCARD:
                    String lsGCard = poSys.GetNewGCardNumber(lsQrCode);
                    if(poSys.AddGCard(lsGCard)){
                        isSuccess = true;
                        break;
                    }

                    Log.e(TAG, poSys.getMessage());
                    break;
                default:
                    String lsResult = poSys.GetTransactionPIN(lsQrCode);
                    if(lsResult != null){
                        isSuccess = true;
                        break;
                    }

                    Log.e(TAG, poSys.getMessage());
                    break;
            }
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test05GetActiveGCard() {
        poSys.GetActiveGCardInfo().observeForever(eGcardApp -> {
            if(eGcardApp == null){
                Log.d(TAG, "No GCard detected.");
                return;
            }
            isSuccess = true;
        });

        assertTrue(isSuccess);
        isSuccess = false;
    }
}
