package org.rmj.g3appdriver.etc.FileViewer.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.alamin5g.pdf.PDFView;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.etc.FileViewer.ViewModel.VMLoadPDF;
import org.rmj.g3appdriver.R;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;

import java.io.File;

public class Activity_PDFViewer extends AppCompatActivity {

    private VMLoadPDF mviewmodel;
    private LoadDialog poDialog;
    private PDFView pdf_viewer;
    private MessageBox poMessage;

    private String lsPassword;
    private boolean has_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pdfviewer);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );

        poMessage = new MessageBox(this);
        mviewmodel = new ViewModelProvider(this).get(VMLoadPDF.class);
        poDialog = new LoadDialog(this);
        pdf_viewer = findViewById(R.id.pdv_viewer);

        poMessage.initDialog();

        //check if pdf is password protected
        has_password = getIntent().hasExtra("has_password") && getIntent().getBooleanExtra("has_password", false);
        if (has_password){

            DialogPassword.RequirePassword(Activity_PDFViewer.this, new DialogPassword.PasswordCallback() {
                @Override
                public void onPasswordEntered(String password) {
                    lsPassword = password;
                    InitData();
                }
            });

        }else {
            InitData();
        }
    }

    private void InitData(){

        if (!getIntent().hasExtra("pdf_type")){

            poMessage.setTitle("PDF Viewer");
            poMessage.setMessage("Could not define PDF type");
            poMessage.setIcon(R.drawable.baseline_error_24);
            poMessage.setPositiveButton("Okay", new MessageBox.DialogButton() {
                @Override
                public void OnButtonClick(View view, AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
        }else {

            String pdf_type = getIntent().getStringExtra("pdf_type");
            if (pdf_type.equalsIgnoreCase("file") || pdf_type.equalsIgnoreCase("stream")){

                if (!getIntent().hasExtra("pdf_url")){

                    poMessage.setTitle("PDF Viewer");
                    poMessage.setMessage("No PDF file found");
                    poMessage.setIcon(R.drawable.baseline_error_24);
                    poMessage.setPositiveButton("Okay", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    return;
                }

                String pdf_url = getIntent().getStringExtra("pdf_url");

                if (pdf_type.equalsIgnoreCase("stream")){
                    StreamPDF(pdf_url);
                }else if (pdf_type.equalsIgnoreCase("file")){
                    DisplayPDFile(pdf_url);
                }

            }else {

                if (getIntent().getData() == null){

                    poMessage.setTitle("PDF Viewer");
                    poMessage.setMessage("No PDF file found");
                    poMessage.setIcon(R.drawable.baseline_error_24);
                    poMessage.setPositiveButton("Okay", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    return;
                }

                if (pdf_type.equalsIgnoreCase("uri")){
                    DisplayPDFUri(getIntent().getData());
                }else {

                    poMessage.setTitle("PDF Viewer");
                    poMessage.setMessage("Could not verify pdf file");
                    poMessage.setIcon(R.drawable.baseline_error_24);
                    poMessage.setPositiveButton("Okay", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
                }

            }
        }
    }

    private void StreamPDF(String fsURL){

        mviewmodel.StreamPDF(fsURL, new VMLoadPDF.OnStreamPDF() {
            @Override
            public void OnLoading() {
                poDialog.initDialog("PDF File", "Loading PDF file...", false);
                poDialog.show();
            }

            @Override
            public void OnSuccess(File foFile) {
                poDialog.dismiss();

                int pdf_index = 1;
                if (getIntent().hasExtra("pdf_index")){
                    pdf_index = Integer.parseInt(getIntent().getStringExtra("pdf_index"));
                }

                Uri uri = FileProvider.getUriForFile(Activity_PDFViewer.this, getPackageName() + ".provider", foFile);
                pdf_viewer.fromUri(uri)
                        .enableSwipe(true)
                        .enableDoubletap(true)
                        .swipeHorizontal(false)
                        .defaultPage(pdf_index);

                //initialize if password required
                if (has_password){
                    pdf_viewer.password(lsPassword);
                }
                pdf_viewer.load();
            }

            @Override
            public void OnFailed(String message) {
                poDialog.dismiss();
            }
        });

    }

    private void DisplayPDFUri(Uri foUri){

        int pdf_index = 1;
        if (getIntent().hasExtra("pdf_index")){
            pdf_index = Integer.parseInt(getIntent().getStringExtra("pdf_index"));
        }

        pdf_viewer.fromUri(foUri)
                .enableSwipe(true)
                .enableDoubletap(true)
                .swipeHorizontal(false)
                .defaultPage(pdf_index);

        //initialize if password required
        if (has_password){
            pdf_viewer.password(lsPassword);
        }
        pdf_viewer.load();

    }

    private void DisplayPDFile(String fsPath){

        mviewmodel.DisplayPDFile(new File(fsPath), new VMLoadPDF.OnLoadPDFile() {
            @Override
            public void OnLoading() {
                poDialog.initDialog("PDF File", "Loading PDF file...", false);
                poDialog.show();
            }

            @Override
            public void OnSuccess(File foFile) {
                poDialog.dismiss();

                int pdf_index = 1;
                if (getIntent().hasExtra("pdf_index")) {
                    pdf_index = Integer.parseInt(getIntent().getStringExtra("pdf_index"));
                }

                pdf_viewer.fromFile(foFile)
                        .enableSwipe(true)
                        .enableDoubletap(true)
                        .swipeHorizontal(false)
                        .defaultPage(pdf_index);

                //initialize if password required
                if (has_password){
                    pdf_viewer.password(lsPassword);
                }
                pdf_viewer.load();
            }

            @Override
            public void OnFailed(String message) {
                poDialog.dismiss();
            }
        });
    }

    public static class DialogPassword {

        public interface PasswordCallback {
            void onPasswordEntered(String password);
        }

        public static void RequirePassword(Context context, PasswordCallback callback) {
            // Create an EditText for password input
            final TextInputEditText input = new TextInputEditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setHint("Enter PDF password");

            new AlertDialog.Builder(context)
                    .setTitle("Password Required")
                    .setMessage("This PDF is protected. Please enter the password:")
                    .setView(input)
                    .setPositiveButton("OK", (dialog, which) -> {
                        String password = input.getText().toString().trim();
                        if (!password.isEmpty()) {
                            callback.onPasswordEntered(password);
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

}