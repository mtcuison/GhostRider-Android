package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.imgcapture.ImageFileCreator;
import com.example.imgcapture.database.DatabaseHelper;

import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.MainActivity;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.SplashScreenActivity;
import org.rmj.guanzongroup.ghostrider.epacss.Dialog.DialogUserProfile;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ui.HomeContainer.Fragment_MainContainer;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_NotificationList;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class FragmentDashboard extends Fragment {

    private VMDashboard mViewModel;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final String CAMERA_USAGE = "Login";

    private MessageBox loMessage;
    private GeoLocator poLocator;
    private ImageFileCreator poFilexx;
    private DatabaseHelper imgDBHelper;

    private ImageView imgProfile;
    private String lblUserNm, lblEmailx, lblPstion, lblMobile, lblAddrss;

    private String photoPath;
    private double latitude, longitude;
    private CardView cvProfile, cvMessages, cvNotif, cvSettings, cvLogout;
    public static FragmentDashboard newInstance() {
        return new FragmentDashboard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        poLocator = new GeoLocator(getActivity(), getActivity());
        poFilexx = new ImageFileCreator(getActivity(), CAMERA_USAGE);
        loMessage = new MessageBox(getActivity());
        imgDBHelper = new DatabaseHelper(getActivity());
        cvProfile = view.findViewById(R.id.cvProfile);
        cvMessages = view.findViewById(R.id.cvMessages);
        cvNotif = view.findViewById(R.id.cvNotif);
        cvLogout = view.findViewById(R.id.cvUserLogout);
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
                lblEmailx =eEmployeeInfo.getEmailAdd();
                lblUserNm =eEmployeeInfo.getUserName();
                lblPstion =DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx());
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
        cvLogout.setOnClickListener(v ->{
            showDialog();
        });
        cvProfile.setOnClickListener(v ->{
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
            String dateToStr = format.format(today);
            DialogUserProfile loDialog = new DialogUserProfile(getActivity());
            loDialog.setAddress(poLocator.getAddress());
            loDialog.setEmpContctNo(lblMobile);
            loDialog.setEmpEmail(lblEmailx);
            loDialog.setEmpName(lblUserNm);
            loDialog.setEmpPosition(lblPstion);
            loDialog.setLblDate(dateToStr);
            //loDialog.setAddress(poLocator.getAddress() + "\n Longitude" + poLocator.getLongitude() + "\n Lattitude" +poLocator.getLattitude());
            loDialog.show();
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Save to mysqlLite DB
            imgDBHelper.insertPhoto(CAMERA_USAGE, photoPath, latitude, longitude);
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

        loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        loMessage.setPositiveButton("Yes", (view, dialog) -> {
            dialog.dismiss();
            getActivity().finish();
            new REmployee(getActivity().getApplication()).LogoutUserSession();
            startActivity(new Intent(getActivity(), SplashScreenActivity.class));
        });
        loMessage.setTitle("GhostRider Session");
        loMessage.setMessage("Are you sure you want to end session/logout?");
        loMessage.show();
    }

}