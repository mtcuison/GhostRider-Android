package org.rmj.guanzongroup.ghostrider.settings.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMAppVersion;

import java.util.List;

public class Activity_AppVersion_Download extends AppCompatActivity {
    TextView lbl_updatedLog;
    TextView lbl_updatedFixedConcerns;
    TextView btn_checkupdate;
    TextView rec_updatedNewFeatures;
    TextView rec_updatedFixedConcerns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_appversion_download_update);

        lbl_updatedLog = findViewById(R.id.lbl_updatedLog);
        lbl_updatedFixedConcerns = findViewById(R.id.lbl_updatedFixedConcerns);

        btn_checkupdate = findViewById(R.id.btn_checkupdate);
        rec_updatedNewFeatures = findViewById(R.id.rec_updatelogs);
        rec_updatedFixedConcerns = findViewById(R.id.rec_updatedFixedConcerns);
    }
}
