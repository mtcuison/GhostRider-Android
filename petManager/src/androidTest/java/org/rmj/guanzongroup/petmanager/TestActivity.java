package org.rmj.guanzongroup.petmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.rmj.guanzongroup.petmanager.Fragment.Fragment_LeaveApplication;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment_LeaveApplication())
                    .commit();
        }
    }
}