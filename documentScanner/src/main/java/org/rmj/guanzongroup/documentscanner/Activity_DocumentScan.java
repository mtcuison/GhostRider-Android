package org.rmj.guanzongroup.documentscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import org.rmj.g3appdriver.lib.Scanner.DocScanner;

public class Activity_DocumentScan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_scan);

        ImageView imageView = findViewById(R.id.image_view);

        new DocScanner(Activity_DocumentScan.this).initScanner(new DocScanner.OnScanDocumentListener() {
            @Override
            public void OnScanned(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void OnFailed(String message) {
                Toast.makeText(Activity_DocumentScan.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnCancelled() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}