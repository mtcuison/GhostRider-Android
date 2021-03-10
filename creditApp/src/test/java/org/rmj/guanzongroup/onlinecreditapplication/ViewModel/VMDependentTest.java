package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.g3appdriver.utils.CodeGenerator;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMDependentTest {
    private DependentsInfoModel infoModels;
    private List<DependentsInfoModel> dependentInfo;
    private String transnox;
    private VMDependent mViewModel;
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
    @Mock
    VMDependent.ExpActionListener listener;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
        mViewModel = new VMDependent(ApplicationProvider.getApplicationContext());
        dependentInfo = new ArrayList<>();
        infoModels = new DependentsInfoModel();
        transnox = "Z3TXCBMCHCAO";
        dpdName = "Jonathan Sabiniano";
        mRelationPosition = "2";
        dpdAge = "30";
        IsStudentx = "0";
        IsPrivatex = "0";
        mEducLvlPosition = "0";
        IsScholarx = "0";
        dpdSchoolName = "";
        dpdSchoolAddress = "";
        dpdSchoolProv = "0";
        dpdSchoolTown = "";
        IsEmployed = "0";
        Employment = "0";
        dpdCompanyN = "Guanzon Group of Companies";
        Dependentx = "1";
        HouseHoldx = "1";
        IsMarriedx = "0";
        mViewModel.setTransNox(transnox);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddDependent() {
            DependentsInfoModel infoModel = new DependentsInfoModel(dpdName ,
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
            this.dependentInfo.add(infoModel);
//            when(mViewModel.AddDependent(infoModel, listener)).thenReturn(true);
            assertTrue(mViewModel.AddDependent(infoModel,listener));
            System.out.print(mViewModel.AddDependent(infoModel, listener));

    }

    @Test
    public void testSubmitDependentInfo() {
           // when(mViewModel.SubmitDependentInfo(callBack)).thenReturn(true);
            assertTrue(mViewModel.SubmitDependentInfo(callBack));
            System.out.print(mViewModel.SubmitDependentInfo(callBack));

    }

 

}