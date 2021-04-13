package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

import java.util.Objects;

public class Activity_EvaluationHistoryInfo extends AppCompatActivity {
    private static final String TAG = Activity_EvaluationHistoryInfo.class.getSimpleName();
    private static String psTransNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_history_info);

        Toolbar toolbar = findViewById(R.id.toolbar_evaluation_history_info);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(CIConstants.EVALUATION_HISTORY);

        initIntentValues();
    }

    private void initIntentValues() {
        psTransNo = getIntent().getStringExtra("sTransNox");
    }
}