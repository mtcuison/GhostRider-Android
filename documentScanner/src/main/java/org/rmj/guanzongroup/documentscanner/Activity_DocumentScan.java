package org.rmj.guanzongroup.documentscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.SessionManager;
import org.rmj.g3appdriver.lib.Scanner.DocScanner;
import org.rmj.g3appdriver.etc.ImageFileCreator;

import java.io.File;

public class Activity_DocumentScan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_scan);

        ImageView imageView = findViewById(R.id.image_view);

        new DocScanner(Activity_DocumentScan.this).initScanner(new DocScanner.OnScanDocumentListener() {
            @Override
            public void OnScanned(Bitmap bitmap) {
//                imageView.setImageBitmap(bitmap);

                ImageFileCreator loImage = new ImageFileCreator(
                        Activity_DocumentScan.this,
                        AppConstants.SUB_FOLDER_CREDIT_APP_DOCUMENTS,
                        new SessionManager(Activity_DocumentScan.this).getUserID());
                if(loImage.SaveDocumentScan(bitmap)){
                    finish();
                } else {
                    Toast.makeText(Activity_DocumentScan.this, loImage.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnFailed(String message) {
                Toast.makeText(Activity_DocumentScan.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnCancelled() {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    private File createImageScanFile(String TransNox, int EntryNox, String FileCode) {
        String root = Environment.getExternalStorageDirectory().toString();
        File sd = new File(root + "/"+ Activity_DocumentScan.this.getPackageName() + "/" + "COAD" + "/");
        String imageFileName = TransNox + "_" + EntryNox + "_" + FileCode + ".png";
        File mypath = new File(sd, imageFileName);
        return mypath;
    }
}