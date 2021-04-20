package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMPromiseToPayTest  {

    private String brnCode, collectName, date, remarks, appUnit,latt,longi;
    private String emptyBrnCode, imgName,imgFileLoc;
    private String sTransNox;
    private String sSourceNo;
    private String sSoucrceCD;
    private String sDtlSrcNo;
    private String sFileCode;
    private String sMD5Hashx;
    private char sSendStat;
    private String sSendDate;
    PromiseToPayModel infoModels;
    VMPromiseToPay mViewModel;
    private EImageInfo poImageInfo;
    @Mock
    ViewModelCallback callback;
    @Before
    public void setUp() throws Exception {
        mViewModel = new VMPromiseToPay(ApplicationProvider.getApplicationContext());
        infoModels = new PromiseToPayModel();
        poImageInfo = new EImageInfo();
        date = "February 20, 2021";
        collectName = "Sabiniano, Jonathan";

        latt = "16.0357708";
        longi = "120.3316509";
        emptyBrnCode = "";
        appUnit = "0";
        remarks = "Promise to Pay";
        if (Integer.parseInt(appUnit) < 1){
            brnCode = "M042";
        }else{
            brnCode = "";
        }
        emptyBrnCode = brnCode;
        imgName = "PTP_20210311_105137.jpeg";
        imgFileLoc = "/storage/emulated/0/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/DCP/PTP/PTP_20210311_105137.jpeg";
        sTransNox = "MX01210000000280";
        sSourceNo = "M00121000040";
        sSoucrceCD = "DCPa";
        sDtlSrcNo = "M001170119";
        sFileCode = "0020";
        sMD5Hashx ="707b6b391be5c5605ae5b954090d9e9e";
        sSendStat = '1';
        sSendDate = "2021-03-11 13:13:06";
        infoModels.setPtpBranch(emptyBrnCode);
        infoModels.setPtpDate(date);
        infoModels.setPtpCollectorName(collectName);
        infoModels.setPtpAppointmentUnit(appUnit);
        infoModels.setPtpRemarks(remarks);
        mViewModel.setImgName(imgName);
        mViewModel.setLatitude(latt);
        mViewModel.setLongitude(longi);
        infoModels.setPtpImgPath(imgFileLoc);

    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_getInfoModels(){

        assertEquals(date, infoModels.getPtpDate());
        assertEquals(collectName, infoModels.getPtpCollectorName());
        assertEquals(emptyBrnCode, infoModels.getPtpBranch());
        assertEquals(appUnit, infoModels.getPtpAppointmentUnit());
        assertEquals(remarks, infoModels.getPtpRemarks());

        System.out.print("Branch code " + infoModels.getPtpBranch() + "\n");
        System.out.print("Application Unit " + infoModels.getPtpAppointmentUnit() + "\n");
        System.out.print("Colletion Name " + infoModels.getPtpCollectorName() + "\n");
        System.out.print("Promise Date " + infoModels.getPtpDate() + "\n");
        System.out.print("Remarks " + infoModels.getPtpRemarks() + "\n");
    }
    @Test
    public void savePtpInfo() {

            Assert.assertTrue(mViewModel.savePtpInfo(infoModels, callback));
            if (mViewModel.savePtpInfo(infoModels, callback)){
                System.out.print("save success" + "\n");
            }else{
                System.out.print("save error " + "\n");
            }
    }


}