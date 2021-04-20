package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseSelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseSelfEmployedInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE)
public class Fragment_SpouseSelfEmployedInfoTest extends TestCase {
    private static final Application APPLICATION = ApplicationProvider.getApplicationContext();
    private static final String TRANSACTION_NO = "M021210014";
    private static final String STRING_ZERO = "0";
    private static final String STRING_ONE = "1";
    private static final String STRING_TWO = "2";
    private static final String FAKE_STRING = "Fake String";
    private static final String FAKE_JSON = "{\"key\":\"value\"}";
    private static final String FAKE_CODE = "00123";
    private static final String FAKE_NUMBER = "0123456789";
    private static final String FAKE_AMOUNT = "50,000";
    private static final String FAKE_COMPANY = "Guanzon Group of Companies";
    private SpouseSelfEmployedInfoModel infoModel;
    private VMSpouseSelfEmployedInfo mViewModel;

    @Mock
    ECreditApplicantInfo poInfo;

    @Before
    public void setUp() {
        poInfo = new ECreditApplicantInfo();
        infoModel = new SpouseSelfEmployedInfoModel();
        mViewModel = new VMSpouseSelfEmployedInfo(APPLICATION);

        poInfo.setTransNox(TRANSACTION_NO);
        poInfo.setClientNm(FAKE_NUMBER);
        poInfo.setDetlInfo(FAKE_JSON);
        poInfo.setPurchase(FAKE_STRING);
        poInfo.setApplInfo(FAKE_JSON);
        poInfo.setResidnce(FAKE_JSON);
        poInfo.setAppMeans(FAKE_JSON);
        poInfo.setEmplymnt(FAKE_JSON);
        poInfo.setBusnInfo(FAKE_JSON);
        poInfo.setFinancex(FAKE_JSON);
        poInfo.setPensionx(FAKE_JSON);
        poInfo.setOtherInc(FAKE_JSON);
        poInfo.setSpousexx(FAKE_JSON);
        poInfo.setSpsResdx(FAKE_JSON);
        poInfo.setSpsMeans(FAKE_JSON);
        poInfo.setSpsEmplx(FAKE_JSON);
        poInfo.setSpsBusnx(FAKE_JSON);
        poInfo.setSpsPensn(FAKE_JSON);
        poInfo.setDisbrsmt(FAKE_JSON);
        poInfo.setDependnt(FAKE_JSON);
        poInfo.setProperty(FAKE_JSON);
        poInfo.setOthrInfo(FAKE_JSON);
        poInfo.setComakerx(FAKE_JSON);
        poInfo.setCmResidx(FAKE_JSON);
        poInfo.setIsSpouse(STRING_ONE);
        poInfo.setIsComakr(STRING_ONE);
        poInfo.setBranchCd(FAKE_CODE);
        poInfo.setAppliedx(STRING_ONE);
        poInfo.setTransact("01-01-1990");
        poInfo.setCreatedx("01-01-1990");
        poInfo.setDownPaym(12.0);
        poInfo.setTranStat(STRING_ZERO);


        mViewModel.setTransNox(TRANSACTION_NO);
        mViewModel.setDetailInfo(poInfo);
        mViewModel.setBizIndustry(STRING_ONE);
        infoModel.setsBizName(FAKE_COMPANY);
        infoModel.setsBizAddress(FAKE_CODE);
        mViewModel.setProvinceID(FAKE_CODE);
        mViewModel.setTownID(FAKE_CODE);
        mViewModel.setBizType(STRING_ZERO);
        mViewModel.setBizSize(STRING_TWO);
        infoModel.setsBizYrs(STRING_TWO);
        mViewModel.setMosOrYr(STRING_ZERO);
        infoModel.setsGrossMonthly(FAKE_AMOUNT);
        infoModel.setsMonthlyExps("25,000");
    }

    @Test
    public void testSetTransNox() {
        assertTrue(mViewModel.setTransNox("M001210001"));
    }

    @Test
    public void testSetBizIndustry() {
        assertTrue(mViewModel.setBizIndustry(STRING_ONE));
    }

    @Test
    public void testSetBizType() {
        assertTrue(mViewModel.setBizType(STRING_ZERO));
    }

    @Test
    public void testSetBizSize() {
        assertTrue(mViewModel.setBizSize(STRING_TWO));
    }

    @Test
    public void testSetMosOrYr() {
        assertTrue(mViewModel.setMosOrYr(STRING_ZERO));
    }

    @Test
    public void testSetProvinceId() {
        assertTrue(mViewModel.setProvinceID(FAKE_CODE));
    }

    @Test
    public void testSetTownId() {
        assertTrue(mViewModel.setTownID(FAKE_CODE));
    }

    @Test
    public void testGetNatureOfBusiness() {
        mViewModel.getNatureOfBusiness().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Nature of Business");
        });
    }

    @Test
    public void testGetTypeOfBusiness() {
        mViewModel.getTypeOfBusiness().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Type of Business");
        });
    }

    @Test
    public void testGetSizeOfBusiness() {
        mViewModel.getSizeOfBusiness().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Size of Business");
        });
    }

    @Test
    public void testGetLengthOfService() {
        mViewModel.getLengthOfService().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Length of Service");
        });
    }

    @Test
    public void testSave() {
        assertTrue(mViewModel.Save(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                System.out.println(args);
            }

            @Override
            public void onFailedResult(String message) {
                System.out.println(message);
            }
        }));
    }

    private void displayArrayAdapterItem(ArrayAdapter<String> fsAdapter, String fsType) {
        System.out.println("\n" + fsType.toUpperCase() + " ITEMS");
        for(int x = 0; x < fsAdapter.getCount(); x++) {
            final int lnItemNox = x+1;
            System.out.println(fsType + " Item " + lnItemNox + ": " + fsAdapter.getItem(x));
        }
    }

}
