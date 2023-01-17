package org.guanzongroup.com.creditevaluation;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.annotation.UiThread;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

import java.util.HashMap;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class EvaluationCoreTest {
    private static final String TAG = EvaluationCoreTest.class.getSimpleName();

    private Application instance;
    private RCreditOnlineApplicationCI poDB;
    private EvaluatorManager poSystem;

    private boolean isSuccess = false;

    private HashMap<oParentFndg, List<oChildFndg>> poEvaluate;

    @Before
    public void setup() throws Exception{
        instance = ApplicationProvider.getApplicationContext();
        poDB = new RCreditOnlineApplicationCI(instance);
        poSystem = new EvaluatorManager(instance);
    }

    @Test
    public void test01TestInsert(){
        ECreditOnlineApplicationCI loCI = new ECreditOnlineApplicationCI();
        loCI.setTransNox("CI5UV2200018");
        loCI.setAddressx("{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}");
        loCI.setAddrFndg("{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}");
        loCI.setAssetsxx("{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}");
        loCI.setAsstFndg("{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}");
        loCI.setIncomexx("{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}");
        loCI.setIncmFndg("{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":0.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":0.0},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}");

        poDB.SaveApplicationInfo(loCI);
    }

    @Test
    public void test02TestRetrieve(){
//        poSystem.RetrieveApplicationData("CI5UV2200018", new EvaluatorManager.OnRetrieveDataCallback() {
//            @Override
//            public void OnRetrieve(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI foData) {
//                poEvaluate = foEvaluate;
//                isSuccess = true;
//            }
//
//            @Override
//            public void OnFailed(String message) {
//                isSuccess = false;
//            }
//        });
//        assertTrue(isSuccess);
    }

    @Test @UiThread
    public void test03TestUpdateCI() throws Exception{
        poSystem.RetrieveApplicationData("CI5UV2200018").observeForever(new Observer<ECreditOnlineApplicationCI>() {
            @Override
            public void onChanged(ECreditOnlineApplicationCI eCreditOnlineApplicationCI) {
                try{
                    poEvaluate = poSystem.parseToEvaluationData(eCreditOnlineApplicationCI);
                    isSuccess = true;
                } catch (Exception e){
                    e.printStackTrace();
                    isSuccess = false;
                }
            }
        });

        poEvaluate.forEach((oParent, oChild) -> {
            if(oChild.size() > 0) {
                for(int x = 0; x < oChild.size(); x++){
                    oChildFndg loChild = oChild.get(x);
                    loChild.setsValue("1");
                    String lsField = oParent.getField();
                    poSystem.UpdateConfirmInfos("CI5UV2200018", oParent, loChild, new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            isSuccess = true;
                        }

                        @Override
                        public void OnFailed(String message) {
                            isSuccess = false;
                            Log.d(TAG, message);
                        }
                    });
                }
            } else {
                isSuccess = false;
            }
        });

        EImageInfo loImage = new EImageInfo();
        loImage.setLatitude("0.0");
        loImage.setLongitud("0.0");

        poSystem.SaveImageInfo("CI5UV2200018", loImage, true, new EvaluatorManager.OnActionCallback() {
            @Override
            public void OnSuccess(String args) {

            }

            @Override
            public void OnFailed(String message) {

            }
        });

        String lsAddress = poDB.getAddressForEvaluation("CI5UV2200018");
        assertTrue(isSuccess);

        String lsExpect = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";
        assertEquals(lsExpect, lsAddress);
    }
}
