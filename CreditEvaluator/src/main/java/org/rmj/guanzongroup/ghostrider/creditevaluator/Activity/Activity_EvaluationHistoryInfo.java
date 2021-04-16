package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationHistoryInfoAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.EvaluationHistoryInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationHistory;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationHistoryInfo;

import java.util.List;
import java.util.Objects;

public class Activity_EvaluationHistoryInfo extends AppCompatActivity {
    private static final String TAG = Activity_EvaluationHistoryInfo.class.getSimpleName();
    private VMEvaluationHistoryInfo mViewModel;
    private LinearLayoutManager poLayout;
    private CreditEvaluationHistoryInfoAdapter poAdapter;
    private RecyclerView recyclerView;

    public static String psTransNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_history_info);

        Toolbar toolbar = findViewById(R.id.toolbar_evaluation_history_info);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(CIConstants.EVALUATION_HISTORY);

        initObjects();
        initWidgets();
        initIntentValues();

        // Set ViewModel Parameters
        // mViewModel.setCreditEvaluationObject();

        mViewModel.onFetchCreditEvaluationDetail(evaluationDetl -> {
            this.poAdapter = new CreditEvaluationHistoryInfoAdapter(evaluationDetl);
            // Displaying Evaluation Info
            // Must be inside mViewModel method call
            poLayout.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(poLayout);
            recyclerView.setAdapter(this.poAdapter);
        });

    }

    private void initObjects() {
        this.mViewModel = ViewModelProviders.of(this).get(VMEvaluationHistoryInfo.class);
        this.poLayout = new LinearLayoutManager(Activity_EvaluationHistoryInfo.this);
    }

    private void initWidgets() {
        // poAdapter = new EvaluationHistoryInfoAdapter();
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initIntentValues() {
        psTransNo = getIntent().getStringExtra("sTransNox");
    }
}