package org.rmj.guanzongroup.pacitareward;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_BranchList extends AppCompatActivity {
    MaterialToolbar toolbar;
    RecyclerView rvc_branchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_list);

        toolbar = findViewById(R.id.toolbar);
        rvc_branchlist = findViewById(R.id.branch_list);

        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle(""); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        List<String> listBranches = new ArrayList<>();
        listBranches.add("Nepo Mall");
        listBranches.add("Robinsons Mall");
        listBranches.add("SM Store");
        listBranches.add("CSI Square");
        listBranches.add("CSI Lucao");

        HashMap<String, String> mapBranches = new HashMap<String, String>();
        mapBranches.put("Nepo Mall", "Arellano St. Dagupan City");
        mapBranches.put("Robinsons Mall", "Calasiao, Dagupan City");
        mapBranches.put("SM Store", "Arellano St. Dagupan City");
        mapBranches.put("CSI Square", "Perez, Downtown St. Dagupan City");
        mapBranches.put("CSI Lucao", "Lucao Dist, Dagupan City");

        RecyclerViewAdapter_BranchList rec_branchList = new RecyclerViewAdapter_BranchList(Activity_BranchList.this, listBranches, mapBranches);
        rvc_branchlist.setAdapter(rec_branchList);
        rvc_branchlist.setLayoutManager(new LinearLayoutManager(Activity_BranchList.this));
    }
}