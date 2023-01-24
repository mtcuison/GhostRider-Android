package org.rmj.guanzongroup.ghostrider.PetManager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import org.rmj.guanzongroup.ghostrider.PetManager.R;

public class Activity_EmployeeLoanEntry extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_loan_entry);

//      set declared toolbar object value by getting id object from xml file
        toolbar = findViewById(R.id.toolbar);
//      set object toolbar as default action bar for activity
        setSupportActionBar(toolbar);
//      set default title for action bar
        getSupportActionBar().setTitle("");
//      set back button to toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//      enable the back button set on toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
}