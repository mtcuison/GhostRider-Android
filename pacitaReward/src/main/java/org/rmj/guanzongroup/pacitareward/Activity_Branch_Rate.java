package org.rmj.guanzongroup.pacitareward;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class Activity_Branch_Rate extends AppCompatActivity {
    RecyclerView rate_list;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_rate);

        toolbar = findViewById(R.id.toolbar);
        rate_list = findViewById(R.id.rate_list);

        /*TOOL BAR*/
        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Branch Rate"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        List<String> questionList = new ArrayList<>();
        questionList.add("Comfort Room Cleanliness");
        questionList.add("Store Ambiance");
        questionList.add("Staff Services/Accomodation");

        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter(Activity_Branch_Rate.this, questionList);

        rate_list.setAdapter(viewAdapter);
        rate_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}