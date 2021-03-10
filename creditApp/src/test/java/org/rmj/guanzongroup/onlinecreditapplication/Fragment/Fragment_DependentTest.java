package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDependent;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDependent.ExpActionListener;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_DependentTest {
    private String dpdName,
            mRelationPosition,
            dpdAge,
            IsStudentx,
            IsPrivatex,
            mEducLvlPosition,
            IsScholarx,
            dpdSchoolName,
            dpdSchoolAddress,
            dpdSchoolProv,
            dpdSchoolTown,
            IsEmployed,
            Employment,
            dpdCompanyN,
            Dependentx,
            HouseHoldx,
            IsMarriedx;
    private String TransNox;
    private DependentsInfoModel infoModel;
    private VMDependent mViewModel;
//    VMDependent.ExpActionListener listeners;
    @Mock
    VMDependent.ExpActionListener listener;
    @Mock
    ViewModelCallBack callBack;

    @Before
    public void setUp() {
        infoModel = new DependentsInfoModel();
        mViewModel = new VMDependent(ApplicationProvider.getApplicationContext());

        TransNox = "Z3TXCBMCHCAO";
        dpdName = "Jonathan Sabiniano";
        mRelationPosition = "2";
        dpdAge = "30";
        IsStudentx = "1";
        IsPrivatex = "0";
        mEducLvlPosition = "0";
        IsScholarx = "1";
        dpdSchoolName = "University of Pangasinan";
        dpdSchoolAddress = "Arellano Street";
        dpdSchoolProv = "20";
        dpdSchoolTown = "20";
        IsEmployed = "1";
        Employment = "1";
        dpdCompanyN = "Guanzon Group of Companies";
        Dependentx = "1";
        HouseHoldx = "1";
        IsMarriedx = "0";
        mViewModel.setTransNox(TransNox);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_submitNext(){
            assertTrue(mViewModel.SubmitDependentInfo(callBack));
            assertEquals(true,mViewModel.SubmitDependentInfo(callBack));

    }
    @Test
    public void test_addDependent(){
            DependentsInfoModel infoModels = new DependentsInfoModel(dpdName ,
                    mRelationPosition ,
                    dpdAge,
                    IsStudentx,
                    IsPrivatex,
                    mEducLvlPosition,
                    IsScholarx,
                    dpdSchoolName,
                    dpdSchoolAddress,
                    dpdSchoolProv,
                    dpdSchoolTown,
                    IsEmployed,
                    Employment ,
                    dpdCompanyN,
                    Dependentx,
                    HouseHoldx,
                    IsMarriedx);
            assertTrue(mViewModel.AddDependent(infoModels,listener));
            assertEquals(true,mViewModel.AddDependent(infoModel,listener));
            System.out.print(mViewModel.getAllDependent());

    }
}