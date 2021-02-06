package org.rmj.guanzongroup.authlibrary.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.rmj.g3appdriver.GRider.Etc.TransparentToolbar;
import org.rmj.guanzongroup.authlibrary.R;

public class Activity_Authenticate extends AppCompatActivity {

    //private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        new TransparentToolbar(Activity_Authenticate.this).SetupActionbar();
    }
}