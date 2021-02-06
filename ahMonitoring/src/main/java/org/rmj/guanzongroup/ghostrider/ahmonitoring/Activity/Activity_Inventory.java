package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

public class Activity_Inventory extends AppCompatActivity {

    private TextView lblTransNox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Toolbar toolbar = findViewById(R.id.toolbar_inventory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lblTransNox = findViewById(R.id.lbl_itemTransNox);
        lblTransNox.setText(getIntent().getStringExtra("transno"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}