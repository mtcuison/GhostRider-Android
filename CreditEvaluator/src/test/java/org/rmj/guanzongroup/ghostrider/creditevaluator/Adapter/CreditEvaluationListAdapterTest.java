package org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstantsTest;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModelTest;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class CreditEvaluationListAdapterTest {
    private Context context;
    private List<CreditEvaluationModel> ciList;
    private CreditEvaluationModel infoModel;
    @Mock
    CreditEvaluationListAdapter.OnApplicationClickListener onApplicationClickListener;
    @Before
    public void setUp() {
        Application application = RuntimeEnvironment.application;
        assertNotNull(application);

        context = application;
        infoModel = new CreditEvaluationModel();
        ciList = CIConstantsTest.getDataList();



    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_onCreateViewHolder() {
        CreditEvaluationListAdapter adapter = new CreditEvaluationListAdapter(ciList, onApplicationClickListener);
        LinearLayout parent = new LinearLayout(context);

        //child view holder
        RecyclerView.ViewHolder childViewHolder = adapter.onCreateViewHolder(parent, R.layout.credit_app_list);
        assertTrue(childViewHolder instanceof CreditEvaluationListAdapter.CreditEvaluationViewHolder);
//
    }

    @Test
    public void test_getItemCount() {
        CreditEvaluationListAdapter adapter = new CreditEvaluationListAdapter(ciList,onApplicationClickListener);
        //initial state
        int initialActual = adapter.getItemCount();
        assertEquals(ciList.size(), initialActual);
    }

    @Test
    public void test_getItemViewValue() {
        //initial state
        Random random = new Random();
        int position = random.nextInt(ciList.size());
        infoModel = ciList.get(position);
//              ASSERTION
        Assert.assertEquals(infoModel.getsTransNox(), ciList.get(position).getsTransNox());
        Assert.assertEquals(infoModel.getdTransact(), ciList.get(position).getdTransact());
        Assert.assertEquals(infoModel.getsCredInvx(), ciList.get(position).getsCredInvx());
        Assert.assertEquals(infoModel.getsCompnyNm(), ciList.get(position).getsCompnyNm());
        Assert.assertEquals(infoModel.getsSpouseNm(), ciList.get(position).getsSpouseNm());
        Assert.assertEquals(infoModel.getsAddressx(), ciList.get(position).getsAddressx());
        Assert.assertEquals(infoModel.getsMobileNo(), ciList.get(position).getsMobileNo());
        Assert.assertEquals(infoModel.getsQMAppCde(), ciList.get(position).getsQMAppCde());
        Assert.assertEquals(infoModel.getsModelNme(), ciList.get(position).getsModelNme());
        Assert.assertEquals(infoModel.getnDownPaym(), ciList.get(position).getnDownPaym());
        Assert.assertEquals(infoModel.getnAcctTerm(), ciList.get(position).getnAcctTerm());
        Assert.assertEquals(infoModel.getcTranStat(), ciList.get(position).getcTranStat());
        Assert.assertEquals(infoModel.getdTimeStmp(), ciList.get(position).getdTimeStmp());
//      PRINT CLIENT DETAIL from ciList index 3
        System.out.println("position = " +  position);
        System.out.println("sTransNox = " +  infoModel.getsTransNox());
        System.out.println("dTransact = " +  infoModel.getdTransact());
        System.out.println("sCredInvx = " +  infoModel.getsCredInvx());
        System.out.println("sCompnyNm = " +  infoModel.getsCompnyNm());
        System.out.println("sSpouseNm = " +  infoModel.getsSpouseNm());
        System.out.println("sAddressx = " +  infoModel.getsAddressx());
        System.out.println("sMobileNo = " +  infoModel.getsMobileNo());
        System.out.println("sQMAppCde = " +  infoModel.getsQMAppCde());
        System.out.println("sModelNme  = " +  infoModel.getsModelNme());
        System.out.println("nDownPaym = " +  infoModel.getnDownPaym());
        System.out.println("nAcctTerm = " +  infoModel.getnAcctTerm());
        System.out.println("cTranStat = " +  infoModel.getcTranStat());
        String timeStamp = infoModel.getdTimeStmp();
        System.out.println("dTimeStmp = " + timeStamp + "\n");
    }

}