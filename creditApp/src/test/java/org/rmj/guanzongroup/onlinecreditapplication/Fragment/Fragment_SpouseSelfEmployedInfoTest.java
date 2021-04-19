package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.app.Application;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
    private static final String STRING_ZERO = "0";
    private static final String STRING_ONE = "1";
    private static final String STRING_TWO = "2";
    private static final String FAKE_CODE = "00123";
    private SpouseSelfEmployedInfoModel infoModel;
    private VMSpouseSelfEmployedInfo mViewModel;

    @Mock
    ViewModelCallBack callBack;

    @Before
    public void setUp() {
        infoModel = new SpouseSelfEmployedInfoModel();
        mViewModel = new VMSpouseSelfEmployedInfo(APPLICATION);
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

//    @Test
//    public void testGetNatureOfBusiness() {
//        assertEquals(getDropdownListItem(CreditAppConstants.BUSINESS_NATURE), mViewModel.getNatureOfBusiness());
//    }
//
//
//
//
//
//    private ArrayAdapter<String> getDropdownListItem(String[] fSource) {
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(APPLICATION, android.R.layout.simple_spinner_dropdown_item, fSource);
//        return adapter;
//    }

}
