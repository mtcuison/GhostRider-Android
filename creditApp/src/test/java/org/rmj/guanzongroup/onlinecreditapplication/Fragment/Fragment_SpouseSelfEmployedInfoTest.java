package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;
import android.widget.ArrayAdapter;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseSelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.TestConstants;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseSelfEmployedInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE)
public class Fragment_SpouseSelfEmployedInfoTest extends TestCase {
    private SpouseSelfEmployedInfoModel infoModel;
    private VMSpouseSelfEmployedInfo mViewModel;
    private ECreditApplicantInfo poInfo;

//    @Mock
//    ECreditApplicantInfo poInfo;

    @Before
    public void setUp() {
        poInfo = TestConstants.getDummyCreditApp();
        infoModel = new SpouseSelfEmployedInfoModel();
        mViewModel = new VMSpouseSelfEmployedInfo(TestConstants.APPLICATION);

        mViewModel.setTransNox(TestConstants.TRANSACTION_NO);
        mViewModel.setDetailInfo(poInfo);
        mViewModel.setBizIndustry(TestConstants.STRING_ONE);
        infoModel.setsBizName(TestConstants.FAKE_COMPANY);
        infoModel.setsBizAddress(TestConstants.FAKE_CODE);
        mViewModel.setProvinceID(TestConstants.FAKE_CODE);
        mViewModel.setTownID(TestConstants.FAKE_CODE);
        mViewModel.setBizType(TestConstants.STRING_ZERO);
        mViewModel.setBizSize(TestConstants.STRING_TWO);
        infoModel.setsBizYrs(TestConstants.STRING_TWO);
        mViewModel.setMosOrYr(TestConstants.STRING_ZERO);
        infoModel.setsGrossMonthly(TestConstants.FAKE_STRING_AMOUNT);
        infoModel.setsMonthlyExps("25,000");
    }

    @Test
    public void testSetTransNox() {
        assertTrue(mViewModel.setTransNox("M001210001"));
    }

    @Test
    public void testSetBizIndustry() {
        assertTrue(mViewModel.setBizIndustry(TestConstants.STRING_ONE));
    }

    @Test
    public void testSetBizType() {
        assertTrue(mViewModel.setBizType(TestConstants.STRING_ZERO));
    }

    @Test
    public void testSetBizSize() {
        assertTrue(mViewModel.setBizSize(TestConstants.STRING_TWO));
    }

    @Test
    public void testSetMosOrYr() {
        assertTrue(mViewModel.setMosOrYr(TestConstants.STRING_ZERO));
    }

    @Test
    public void testSetProvinceId() {
        assertTrue(mViewModel.setProvinceID(TestConstants.FAKE_CODE));
    }

    @Test
    public void testSetTownId() {
        assertTrue(mViewModel.setTownID(TestConstants.FAKE_CODE));
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
