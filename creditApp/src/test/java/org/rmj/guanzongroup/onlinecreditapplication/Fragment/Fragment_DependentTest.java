package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDependent;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMOtherInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_DependentTest implements ViewModelCallBack, VMDependent.ExpActionListener {
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
    private List<DependentsInfoModel> arrayList;
    private VMDependent mViewModel;
    private Fragment_Dependent fragment;
    @Mock
    ViewModelCallBack callBack;

    @Mock
    VMDependent.ExpActionListener listener;
    @Before
    public void setUp() throws Exception {
        mViewModel = new VMDependent(ApplicationProvider.getApplicationContext());
        infoModel = new DependentsInfoModel();
        fragment = new Fragment_Dependent();

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
        arrayList = new ArrayList<>();




    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_addDependent(){
        try {
            addDependent();
            System.out.println("Dependent count = " + mViewModel.getAllDependent().getValue().size());
        }catch (NullPointerException e){
            e.getMessage();
        } catch (Exception e){
            e.getMessage();
        }
    }
    @Test
    public void test_submitDependent(){
        try {
            addDependent();
            if (addDependent().size() >0){
                Assert.assertTrue(mViewModel.SubmitDependentInfo(this));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveSuccessResult(String args) {

        System.out.println("Submit " + args);
    }

    @Override
    public void onFailedResult(String message) {

        System.out.print("Submit " + message);
    }

    @Override
    public void onSuccess(String message) {

        System.out.println("Add " + message);
    }

    @Override
    public void onFailed(String message) {

        System.out.println("Add " + message);
    }
    public List<DependentsInfoModel> addDependent(){
        for (int i = 0;i<=3; i++){
            infoModel = new DependentsInfoModel(dpdName ,
                    mRelationPosition ,
                    dpdAge,
                    IsStudentx,
                    IsPrivatex,
                    IsScholarx,
                    mEducLvlPosition,
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
            arrayList.add(infoModel);
            Assert.assertEquals(true, mViewModel.AddDependent(infoModel, this));
        }
        return arrayList;
    }
    @Test
    public void test_getInfoModels(){
        for (int i = 0;i<=3; i++){
            infoModel = new DependentsInfoModel(dpdName ,
                    mRelationPosition ,
                    dpdAge,
                    IsStudentx,
                    IsPrivatex,
                    IsScholarx,
                    mEducLvlPosition,
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
            Assert.assertEquals(true, mViewModel.AddDependent(infoModel, this));
        }
        Assert.assertEquals(dpdName,infoModel.getDpdFullName());
        Assert.assertEquals(mRelationPosition,infoModel.getDpdRlationship());
        Assert.assertEquals(dpdAge,infoModel.getDpdAge());
        Assert.assertEquals(IsStudentx,infoModel.getIsStudent());
        Assert.assertEquals(IsPrivatex,infoModel.getDpdSchoolType());
        Assert.assertEquals(mEducLvlPosition,infoModel.getDpdEducLevel());

        Assert.assertEquals(IsScholarx,infoModel.getDpdIsScholar());
        Assert.assertEquals(dpdSchoolName,infoModel.getDpdSchoolName());
        Assert.assertEquals(dpdSchoolAddress,infoModel.getDpdSchoolAddress());

        Assert.assertEquals(dpdSchoolTown,infoModel.getDpdSchoolTown());
        Assert.assertEquals(IsEmployed,infoModel.getIsEmployed());
        Assert.assertEquals(Employment,infoModel.getDpdEmployedSector());
        Assert.assertEquals(dpdCompanyN,infoModel.getDpdCompanyName());
        Assert.assertEquals(Dependentx,infoModel.getIsDependent());
        Assert.assertEquals(HouseHoldx,infoModel.getIsHouseHold());
        Assert.assertEquals(IsMarriedx,infoModel.getIsMarried());

        System.out.print("Full Name = " + infoModel.getDpdFullName() + "\n");
        System.out.print("Dependent Relation index = " + infoModel.getDpdRlationship() + "\n");
        System.out.print("Dependednt Age = " + infoModel.getDpdAge() + "\n");
        System.out.print("Dependednt is Student Y(1)/N(0)? = " + infoModel.getIsStudent() + "\n");
        System.out.print("School Type = " + infoModel.getDpdSchoolType() + "\n");
        System.out.print("Educational Level = " + infoModel.getDpdEducLevel() + "\n");
        System.out.print("Scholar Y(1)/N(0)? = " + infoModel.getDpdIsScholar() + "\n");
        System.out.print("School Name" + infoModel.getDpdSchoolName() + "\n");

        System.out.print("School Address = " + infoModel.getDpdSchoolAddress() + "\n");
        System.out.print("School Town" + infoModel.getDpdSchoolTown() + "\n");
        System.out.print("Dependednt is Student Y(1)/N(0)? = " + infoModel.getIsEmployed() + "\n");
        System.out.print("Dependednt is Student Y(1)/N(0)? = " + infoModel.getDpdEmployedSector() + "\n");
        System.out.print("Company Name" + infoModel.getDpdCompanyName() + "\n");
        System.out.print("Is Dependent Y(1)/N(0)? = " + infoModel.getIsDependent() + "\n");
        System.out.print("Is Household Y(1)/N(0)? = " + infoModel.getIsHouseHold() + "\n");
        System.out.print("Is Married Y(1)/N(0)? = " + infoModel.getIsMarried() + "\n");

    }
}