package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationListAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstantsTest;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.robolectric.RuntimeEnvironment.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Activity_EvaluationListTest {

    private Context context;
    private List<CreditEvaluationModel> ciList;
    private Activity_EvaluationList activity;
    private LinearLayoutManager mockLayoutManager;
    Application application;
    @Inject
    CreditEvaluationListAdapter mockAdapter;

    @Mock
    CreditEvaluationListAdapter.OnApplicationClickListener onApplicationClickListener;
    @Before
    public void setUp() throws Exception {
        application = RuntimeEnvironment.application;
        assertNotNull(application);
        context = application;
        activity = new Activity_EvaluationList();
        mockLayoutManager = Mockito.mock(LinearLayoutManager.class);
        ciList = CIConstantsTest.getDataList();
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void checkRecyclerViewAdapter() {
        CreditEvaluationListAdapter adapter = new CreditEvaluationListAdapter( ciList,onApplicationClickListener);

        System.out.println(adapter.getItemCount() + " " + ciList.size());
    }

}