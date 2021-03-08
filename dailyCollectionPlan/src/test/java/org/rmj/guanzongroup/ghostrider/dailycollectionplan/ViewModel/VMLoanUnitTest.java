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
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMLoanUnitTest {

    String TransNox, Remarks;
    int EntryNox;
    LoanUnitModel infoModel;

    VMLoanUnit mViewModel;

    private RDailyCollectionPlan poDcp;
    @Mock
    ViewModelCallback callback;

    @Before
    public void setUp() throws Exception {
        EntryNox = 4;
        TransNox = "M00121000014";
        Remarks = "Loan Unit";
//        mViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(VMLoanUnit.class);
        mViewModel = new VMLoanUnit(ApplicationProvider.getApplicationContext());

        infoModel = new LoanUnitModel();
        infoModel.setLuCivilStats("0");
        infoModel.setLuBDate("03/06/1990");
        infoModel.setLuPhone("");
        infoModel.setLuMobile("09452086661");
        infoModel.setLuEmail("jonsabiniano03@gmail.com");
        infoModel.setLuLastName("Sabiniano");
        infoModel.setLuFirstName("Jonathan");
        infoModel.setLuMiddleName("Tamayo");
        infoModel.setLuSuffix("");
        infoModel.setLuImgPath("/storage/emulated/0/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/DCP/LUn/LUn_20210304_131840.jpeg");
        //Address
        infoModel.setLuHouseNo("627");
        infoModel.setLuStreet("Ampongan");
        infoModel.setLuTown("0335");
        infoModel.setLuBrgy("1200145");
        infoModel.setLuBPlace("0335");

        mViewModel.setParameter(TransNox, EntryNox, Remarks);
        mViewModel.setGender("0");
        mViewModel.setImgName("LUn_20210304_131840.jpeg");
        mViewModel.setLatitude("16.0357497");
        mViewModel.setLongitude("120.331627");

//        mViewModel.getCollectionDetail().observeForever(collectionDetail ->{
//            Log.e("CollectionDetail ", String.valueOf(collectionDetail));
////            mViewModel.setCurrentCollectionDetail(collectionDetail);
//                });

    }
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_saveLuInfo() {

//        doReturn(true).when(mViewModel).saveLuInfo(infoModel, callback);
        Assert.assertEquals(true, mViewModel.saveLUnInfo(infoModel, callback));
    }

}