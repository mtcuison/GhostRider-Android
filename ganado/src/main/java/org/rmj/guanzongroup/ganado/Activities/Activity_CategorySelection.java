package org.rmj.guanzongroup.ganado.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import org.rmj.guanzongroup.ganado.R;

import java.util.Objects;

public class Activity_CategorySelection extends AppCompatActivity {

    private MaterialCardView selectAuto;
    private MaterialCardView selectCP;
    private MaterialCardView selectMC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);
        initWidgets();
        selectAuto = findViewById(R.id.materialCardView) ;
        selectCP = findViewById(R.id.materialCardView1);
        selectMC = findViewById(R.id.materialCardView2);

        selectAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent1 = new Intent(Activity_CategorySelection.this, Activity_BrandSelection.class);
                intent1.putExtra("background", R.drawable.img_category_mc);
                    startActivity(intent1);
                }
            });
        selectCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action you want for button 1
                // For example, switch to a new activity

                Intent intent1 = new Intent(Activity_CategorySelection.this, Activity_BrandSelection.class);
                intent1.putExtra("background", R.drawable.img_category_mp);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
                finish();
            }
        });
        selectMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action you want for button 1
                // For example, switch to a new activity

                Intent intent1 = new Intent(Activity_CategorySelection.this, Activity_BrandSelection.class);
                intent1.putExtra("background", R.drawable.img_category_mc);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
                finish();
            }
        });

    }
    private void initWidgets(){
        MaterialToolbar toolbar = findViewById(R.id.toolbar_category);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed () {
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
        finish();
    }

    @Override
    protected void onDestroy () {
        getViewModelStore().clear();
        super.onDestroy();
    }
}

