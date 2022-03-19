/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.AppAssistantConfig;
import org.rmj.g3appdriver.utils.DayCheck;
import org.rmj.g3appdriver.utils.FileRemover;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.CollectionAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogAccountDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogConfirmPost;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogAddCollection;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.Dialog_ClientSearch;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.Dialog_DebugEntry;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Service.GLocatorService;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Help;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_CollectionList extends AppCompatActivity implements ViewModelCallback, VMCollectionList.OnDownloadCollection {
    private static final String TAG = Activity_CollectionList.class.getSimpleName();

    private static final int MOBILE_DIALER = 104;
    private static final int PICK_TEXT_FILE = 105;
    private static final int EXPORT_TEXT_FILE = 106;

    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private DialogAddCollection loDialog;

    private VMCollectionList mViewModel;

    private TextInputEditText txtSearch;
    private TextInputLayout tilSearch;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private TextView lblBranch, lblAddxx, lblDate;
    private JSONArray expCollectDetl;

    private MaterialButton btnDownload, btnImport;
    private LinearLayout lnImportPanel;
    private TextView lblNoName;

    private String FILENAME;
    private final String FILE_TYPE = "-mob.txt";
    private String fileContent= "";

    private JSONObject poDcpData;

    private List<DDCPCollectionDetail.CollectionDetail> plDetail;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        mViewModel = new ViewModelProvider(this).get(VMCollectionList.class);
        expCollectDetl = new JSONArray();
        initWidgets();
        mViewModel.getEmplopyeInfo().observe(this, eEmployeeInfo ->{
            try {
                mViewModel.setEmployeeID(eEmployeeInfo.getEmployID());
            } catch(Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getCollectionLastEntry().observe(this, collectionDetail -> {
            // Added +1 for entry nox to increment the value which will be
            // use when inserting new AR Client info to database
            try {
                if(collectionDetail != null){
                    int lnEntry = 1 + collectionDetail.getEntryNox();
                    mViewModel.setParameter(collectionDetail.getTransNox(), lnEntry);
                }
            } catch (Exception e){
                e.printStackTrace();
                Log.e("Exception", e.getMessage());
            }
        });

        mViewModel.getCollectionMasterList().observe(this, edcpCollectionMasters -> {
            mViewModel.setCollectionMasterList(edcpCollectionMasters);
        });

        mViewModel.getCollectionDetailForPosting().observe(this, collectionDetails -> {
            try {
                plDetail = collectionDetails;
                mViewModel.setCollectionListForPosting(collectionDetails);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getCollectionList().observe(this, collectionDetails -> {
            if(collectionDetails.size() > 0) {
                tilSearch.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                lnImportPanel.setVisibility(View.GONE);
                FILENAME = collectionDetails.get(0).getTransNox();
                Log.e("Master List TransNox",collectionDetails.get(0).getTransNox() );
            } else {
                tilSearch.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                lnImportPanel.setVisibility(View.VISIBLE);
                btnDownload.setOnClickListener(v -> {
                    showDownloadDcp();
                });
                btnImport.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("text/plain");

                    // Optionally, specify a URI for the file that should appear in the
                    // system file picker when it loads.
                    try {
                        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, new URI(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    startActivityForResult(intent, PICK_TEXT_FILE);
                });

                // Remove old files every monday (with confirmation)
                deleteOldFileSchedule();
            }

            CollectionAdapter loAdapter = new CollectionAdapter(collectionDetails, new CollectionAdapter.OnItemClickListener() {
                @Override
                public void OnClick(int position) {
                    if (!AppAssistantConfig.getInstance(Activity_CollectionList.this).getASSIST_DCP_TRANSACTION()){
                        Intent intent = new Intent(Activity_CollectionList.this, Activity_Help.class);
                        intent.putExtra("help", AppConstants.INTENT_TRANSACTION_DCP);
                        startActivityForResult(intent, AppConstants.INTENT_TRANSACTION_DCP);
                        DCP_Constants.collectionPos = position;
                    }else{
                        showTransaction(position,collectionDetails);
                    }

                }

                @Override
                public void OnMobileNoClickListener(String MobileNo) {
                    Intent mobileIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", MobileNo, null));
                    startActivityForResult(mobileIntent, MOBILE_DIALER);
                }

                @Override
                public void OnAddressClickListener(String Address, String[] args) {
                    //TODO: Future update... add google map API for auto searching address location...
                }

                @Override
                public void OnActionButtonClick() {
                    //TODO: Future update
                }
            });

            txtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        loAdapter.getCollectionSearch().filter(charSequence.toString());
                        loAdapter.notifyDataSetChanged();
                        if(loAdapter.getItemCount() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            lblNoName.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            lblNoName.setVisibility(View.GONE);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(loAdapter);
            recyclerView.getRecycledViewPool().clear();
            loAdapter.notifyDataSetChanged();
        });

        mViewModel.getUserBranchInfo().observe(Activity_CollectionList.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddxx.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getCollectionDetailForPosting().observe(Activity_CollectionList.this, collectionDetails -> mViewModel.setCollectionListForPosting(collectionDetails));

        mViewModel.getAddressRequestList().observe(Activity_CollectionList.this, eAddressUpdates -> mViewModel.setAddressRequestList(eAddressUpdates));

        mViewModel.getMobileRequestList().observe(Activity_CollectionList.this, eMobileUpdates -> mViewModel.setMobileRequestList(eMobileUpdates));
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_collectionList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtSearch = findViewById(R.id.txt_collectionSearch);
        tilSearch = findViewById(R.id.til_collectionSearch);

        btnDownload = findViewById(R.id.btn_download);
        btnImport = findViewById(R.id.btn_import);
        lnImportPanel = findViewById(R.id.ln_import_panel);
        lblNoName = findViewById(R.id.txt_no_name);

        recyclerView = findViewById(R.id.recyclerview_collectionList);
        layoutManager = new LinearLayoutManager(Activity_CollectionList.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddxx = findViewById(R.id.lbl_headerAddress);
        lblDate = findViewById(R.id.lbl_headerDate);

        poDialogx = new LoadDialog(Activity_CollectionList.this);
        poMessage = new MessageBox(Activity_CollectionList.this);

        plDetail = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_dcp_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        } else if(item.getItemId() == R.id.action_menu_add_collection){
            if (!AppAssistantConfig.getInstance(Activity_CollectionList.this).getASSIST_DCP_ADD()){
                Intent intent = new Intent(Activity_CollectionList.this, Activity_Help.class);
                intent.putExtra("help", AppConstants.INTENT_ADD_COLLECTION_DCP);
                startActivityForResult(intent, AppConstants.INTENT_ADD_COLLECTION_DCP);
            }else {
                showAddDcpCollection();
            }
        } else if (item.getItemId() == R.id.action_menu_post_collection) {
            showPostCollection();
        } else if (item.getItemId() == R.id.action_menu_image_log) {
            Intent loIntent = new Intent(Activity_CollectionList.this, Activity_ImageLog.class);
            startActivity(loIntent);
        } else {
            mViewModel.ValidatePostDCP(new VMCollectionList.ValidateDCPTask.OnValidateCallback() {
                @Override
                public void OnSuccess(boolean hasNV, String args) {
                    if(hasNV){
                        DialogConfirmPost loPost = new DialogConfirmPost(Activity_CollectionList.this);
                        loPost.iniDialog(new DialogConfirmPost.DialogPostUnfinishedListener() {
                            @Override
                            public void OnConfirm(AlertDialog dialog, String Remarks) {
                                dialog.dismiss();

                                mViewModel.UpdateNotVisitedCollections(Remarks, new VMCollectionList.OnTransactionCallback() {
                                    @Override
                                    public void onLoading() {
                                        poDialogx = new LoadDialog(Activity_CollectionList.this);
                                        poDialogx.initDialog("Daily Collection Plan", "Updating unvisited collections.", false);
                                        poDialogx.show();
                                    }

                                    @Override
                                    public void onSuccess(String fsMessage) {
                                        poDialogx.dismiss();
                                        startIntentExportDCPPost();
                                    }

                                    @Override
                                    public void onFailed(String fsMessage) {
                                        poDialogx.dismiss();
                                        poDialogx.dismiss();
                                        poMessage.initDialog();
                                        poMessage.setTitle("Daily Collection Plan");
                                        poMessage.setMessage(fsMessage);
                                        poMessage.setPositiveButton("Okay", (view, dialog) -> {
                                            dialog.dismiss();
                                        });
                                        poMessage.show();
                                    }
                                });

                            }

                            @Override
                            public void OnCancel(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                        loPost.show();
                    } else {
                        startIntentExportDCPPost();
                    }
                }

                @Override
                public void OnFailed(String message) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MOBILE_DIALER){
            if(resultCode == RESULT_OK){

            }
        }
        else if (requestCode == AppConstants.INTENT_DOWNLOAD_DCP && resultCode == RESULT_OK){
            showDownloadDcp();
        }else if (requestCode == AppConstants.INTENT_ADD_COLLECTION_DCP && resultCode == RESULT_OK){
            showAddDcpCollection();
        }else if (requestCode == AppConstants.INTENT_TRANSACTION_DCP && resultCode == RESULT_OK){
            mViewModel.getCollectionList().observe(this, collectionDetails -> {
                showTransaction(DCP_Constants.collectionPos, collectionDetails);
            });
        }else if(requestCode == PICK_TEXT_FILE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            importDataFromFile(uri);
        }else if(requestCode == EXPORT_TEXT_FILE){
            if(mViewModel.isExportedDCP() && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                exportCollectionList(uri, poDcpData);
                mViewModel.setExportedDCP(false);
            } else if(mViewModel.isExportedDCP() && resultCode == RESULT_CANCELED){
                poDialogx.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage("Exporting DCP file for posting has been canceled. Please export your collection file for your collection today.");
                poMessage.setPositiveButton("Export", (view, dialog) -> {
                    dialog.dismiss();
                    startIntentExportDCPPost();
                });
                poMessage.show();
            } else if(!mViewModel.isExportedDCP() && resultCode == RESULT_CANCELED){
                poDialogx.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage("Exporting DCP file for posting has been canceled.");
                poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                poMessage.show();
            }
        }
    }

    @Override
    public void OnStartSaving() {
        poDialogx.initDialog("Add Collection", "Downloading client info. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void OnSuccessResult(String[] args) {
        poDialogx.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("Add Collection");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        poDialogx.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("Add Collection");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }

    @Override
    public void OnDownload() {
        poDialogx.initDialog("Daily Collection Plan","Downloading collection list. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void OnSuccessDownload() {
        poDialogx.dismiss();
        startService(new Intent(Activity_CollectionList.this, GLocatorService.class));
    }

    @Override
    public void OnDownloadFailed(String message) {
        poDialogx.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("Daily Collection Plan");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            if (message.equalsIgnoreCase("Record not found")){
                dialog.dismiss();
            }else {
                dialog.dismiss();
            }
        });
        poMessage.show();

    }
    public void showPostCollection(){
        mViewModel.ValidatePostDCP(new VMCollectionList.ValidateDCPTask.OnValidateCallback() {
            @Override
            public void OnSuccess(boolean hasNV, String args) {
                poMessage.initDialog();
                poMessage.setPositiveButton("Post", (view, dialog) -> {
                    dialog.dismiss();
                    postDCPTransaction(hasNV);
                });
                poMessage.setNegativeButton("Cancel", (view, dialog) -> dialog.dismiss());
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage(args);
                poMessage.show();
            }

            @Override
            public void OnFailed(String message) {
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                });
                poMessage.show();
            }
        });
    }

    public void showTransaction(int position, List<EDCPCollectionDetail> collectionDetails){
        DialogAccountDetail loDialog = new DialogAccountDetail(Activity_CollectionList.this);
        loDialog.initAccountDetail(Activity_CollectionList.this ,collectionDetails.get(position), (dialog, remarksCode) -> {
            Intent loIntent = new Intent(Activity_CollectionList.this, Activity_Transaction.class);
            loIntent.putExtra("remarksx", remarksCode);
            loIntent.putExtra("transnox", collectionDetails.get(position).getTransNox());
            loIntent.putExtra("entrynox", collectionDetails.get(position).getEntryNox());
            loIntent.putExtra("accntnox", collectionDetails.get(position).getAcctNmbr());
            startActivity(loIntent);
            dialog.dismiss();
        });
        loDialog.show();
    }

    public void showAddDcpCollection(){
        try {
            loDialog = new DialogAddCollection(Activity_CollectionList.this);
            loDialog.initDialog(new DialogAddCollection.OnDialogButtonClickListener() {
                @Override
                public void OnDownloadClick(Dialog Dialog, String clientName, String fsType) {
                    Dialog.dismiss();
                    /** Comment this out to use DCPManager function */
//                    if(fsType.equalsIgnoreCase("0")) {
                    Dialog_ClientSearch loClient = new Dialog_ClientSearch(Activity_CollectionList.this);
                    mViewModel.getSearchList(clientName, new VMCollectionList.OnCollectionNameSearch() {
                        @Override
                        public void onLoading() {
                            poDialogx.initDialog("Daily Collection Plan", "Searching client. Please wait...", false);
                            poDialogx.show();
                        }

                        @Override
                        public void onSuccess(List<EDCPCollectionDetail> foDetail) {
                            poDialogx.dismiss();
                            loClient.initDialog(foDetail, new Dialog_ClientSearch.OnClientSelectListener() {
                                @Override
                                public void OnSelect(AlertDialog clientDialog, EDCPCollectionDetail detail) {
                                    /** Validation if user accidentally tap on list on */
                                    poMessage.initDialog();
                                    poMessage.setTitle("Add Collection");
                                    poMessage.setMessage("Add " + detail.getFullName() + " with account number " +
                                            detail.getAcctNmbr() + " to list of collection?");
                                    poMessage.setPositiveButton("Yes", (view, msgDialog) -> {
                                        clientDialog.dismiss();
                                        mViewModel.AddCollection(detail, new VMCollectionList.OnTransactionCallback() {
                                            @Override
                                            public void onLoading() {
                                                msgDialog.dismiss();
                                                poDialogx.initDialog("Daily Collection Plan", "Adding client to DCP list. Please wait...", false);
                                                poDialogx.show();
                                            }

                                            @Override
                                            public void onSuccess(String fsMessage) {
                                                poDialogx.dismiss();
                                                poMessage.initDialog();
                                                poMessage.setTitle("Daily Collection Plan");
                                                poMessage.setMessage(fsMessage);
                                                poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                                poMessage.show();
                                            }

                                            @Override
                                            public void onFailed(String fsMessage) {
                                                poDialogx.dismiss();
                                                poMessage.initDialog();
                                                poMessage.setTitle("Daily Collection Plan");
                                                poMessage.setMessage(fsMessage);
                                                poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                                poMessage.show();
                                            }
                                        });
                                    });
                                    poMessage.setNegativeButton("No", (view, msgDialog) -> msgDialog.dismiss());
                                    poMessage.show();
                                }

                                @Override
                                public void OnCancel(AlertDialog clientDialog) {
                                    clientDialog.dismiss();
                                    /** Show Add collection dialog if user cancels search client list */
                                    loDialog.show();
                                }
                            });
                            loClient.show();
                        }

                        @Override
                        public void onFailed(String fsMessage) {
                            poDialogx.dismiss();
                            poMessage.initDialog();
                            poMessage.setTitle("Daily Collection Plan");
                            poMessage.setMessage(fsMessage);
                            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                            poMessage.show();
                        }

                    });
                        /** Comment the following out to use DCPManager function */
//                    }
//                    else if(fsType.equalsIgnoreCase("1")) {
//                        Dialog_ClientSearch loClient = new Dialog_ClientSearch(Activity_CollectionList.this);
//                        mViewModel.importInsuranceInfo(clientName, new VMCollectionList.OnDownloadClientList() {
//                            @Override
//                            public void OnDownload() {
//                                poDialogx.initDialog("Daily Collection Plan", "Searching client. Please wait...", false);
//                                poDialogx.show();
//                            }
//
//                            @Override
//                            public void OnSuccessDownload(List<EDCPCollectionDetail> collectionDetails) {
//                                poDialogx.dismiss();
//                                loClient.initDialog(collectionDetails, new Dialog_ClientSearch.OnClientSelectListener() {
//                                    @Override
//                                    public void OnSelect(AlertDialog clientDialog, EDCPCollectionDetail detail) {
//                                        /**
//                                         * validation if user accidentally tap on list on
//                                         *
//                                         */
//                                        poMessage.initDialog();
//                                        poMessage.setTitle("Add Collection");
//                                        poMessage.setMessage("Add " + detail.getFullName() + " with account number " +
//                                                detail.getAcctNmbr() + " to list of collection?");
//                                        poMessage.setPositiveButton("Yes", (view, msgDialog) -> {
//                                            clientDialog.dismiss();
//                                            mViewModel.insertAddedCollectionDetail(detail, message -> {
//                                                GToast.CreateMessage(Activity_CollectionList.this, message, GToast.INFORMATION).show();
//                                                msgDialog.dismiss();
//                                            });
//                                        });
//                                        poMessage.setNegativeButton("No", (view, msgDialog) -> msgDialog.dismiss());
//                                        poMessage.show();
//                                    }
//
//                                    @Override
//                                    public void OnCancel(AlertDialog clientDialog) {
//                                        clientDialog.dismiss();
//
//                                            /*
//                                            Show Add collection dialog if user cancels search client list
//                                             */
//                                        loDialog.show();
//                                    }
//                                });
//                                loClient.show();
//                            }
//
//                            @Override
//                            public void OnFailedDownload(String message) {
//                                poDialogx.dismiss();
//                                poMessage.initDialog();
//                                poMessage.setTitle("Daily Collection Plan");
//                                poMessage.setMessage(message);
//                                poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
//                                poMessage.show();
//                            }
//                        });
//                    }
                }

                @Override
                public void OnCancel(Dialog Dialog) {
                    Dialog.dismiss();
                }
            });
            loDialog.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDownloadDcp(){
        if(!mViewModel.isDebugMode()){
            mViewModel.DownloadDcp(new AppConstants().CURRENT_DATE, Activity_CollectionList.this);
        } else {
            Dialog_DebugEntry loDebug = new Dialog_DebugEntry(Activity_CollectionList.this);
            loDebug.iniDialog(args -> {
                try {
                    JSONObject loJson = new JSONObject(args);
//                    mViewModel.setEmployeeID(loJson.getString("employid"));
//                    mViewModel.DownloadDcp(loJson.getString("date"), Activity_CollectionList.this);
                    mViewModel.ImportDcpMaster(loJson.getString("employid"),
                            loJson.getString("date"),
                            new VMCollectionList.OnTransactionCallback() {
                                @Override
                                public void onLoading() {
                                    poDialogx.initDialog("Daily Collection Plan",
                                            "Download DCP List ... Please wait.", false);
                                    poDialogx.show();
                                }

                                @Override
                                public void onSuccess(String fsMessage) {
                                    poDialogx.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Daily Collection Plan");
                                    poMessage.setMessage(fsMessage);
                                    poMessage.setPositiveButton("Export", (view, dialog) -> {
                                        dialog.dismiss();
                                    });
                                }

                                @Override
                                public void onFailed(String fsMessage) {
                                    poDialogx.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Daily Collection Plan");
                                    poMessage.setMessage(fsMessage);
                                    poMessage.setPositiveButton("Export", (view, dialog) -> {
                                        dialog.dismiss();
                                    });
                                }
                            });
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
            loDebug.show();
        }
    }

    private boolean exportCollectionList(Uri uri, JSONObject expCollectDetl) {
        // TODO: Exporting Method
        try {
            ParcelFileDescriptor pfd = getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(expCollectDetl.toString().getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void importDataFromFile(Uri uri){
        mViewModel.importDCPFile(uri, new ViewModelCallback() {
            @Override
            public void OnStartSaving() {
                poDialogx.initDialog("Daily Collection Plan", "Importing DCP List from file. Please wait...", false);
                poDialogx.show();
            }

            @Override
            public void OnSuccessResult(String[] args) {
                poDialogx.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage(args[0]);
                poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                poMessage.show();
            }

            @Override
            public void OnFailedResult(String message) {
                poDialogx.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                poMessage.show();
            }
        });
    }

    private boolean deleteOldFileSchedule() {
        if(DayCheck.isMonday())
            return FileRemover.execute(Environment.getExternalStorageDirectory() + "/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/Exported Files");
        else
            return false;
    }

    private void postDCPTransaction(boolean HasNV){
        if(HasNV){
            DialogConfirmPost loPost = new DialogConfirmPost(Activity_CollectionList.this);
            loPost.iniDialog(new DialogConfirmPost.DialogPostUnfinishedListener() {
                @Override
                public void OnConfirm(AlertDialog dialog, String Remarks) {
                    dialog.dismiss();

                    mViewModel.UpdateNotVisitedCollections(Remarks, new VMCollectionList.OnTransactionCallback() {
                        @Override
                        public void onLoading() {
                            poDialogx = new LoadDialog(Activity_CollectionList.this);
                            poDialogx.initDialog("Daily Collection Plan", "Updating unvisited collections.", false);
                            poDialogx.show();
                        }

                        @Override
                        public void onSuccess(String fsMessage) {
                            poDialogx.dismiss();

                            Intent loIntent = new Intent(Activity_CollectionList.this, Activity_PostDcp.class);
                            loIntent.putExtra("sRemarksx", Remarks);
                            startActivity(loIntent);
                        }

                        @Override
                        public void onFailed(String fsMessage) {
                            poDialogx.dismiss();
                            poDialogx.dismiss();
                            poMessage.initDialog();
                            poMessage.setTitle("Daily Collection Plan");
                            poMessage.setMessage(fsMessage);
                            poMessage.setPositiveButton("Okay", (view, dialog) -> {
                                dialog.dismiss();
                            });
                            poMessage.show();
                        }
                    });

                }

                @Override
                public void OnCancel(AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            loPost.show();
        } else {
            Intent loIntent = new Intent(Activity_CollectionList.this, Activity_PostDcp.class);
            loIntent.putExtra("sRemarksx", "");
            startActivity(loIntent);

        }
    }

    private void startIntentExportDCPPost(){
        mViewModel.getExportDataList("", new VMCollectionList.FileManagerCallBack() {
            @Override
            public void OnJSONCreated(JSONObject loJson) {
                poDcpData = loJson;
            } @Override
            public void OnStartSaving() {
            }

            @Override
            public void OnSuccessResult(String[] args) {
            }

            @Override
            public void OnFailedResult(String message) {
            }
        });

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, FILENAME + "-mob.txt");

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
        Uri loDocs = Uri.parse(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)));
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, loDocs);
        if(mViewModel.isExportedDCP()) {
            startActivityForResult(intent, EXPORT_TEXT_FILE);
        }
    }
}