package org.rmj.guanzongroup.ghostrider.pacitaReward;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import org.rmj.guanzongroup.pacitareward.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Branch_Rate extends AppCompatActivity {
    RecyclerView rate_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_rate);

        rate_list = findViewById(R.id.rate_list);

        List<String> questionList = new ArrayList<>();
        questionList.add("Comfort Room Cleanliness");
        questionList.add("Store Ambiance");
        questionList.add("Staff Services/Accomodation");

        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter();
        viewAdapter.questionList = questionList;

        rate_list.setAdapter(viewAdapter);
        rate_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}