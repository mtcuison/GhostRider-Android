package org.rmj.guanzongroup.ganado.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import org.rmj.guanzongroup.ganado.R;

public class Activity_CategorySelection extends AppCompatActivity {

    private MaterialCardView selectAuto;
    private MaterialCardView selectCP;
    private MaterialCardView selectMC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        selectAuto = findViewById(R.id.materialCardView) ;
        selectCP = findViewById(R.id.materialCardView1);
        selectMC = findViewById(R.id.materialCardView2);
        selectAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Perform the action you want for button 1
                    // For example, switch to a new activity

                    Intent intent1 = new Intent(Activity_CategorySelection.this, Activity_BrandSelection.class);
                    startActivity(intent1);
                }
            });
        selectCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action you want for button 1
                // For example, switch to a new activity

                Intent intent1 = new Intent(Activity_CategorySelection.this, Activity_BrandSelection.class);
                startActivity(intent1);
            }
        });
        selectMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action you want for button 1
                // For example, switch to a new activity

                Intent intent1 = new Intent(Activity_CategorySelection.this, Activity_BrandSelection.class);
                startActivity(intent1);
            }
        });

        }
}

