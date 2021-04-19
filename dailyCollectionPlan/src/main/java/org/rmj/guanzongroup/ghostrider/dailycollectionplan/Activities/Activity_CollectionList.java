package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.CollectionAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogAccountDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogConfirmPost;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogImportDCP;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogAddCollection;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.Dialog_ClientSearch;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_CollectionList extends AppCompatActivity implements ViewModelCallback, VMCollectionList.OnDownloadCollection {
    private static final String TAG = Activity_CollectionList.class.getSimpleName();

    private static final int MOBILE_DIALER = 104;

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

    private List<DDCPCollectionDetail.CollectionDetail> plDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        mViewModel = new ViewModelProvider(this).get(VMCollectionList.class);
        expCollectDetl = new JSONArray();
        initWidgets();

        mViewModel.getCollectionLastEntry().observe(this, collectionDetail -> {
            // Added +1 for entry nox to increment the value which will be
            // use when inserting new AR Client info to database
            try {
//                Log.e("", "col entry no " + collectionDetail.getEntryNox());
                int lnEntry = 1 + collectionDetail.getEntryNox();
                mViewModel.setParameter(collectionDetail.getTransNox(), lnEntry);
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
                btnDownload.setOnClickListener(v -> showDownloadDcp());
                btnImport.setOnClickListener(v -> showImportFromFileDcp());
            }

            CollectionAdapter loAdapter = new CollectionAdapter(collectionDetails, new CollectionAdapter.OnItemClickListener() {
                @Override
                public void OnClick(int position) {
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
            try {
                loDialog = new DialogAddCollection(Activity_CollectionList.this);
                loDialog.initDialog(new DialogAddCollection.OnDialogButtonClickListener() {
                    @Override
                    public void OnDownloadClick(Dialog Dialog, String clientName, String fsType) {
                        Dialog.dismiss();
                        if(fsType.equalsIgnoreCase("0")) {
                            Dialog_ClientSearch loClient = new Dialog_ClientSearch(Activity_CollectionList.this);
                            mViewModel.importARClientInfo(clientName, new VMCollectionList.OnDownloadClientList() {
                                @Override
                                public void OnDownload() {
                                    poDialogx.initDialog("Daily Collection Plan", "Searching client. Please wait...", false);
                                    poDialogx.show();
                                }

                                @Override
                                public void OnSuccessDownload(List<EDCPCollectionDetail> collectionDetails) {
                                    poDialogx.dismiss();
                                    loClient.initDialog(collectionDetails, new Dialog_ClientSearch.OnClientSelectListener() {
                                        @Override
                                        public void OnSelect(AlertDialog clientDialog, EDCPCollectionDetail detail) {
                                            /**
                                             * validation if user accidentally tap on list on
                                             *
                                             */
                                            poMessage.initDialog();
                                            poMessage.setTitle("Add Collection");
                                            poMessage.setMessage("Add " + detail.getFullName() + " with account number " +
                                                    detail.getAcctNmbr() + " to list of collection?");
                                            poMessage.setPositiveButton("Yes", (view, msgDialog) -> {
                                                clientDialog.dismiss();
                                                mViewModel.insertAddedCollectionDetail(detail, message -> {
                                                    GToast.CreateMessage(Activity_CollectionList.this, message, GToast.INFORMATION).show();
                                                    msgDialog.dismiss();
                                                });
                                            });
                                            poMessage.setNegativeButton("No", (view, msgDialog) -> msgDialog.dismiss());
                                            poMessage.show();
                                        }

                                        @Override
                                        public void OnCancel(AlertDialog clientDialog) {
                                            clientDialog.dismiss();

                                            /*
                                            Show Add collection dialog if user cancels search client list
                                             */
                                            loDialog.show();
                                        }
                                    });
                                    loClient.show();
                                }

                                @Override
                                public void OnFailedDownload(String message) {
                                    poDialogx.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Daily Collection Plan");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                    poMessage.show();
                                }
                            });
                        } else if(fsType.equalsIgnoreCase("1")) {
                            Dialog_ClientSearch loClient = new Dialog_ClientSearch(Activity_CollectionList.this);
                            mViewModel.importInsuranceInfo(clientName, new VMCollectionList.OnDownloadClientList() {
                                @Override
                                public void OnDownload() {
                                    poDialogx.initDialog("Daily Collection Plan", "Searching client. Please wait...", false);
                                    poDialogx.show();
                                }

                                @Override
                                public void OnSuccessDownload(List<EDCPCollectionDetail> collectionDetails) {
                                    poDialogx.dismiss();
                                    loClient.initDialog(collectionDetails, new Dialog_ClientSearch.OnClientSelectListener() {
                                        @Override
                                        public void OnSelect(AlertDialog clientDialog, EDCPCollectionDetail detail) {
                                            /**
                                             * validation if user accidentally tap on list on
                                             *
                                             */
                                            poMessage.initDialog();
                                            poMessage.setTitle("Add Collection");
                                            poMessage.setMessage("Add " + detail.getFullName() + " with account number " +
                                                    detail.getAcctNmbr() + " to list of collection?");
                                            poMessage.setPositiveButton("Yes", (view, msgDialog) -> {
                                                clientDialog.dismiss();
                                                mViewModel.insertAddedCollectionDetail(detail, message -> {
                                                    GToast.CreateMessage(Activity_CollectionList.this, message, GToast.INFORMATION).show();
                                                    msgDialog.dismiss();
                                                });
                                            });
                                            poMessage.setNegativeButton("No", (view, msgDialog) -> msgDialog.dismiss());
                                            poMessage.show();
                                        }

                                        @Override
                                        public void OnCancel(AlertDialog clientDialog) {
                                            clientDialog.dismiss();

                                            /*
                                            Show Add collection dialog if user cancels search client list
                                             */
                                            loDialog.show();
                                        }
                                    });
                                    loClient.show();
                                }

                                @Override
                                public void OnFailedDownload(String message) {
                                    poDialogx.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Daily Collection Plan");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                    poMessage.show();
                                }
                            });
                        }
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
        } else if(item.getItemId() == R.id.action_menu_post_collection){
            boolean hasUnTag = false;
            if(plDetail.size()>0){
                for(int x = 0; x < plDetail.size(); x++){
                    if(plDetail.get(x).sRemCodex == null){
                        hasUnTag = true;
                    }
                }
            }
            if(hasUnTag){
                DialogConfirmPost loPost = new DialogConfirmPost(Activity_CollectionList.this);
                loPost.iniDialog(new DialogConfirmPost.DialogPostUnfinishedListener() {
                    @Override
                    public void OnConfirm(AlertDialog dialog, String Remarks) {
                        dialog.dismiss();
                        mViewModel.PostLRCollectionDetail(Remarks, new ViewModelCallback() {
                            @Override
                            public void OnStartSaving() {
                                poDialogx.initDialog("Daily Collection Plan", "Posting collection details. Please wait...", false);
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

                        mViewModel.getExportDataList(Remarks, new VMCollectionList.FileManagerCallBack() {
                            @Override
                            public void OnJSONCreated(JSONObject loJson) {
                                if(exportCollectionList(loJson)) {
                                    Log.e("Success","Success");
                                } else {
                                    Log.e("Failed","Failed");
                                }
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
                    }

                    @Override
                    public void OnCancel(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                loPost.show();
            } else {
                mViewModel.PostLRCollectionDetail("", new ViewModelCallback() {
                    @Override
                    public void OnStartSaving() {
                        poDialogx.initDialog("Daily Collection Plan", "Posting collection details. Please wait...", false);
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

                mViewModel.getExportDataList("", new VMCollectionList.FileManagerCallBack() {
                    @Override
                    public void OnJSONCreated(JSONObject loJson) {
                        if(exportCollectionList(loJson)) {
                            Log.e("Success","Success");
                        } else {
                            Log.e("Failed","Failed");
                        }
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
            }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MOBILE_DIALER){
            if(resultCode == RESULT_OK){

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
    public void showDownloadDcp(){
        mViewModel.DownloadDcp(AppConstants.CURRENT_DATE, Activity_CollectionList.this);
    }

    public void showImportFromFileDcp() {
        DialogImportDCP loPost = new DialogImportDCP(Activity_CollectionList.this);
        loPost.iniDialog(new DialogImportDCP.DialogPostUnfinishedListener() {
            @Override
            public void OnConfirm(AlertDialog dialog, String fileName) {
                dialog.dismiss();
                try {
                    mViewModel.importDCPFile(fileName, new ViewModelCallback() {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnCancel(AlertDialog dialog) {
                dialog.dismiss();
            }
        });
        loPost.show();
    }

    private boolean exportCollectionList(JSONObject expCollectDetl) {
        // TODO: Exporting Method
        try {
            fileContent = expCollectDetl.toString();
            if(!fileContent.equalsIgnoreCase("")) {
                String root = Environment.getExternalStorageDirectory().toString();
                String lsPublicFoldx = AppConstants.APP_PUBLIC_FOLDER;
                String lsSubFoldx = AppConstants.SUB_FOLDER_EXPORTS;
                File sd = new File(root + lsPublicFoldx + lsSubFoldx + "/");
                if (!sd.exists()) {
                    sd.mkdirs();
                }
                File myExternalFile = new File(sd, FILENAME+FILE_TYPE);
                Log.e("Export Directory", myExternalFile.toString());
                if(myExternalFile.exists()) {
                    boolean res = myExternalFile.delete();
                    Log.e("Export Message", "File already exists(" + res + "). Overwritten the previous file.");
                }
                FileOutputStream fos = null;
                fos = new FileOutputStream(myExternalFile);
                fos.write(fileContent.getBytes());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}