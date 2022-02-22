package org.guanzongroup.com.creditevaluation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluation;

public class Activity_Evaluation extends AppCompatActivity {

    private VMEvaluation mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        mViewModel = new ViewModelProvider(this).get(VMEvaluation.class);
    }
}