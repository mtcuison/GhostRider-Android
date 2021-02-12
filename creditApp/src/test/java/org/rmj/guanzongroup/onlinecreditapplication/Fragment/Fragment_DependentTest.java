package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDependent;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDependent.ExpActionListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private DependentsInfoModel infoModel;
    private VMDependent mViewModel;
    @Mock
    ExpActionListener listener;
    @Mock
    ViewModelCallBack callBack;

    Fragment_Dependent fragment_dependent;

    @Before
    public void setUp() throws Exception {
        infoModel = new DependentsInfoModel();
        mViewModel =  mock(VMDependent.class);
        fragment_dependent = new Fragment_Dependent();
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

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_submitNext(){
        try{

            when(mViewModel.SubmitDependentInfo(fragment_dependent)).thenReturn(true);
            assertTrue(mViewModel.SubmitDependentInfo(fragment_dependent));
            assertEquals(true,mViewModel.SubmitDependentInfo(fragment_dependent));

        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test_addDependent(){
        try{
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
            when(mViewModel.AddDependent(infoModels, fragment_dependent)).thenReturn(true);
            assertTrue(mViewModel.AddDependent(infoModel,fragment_dependent));
            assertEquals(true,mViewModel.AddDependent(infoModel,fragment_dependent));
            System.out.print(mViewModel.getAllDependent());

        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}