package org.rmj.guanzongroup.ghostrider.notifications.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMViewNotification;

public class Activity_ViewNotification extends AppCompatActivity {
    private static final String TAG = Activity_ViewNotification.class.getSimpleName();

    private VMViewNotification mViewModel;

    private MaterialToolbar toolbar;
    private MaterialTextView lblTitle,
                            lblSendr,
                            lblRcpnt,
                            lblDatex,
                            lblBodyx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ViewNotification.this).get(VMViewNotification.class);
        setContentView(R.layout.activity_view_notification);
        initWidgets();

        if(!getIntent().hasExtra("sMesgIDxx")){
            return;
        }

        String lsMesgID = getIntent().getStringExtra("sMesgIDxx");
        mViewModel.SendResponse(lsMesgID);
        mViewModel.GetNotificationMaster(lsMesgID).observe(Activity_ViewNotification.this, new Observer<ENotificationMaster>() {
            @Override
            public void onChanged(ENotificationMaster master) {
                try {
                    if (master == null) {
                        return;
                    }

                    lblTitle.setText(master.getMsgTitle());
                    lblSendr.setText(master.getCreatrNm() == null ? "null" : "SYSTEM");
                    lblBodyx.setText(master.getMessagex());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetNotificationDetail(lsMesgID).observe(Activity_ViewNotification.this, new Observer<ENotificationRecipient>() {
            @Override
            public void onChanged(ENotificationRecipient detail) {
                try{
                    if(detail == null){
                        return;
                    }

                    lblRcpnt.setText(detail.getRecpntNm());
                    lblDatex.setText(FormatUIText.getParseDateTime(detail.getReceived()));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void initWidgets(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lblTitle = findViewById(R.id.lbl_messageTitle);
        lblSendr = findViewById(R.id.lbl_messageSender);
        lblRcpnt = findViewById(R.id.lbl_messageRecipient);
        lblDatex = findViewById(R.id.lbl_messageDateTime);
        lblBodyx = findViewById(R.id.lbl_messageBody);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}