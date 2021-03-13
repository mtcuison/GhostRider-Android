package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.epacss.BuildConfig;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.SplashScreenActivity;
import org.rmj.guanzongroup.ghostrider.epacss.Dialog.DialogUserProfile;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_NotificationList;
import org.rmj.guanzongroup.ghostrider.settings.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Fragment_Dashboard extends Fragment {

    private VMDashboard mViewModel;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final String CAMERA_USAGE = "Login";

    private MessageBox loMessage;
    private GeoLocator poLocator;
    private ImageFileCreator poFilexx;

    private ImageView imgProfile;
    private TextView lblVrsion;
    private String lblUserNm, lblEmailx, lblPstion, lblMobile, lblAddrss;

    private String photoPath;
    private double latitude, longitude;
    private CardView cvProfile, cvMessages, cvNotif, cvSettings, cvLogout, cvSelfie;
    public static Fragment_Dashboard newInstance() {
        return new Fragment_Dashboard();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        poLocator = new GeoLocator(getActivity(), getActivity());
        poFilexx = new ImageFileCreator(getActivity(), CAMERA_USAGE);
        loMessage = new MessageBox(getActivity());
        cvProfile = view.findViewById(R.id.cvProfile);
        cvMessages = view.findViewById(R.id.cvMessages);
        cvNotif = view.findViewById(R.id.cvNotif);
        cvSettings = view.findViewById(R.id.cvAppSettings);
        cvLogout = view.findViewById(R.id.cvUserLogout);
        cvSelfie = view.findViewById(R.id.cvSelfieLogin);

        lblVrsion = view.findViewById(R.id.lbl_versionInfo);
        lblVrsion.setText(BuildConfig.VERSION_NAME + "_" + BuildConfig.BUILD_TYPE.toUpperCase());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMDashboard.class);
        // TODO: Use the ViewModel
        mViewModel.getMobileNo().observe(getViewLifecycleOwner(), s -> lblMobile = s);
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblEmailx = eEmployeeInfo.getEmailAdd();
                lblUserNm = eEmployeeInfo.getUserName();
                lblPstion = DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        cvMessages.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(),Activity_NotificationList.class);
            intent.putExtra("type", "Messages");
            startActivity(intent);
        });
        cvNotif.setOnClickListener(v ->{  Intent intent = new Intent(getActivity(),Activity_NotificationList.class);
            intent.putExtra("type", "Notifications");
            startActivity(intent);
        });
        cvSettings.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), Settings.class);
            startActivity(intent);
        });
        cvLogout.setOnClickListener(v ->{
            showDialog();
        });
        cvProfile.setOnClickListener(v ->{
            Date today = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
            String dateToStr = format.format(today);
            DialogUserProfile loDialog = new DialogUserProfile(getActivity());
            loDialog.setAddress(poLocator.getAddress());
            loDialog.setEmpContctNo(lblMobile);
            loDialog.setEmpEmail(lblEmailx);
            loDialog.setEmpName(lblUserNm);
            loDialog.setEmpPosition(lblPstion);
            //loDialog.setLblDate(dateToStr);
            //loDialog.setAddress(poLocator.getAddress() + "\n Longitude" + poLocator.getLongitude() + "\n Lattitude" +poLocator.getLattitude());
            loDialog.show();
        });
        cvSelfie.setOnClickListener(view -> {
            Intent loIntent = new Intent(getActivity(), Activity_Application.class);
            loIntent.putExtra("app", AppConstants.INTENT_SELFIE_LOGIN);
            startActivity(loIntent);
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Save to mysqlLite DB
            poFilexx.galleryAddPic(photoPath);
            setPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgProfile.getWidth();
        int targetH = imgProfile.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        imgProfile.setImageBitmap(bitmap);
    }
    public void showDialog(){
        loMessage.initDialog();
        loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        loMessage.setPositiveButton("Yes", (view, dialog) -> {
            dialog.dismiss();
            requireActivity().finish();
            new REmployee(requireActivity().getApplication()).LogoutUserSession();
            startActivity(new Intent(getActivity(), SplashScreenActivity.class));
        });
        loMessage.setTitle("GhostRider Session");
        loMessage.setMessage("Are you sure you want to end session/logout?");
        loMessage.show();
    }

}