/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 10/22/21, 3:13 PM
 * project file last modified : 10/22/21, 3:13 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPostDcp;

public class Activity_PostDcp extends AppCompatActivity {

    private VMPostDcp mViewModel;

    private boolean Posting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_dcp);

        mViewModel = new ViewModelProvider(this).get(VMPostDcp.class);

        mViewModel.PostDCP(new VMPostDcp.OnPostCollectionCallback() {

            @Override
            public void OnStartPosting(String Title, String Message) {
                Posting = true;
            }

            @Override
            public void OnProgress(String label, int value) {

            }

            @Override
            public void OnFinishPosting(boolean isSuccess, String Message) {
                Posting = true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home &&
            !Posting){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!Posting){
            finish();
        }
    }
}