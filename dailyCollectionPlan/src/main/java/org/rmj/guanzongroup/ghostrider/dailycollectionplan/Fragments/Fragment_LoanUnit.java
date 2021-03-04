package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.OnBirthSetListener;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMLoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

import static android.app.Activity.RESULT_OK;

public class Fragment_LoanUnit extends Fragment implements ViewModelCallback {

    private AlertDialog poDialogx;
    private VMLoanUnit mViewModel;
    private String spnCivilStatsPosition = "-1";
    private String genderPosition = "-1", CollId;
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo,lblLuImgPath;
    private TextInputEditText tieLName, tieFName, tieMName, tieSuffix;
    private TextInputEditText tieHouseNo, tieStreet;
    private AutoCompleteTextView tieTown, tieBrgy,tieBPlace;
    private RadioGroup rgGender;
    private TextInputEditText tieBDate, tiePhone, tieMobileNo, tieEmailAdd;
    private MaterialButton btnLUSubmit;
    private AutoCompleteTextView spnCivilStats;
    private LoanUnitModel infoModel;
    private MessageBox poMessage;
    public static int CAMERA_REQUEST_CODE = 1231;

    public static String mCurrentPhotoPath;
    private ImageFileCreator poFilexx;
    private final String CAMERA_USAGE = "LoanUnit";
    public static ContentResolver contentResolver;

    //Parameters From Activity_Transaction
    private String TransNox;
    private int EntryNox;
    private String Remarksx;
    private String AccntNox;
    private EImageInfo poImageInfo;
    public static Fragment_LoanUnit newInstance() {
        return new Fragment_LoanUnit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_unit, container, false);
        poMessage = new MessageBox(getContext());
        infoModel = new LoanUnitModel();
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v) {

        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);
        lblAccNo = v.findViewById(R.id.tvAccountNo);
        lblClientNm = v.findViewById(R.id.tvClientname);
        lblTransNo = v.findViewById(R.id.lbl_dcpTransNo);
//        Full Name
        tieLName = v.findViewById(R.id.tie_lun_lName);
        tieFName = v.findViewById(R.id.tie_lun_fName);
        tieMName = v.findViewById(R.id.tie_lun_middName);
        tieSuffix = v.findViewById(R.id.tie_lun_suffix);
//        Address
        tieHouseNo = v.findViewById(R.id.tie_lun_houseNo);
        tieStreet = v.findViewById(R.id.tie_lun_street);
        tieTown = v.findViewById(R.id.tie_lun_town);
        tieBrgy = v.findViewById(R.id.tie_lun_brgy);
//        Gender
        rgGender = v.findViewById(R.id.rg_dcp_gender);
