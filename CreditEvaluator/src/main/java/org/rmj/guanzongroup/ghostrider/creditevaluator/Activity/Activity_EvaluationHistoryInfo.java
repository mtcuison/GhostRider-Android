package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

import java.util.Objects;

public class Activity_EvaluationHistoryInfo extends AppCompatActivity {
    private static final String TAG = Activity_EvaluationHistoryInfo.class.getSimpleName();
    private LinearLayoutManager poLayout;
//    private EvaluationHistoryInfoAdapter poAdapter;
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

        initWidgets();
        initIntentValues();

        // Displaying Evaluation Info
        // Must be inside mViewModel method call
        poLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(poLayout);
//        recyclerView.setAdapter(poAdapter);
    }

    private void initWidgets() {
        poLayout = new LinearLayoutManager(Activity_EvaluationHistoryInfo.this);
        // poAdapter = new EvaluationHistoryInfoAdapter();
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initIntentValues() {
        psTransNo = getIntent().getStringExtra("sTransNox");
    }
}