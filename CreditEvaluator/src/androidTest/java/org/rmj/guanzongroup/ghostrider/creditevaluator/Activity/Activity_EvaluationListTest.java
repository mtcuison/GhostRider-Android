package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationListAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationList;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4ClassRunner.class)
public class Activity_EvaluationListTest {

    private RecyclerView recyclerViewClient;
    private VMEvaluationList mViewModel;
    private LinearLayoutManager layoutManager;
    private CreditEvaluationListAdapter adapter;
    private LinearLayout loading;
    private List<CreditEvaluationModel> ciEvaluationList;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private String userBranch;
    private TextInputEditText txtSearch;
    private TextView layoutNoRecord;
    private Activity_EvaluationList activity;
    @Before
    public void setUp() throws Exception {
        mViewModel = new ViewModelProvider(activity).get(VMEvaluationList.class);
    }

    @After
    public void tearDown() throws Exception {
    }
}