//        Other Info
        spnCivilStats = v.findViewById(R.id.spn_lun_cstatus);

        tieBDate = v.findViewById(R.id.tie_lun_bdate);
        tieBPlace = v.findViewById(R.id.tie_lun_bplace);
        tiePhone = v.findViewById(R.id.tie_lun_phone);
        tieMobileNo = v.findViewById(R.id.tie_lun_mobileNp);
        tieEmailAdd = v.findViewById(R.id.tie_lun_email);
        btnLUSubmit = v.findViewById(R.id.btn_dcpSubmit);

        btnLUSubmit.setOnClickListener(view -> {
            try {
                submitLUn(Remarksx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @SuppressLint({"NewApi", "ResourceType"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Initialize Parameters...
        Remarksx = Activity_Transaction.getInstance().getRemarksCode();
        TransNox = Activity_Transaction.getInstance().getTransNox();
        EntryNox = Activity_Transaction.getInstance().getEntryNox();
        AccntNox = Activity_Transaction.getInstance().getAccntNox();

        mViewModel = new ViewModelProvider(this).get(VMLoanUnit.class);
        mViewModel.setParameter(TransNox, EntryNox);
        mViewModel.getSpnCivilStats().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCivilStats.setAdapter(stringArrayAdapter));

        mViewModel.getCollectionMaster().observe(getViewLifecycleOwner(), s ->  {
            CollId = s.getCollctID();
            poFilexx = new ImageFileCreator(getActivity(), DCP_Constants.FOLDER_NAME, DCP_Constants.TRANSACT_LUn, AccntNox);

        });
        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblAccNo.setText(collectionDetail.getAcctNmbr());
                lblClientNm.setText(collectionDetail.getFullName());
                lblTransNo.setText(collectionDetail.getTransNox());
                mViewModel.setCurrentCollectionDetail(collectionDetail);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getUserBranchEmployee().observe(getViewLifecycleOwner(), eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddress.setText(eBranchInfo.getAddressx());
        });

        // Province
        mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
            String[] townProvince = new String[townProvinceInfos.size()];
            for(int x = 0; x < townProvinceInfos.size(); x++){
                townProvince[x] = townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName;
            }
            ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, townProvince);
            tieTown.setAdapter(loAdapter);
            tieBPlace.setAdapter(loAdapter);
        });
        tieTown.setOnItemClickListener((adapterView, view, i, l) -> {
            String lsTown = tieTown.getText().toString();
            String[] town = lsTown.split(", ");
            mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
                for(int x = 0; x < townProvinceInfos.size(); x++){
                    if(town[0].equalsIgnoreCase(townProvinceInfos.get(x).sTownName)){
                        mViewModel.setTownID(townProvinceInfos.get(x).sTownIDxx);

                        infoModel.setLuTown(tieTown.getText().toString());
                        break;
                    }
                }

                mViewModel.getBarangayNameList().observe(getViewLifecycleOwner(), strings -> {
                    ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                    tieBrgy.setAdapter(loAdapter);
                });
            });
        });

        tieBrgy.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getBarangayInfoList().observe(getViewLifecycleOwner(), eBarangayInfos -> {
            for(int x = 0; x < eBarangayInfos.size(); x++){
                if(tieBrgy.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())){
                    mViewModel.setBrgyID(eBarangayInfos.get(x).getBrgyIDxx());

                    infoModel.setLuBrgy(tieBrgy.getText().toString());
                    break;
                }
            }
        }));


        tieBPlace.setOnItemClickListener((adapterView, view, i, l) -> {
            String lsTown = tieTown.getText().toString();
            String[] town = lsTown.split(", ");
            mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
                for(int x = 0; x < townProvinceInfos.size(); x++){
                    if(town[0].equalsIgnoreCase(townProvinceInfos.get(x).sTownName)){
                        mViewModel.setLsBPlaceID(townProvinceInfos.get(x).sTownIDxx);
                        infoModel.setLuBPlace(tieBPlace.getText().toString());
                        break;
                    }
                }

                mViewModel.getBarangayNameList().observe(getViewLifecycleOwner(), strings -> {
                    ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                    tieBrgy.setAdapter(loAdapter);
                });
            });
        });

        // TODO: Use the ViewModel

        spnCivilStats.setOnItemClickListener(new Fragment_LoanUnit.OnItemClickListener(spnCivilStats));

        tieBDate.addTextChangedListener(new OnBirthSetListener(tieBDate));

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == R.id.rb_male){
                genderPosition = "0";
                mViewModel.setGender("0");
            }
            if(i == R.id.rb_female){
                genderPosition = "1";
                mViewModel.setGender("1");
            }
            if(i == R.id.rb_lgbt){
                genderPosition = "2";
                mViewModel.setGender("2");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("REQUEST CODE", String.valueOf(requestCode));
        Log.e("RESULT CODE", String.valueOf(resultCode));
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK) {
                try {
                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(infoModel.getLuImgPath()));
                    mViewModel.saveLUnImageInfo(poImageInfo);
                    submitLUn(Remarksx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                infoModel.setLuImgPath("");
            }
        }
    }


    @Override
    public void OnStartSaving() {

    }

    @Override
    public void OnSuccessResult(String[] args) {
        poMessage.initDialog();
        poMessage.setTitle("Transaction Success");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            getActivity().finish();
        });
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        if (message.trim().equalsIgnoreCase("empty"))
        {
            showDialogImg();
        }else {
            onFailedDialog(message);
        }
    }
    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;
        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnCivilStats.equals(poView)) {
                spnCivilStatsPosition = String.valueOf(i);
                mViewModel.setSpnCivilStats(String.valueOf(i));
            }
        }
    }

    private void submitLUn(String remarksx)throws Exception{
        infoModel.setLuRemarks(remarksx);
        infoModel.setLuLastName(tieLName.getText().toString());
        infoModel.setLuFirstName(tieFName.getText().toString());
        infoModel.setLuMiddleName(tieMName.getText().toString());
        infoModel.setLuSuffix(tieSuffix.getText().toString());
        infoModel.setLuHouseNo(tieHouseNo.getText().toString());
        infoModel.setLuCivilStats(spnCivilStatsPosition);
        infoModel.setLuStreet(tieStreet.getText().toString());
        infoModel.setLuGender(genderPosition);
        infoModel.setLuBDate(tieBDate.getText().toString());
        infoModel.setLuPhone(tiePhone.getText().toString());
        infoModel.setLuMobile(tieMobileNo.getText().toString());
        infoModel.setLuEmail(tieEmailAdd.getText().toString());
        mViewModel.saveLUnInfo(infoModel, Fragment_LoanUnit.this);

    }
    public void showDialogImg(){
        poMessage.initDialog();
        poMessage.setTitle(Remarksx);
        poMessage.setMessage("Please take a selfie in customer's place in order to confirm transaction. \n" +
                "\n" +
                "NOTE: Take a selfie on your current place if customer is not visited");
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            poFilexx.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                infoModel.setLuImgPath(photPath);
                poImageInfo = new EImageInfo();
                poImageInfo.setDtlSrcNo(AccntNox);
                poImageInfo.setSourceNo(TransNox);
                poImageInfo.setSourceCD("DCPa");
                poImageInfo.setImageNme(FileName);
                poImageInfo.setFileLoct(photPath);
                poImageInfo.setFileCode("DCP");
                poImageInfo.setLatitude(String.valueOf(latitude));
                poImageInfo.setLongitud(String.valueOf(longitude));
                startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
            });
        });
        poMessage.setNegativeButton("Cancel", (view, dialog) -> {
            dialog.dismiss();
        });
        poMessage.show();
    }
    public void onFailedDialog(String messages){
        poMessage.initDialog();
        poMessage.setTitle("Transaction Failed");
        poMessage.setMessage(messages);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }

}