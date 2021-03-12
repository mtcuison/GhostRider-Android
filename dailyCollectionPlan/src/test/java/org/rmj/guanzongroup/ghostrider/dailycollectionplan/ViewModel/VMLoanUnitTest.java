package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMLoanUnitTest {

    String TransNox, Remarks, luBDate, luPhune, luMobile, luEmail, luLName, luFName, luMiddName, luSuffix;
    String luIMGPath, luHouseNo, luStreet, luTown, luBrgy;
    String luBPlace, luCivil, luGender;
    String latt, longi;
    int EntryNox;
    LoanUnitModel infoModel;

    VMLoanUnit mViewModel;

    private List<EClientUpdate> clientData =  new ArrayList<>();
    private RDailyCollectionPlan poDcp;

    @Mock
    ViewModelCallback callback;
    @Mock
    RClientUpdate poClient;
    @Before
    public void setUp() throws Exception {
        EntryNox = 4;
        TransNox = "M00121000014";
        Remarks = "Loan Unit";
        luBDate = "03/06/1990";
        luFName = "Jonathan";
        luMiddName = "Tamayo";
        luLName = "Sabiniano";
        luSuffix = "Jr.";
        luHouseNo = "0627";
        luStreet = "Sitio Ampongan";
        luBrgy = "1200145";
        luTown = "0335";
        luBPlace = "0335";
        luGender = "0";
        luCivil = "0";
        luMobile = "09452086661";
        luPhune = "";
        latt = "16.0357708";
        longi = "120.3316509";
        luEmail = "jonsabiniano03@gmail.com";
        luIMGPath = "/storage/emulated/0/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/DCP/LUn/LUn_20210304_131840.jpeg";

//        mViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(VMLoanUnit.class);
        mViewModel = new VMLoanUnit(ApplicationProvider.getApplicationContext());

        infoModel = new LoanUnitModel();
        infoModel.setLuCivilStats(luCivil);
        infoModel.setLuBDate(luBDate);
        infoModel.setLuPhone(luPhune);
        infoModel.setLuMobile(luMobile);
        infoModel.setLuEmail(luEmail);
        infoModel.setLuLastName(luLName);
        infoModel.setLuFirstName(luFName);
        infoModel.setLuMiddleName(luMiddName);
        infoModel.setLuSuffix(luSuffix);
        infoModel.setLuImgPath(luIMGPath);
        //Address
        infoModel.setLuHouseNo("0627");
        infoModel.setLuStreet("Sitio Ampongan");
        infoModel.setLuTown("0335");
        infoModel.setLuBrgy("1200145");
        infoModel.setLuBPlace("0335");
        infoModel.setLuGender("0");

        mViewModel.setParameter(TransNox, EntryNox, Remarks);
        mViewModel.setGender("0");
        mViewModel.setImgName("LUn_20210304_131840.jpeg");
        mViewModel.setLatitude(latt);
        mViewModel.setLongitude(longi);

    }
    @After
    public void tearDown() throws Exception {
        infoModel = null;
        mViewModel = null;
    }

    @Test
    public void test_saveLuInfo() {
        Assert.assertTrue(mViewModel.saveLUnInfo(infoModel, callback));
    }

    @Test
    public void test_getInfoModels(){
        Assert.assertEquals(TransNox,mViewModel.getTransNox().getValue());
        Assert.assertEquals(luFName,infoModel.getLuFirstName());
        Assert.assertEquals(luMiddName,infoModel.getLuMiddleName());
        Assert.assertEquals(luLName,infoModel.getLuLastName());
        Assert.assertEquals(luSuffix,infoModel.getLuSuffix());
        Assert.assertEquals(luBDate,infoModel.getLuBDate());
        Assert.assertEquals(luBPlace,infoModel.getLuBPlace());

        Assert.assertEquals(luGender,infoModel.getLuGender());
        Assert.assertEquals(luCivil,infoModel.getLuCivilStats());
        Assert.assertEquals(luStreet,infoModel.getLuStreet());
        Assert.assertEquals(luHouseNo,infoModel.getLuHouseNo());
        Assert.assertEquals(luBrgy,infoModel.getLuBrgy());
        Assert.assertEquals(luTown,infoModel.getLuTown());
        Assert.assertEquals(luPhune,infoModel.getLuPhone());
        Assert.assertEquals(luMobile,infoModel.getLuMobile());
        Assert.assertEquals(luEmail,infoModel.getLuEmail());
        Assert.assertEquals(luIMGPath,infoModel.getLuImgPath());

        System.out.print("TransNox: " + mViewModel.getTransNox().getValue() + "\n");
        System.out.print("Firstname: " + infoModel.getLuFirstName() + "\n");
        System.out.print("Middle Name:" + infoModel.getLuMiddleName() + "\n");
        System.out.print("Lastname  "+  infoModel.getLuLastName() + "\n");
        System.out.print("Suffix " + infoModel.getLuSuffix() + "\n");
        System.out.print("BirthDate " + infoModel.getLuBDate() + "\n");
        System.out.print("BirthPlace " + infoModel.getLuBPlace() + "\n");
        System.out.print("Gender" + infoModel.getLuGender() + "\n");
        System.out.print("Civil" + infoModel.getLuCivilStats() + "\n");
        System.out.print("Street " + infoModel.getLuStreet() + "\n");
        System.out.print("HouseNo " + infoModel.getLuHouseNo() + "\n");
        System.out.print("Brgy " + infoModel.getLuBrgy() + "\n");
        System.out.print("Town " + infoModel.getLuTown() + "\n");
        System.out.print("Phune " + infoModel.getLuPhone() + "\n");
        System.out.print("Mobile " + infoModel.getLuMobile() + "\n");
        System.out.print("Email " + infoModel.getLuEmail() + "\n");
        System.out.print("IMGPath " + infoModel.getLuImgPath() + "\n");
        System.out.print("Latitude:  " + mViewModel.getLatitude().getValue() + "\n");
        System.out.print("Longitude:  " + mViewModel.getLongitude().getValue() + "\n\n");

    }
}