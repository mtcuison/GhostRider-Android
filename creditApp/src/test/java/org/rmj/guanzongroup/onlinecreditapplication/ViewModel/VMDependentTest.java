package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.g3appdriver.utils.CodeGenerator;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        MockitoAnnotations.initMocks(this);
        dependentInfo = new ArrayList<>();
        infoModels = new DependentsInfoModel();
        mViewModel =  mock(VMDependent.class);
        transnox = new CodeGenerator().generateTransNox();
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

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddDependent() {
        try{
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
            when(mViewModel.AddDependent(infoModel, listener)).thenReturn(true);
            assertTrue(mViewModel.AddDependent(infoModel,listener));
            assertEquals(true,mViewModel.AddDependent(infoModel, listener));
            System.out.print(mViewModel.AddDependent(infoModel, listener));
        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
    }

    @Test
    public void testSubmitDependentInfo() {
        try{
            when(mViewModel.SubmitDependentInfo(callBack)).thenReturn(true);
            assertTrue(mViewModel.SubmitDependentInfo(callBack));
            assertEquals(true,mViewModel.SubmitDependentInfo(callBack));
            System.out.print(mViewModel.SubmitDependentInfo(callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
    }

 

}