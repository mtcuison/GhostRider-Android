package org.rmj.guanzongroup.ghostrider.settings.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.adapter.RecyclerViewAppVersionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogUpdateNotice {
    private  AlertDialog poDialogx;
    Context context;
    List<VersionInfo> infoList;
    private RecyclerView rec_updatedNewFeatures;
    private RecyclerView rec_updatedFixedConcerns;
    private RecyclerViewAppVersionAdapter versionAdapter;
    private MaterialTextView lbl_updatedLog;
    private MaterialTextView lbl_updatedFixedConcerns;

    public DialogUpdateNotice(Context context, List<VersionInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }
    public void initDialog(){
        //instantiate alert dialog builder
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        //create view for alert dialog builder
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_appversion_download_update, null, false);

        poDialogx = loBuilder.create();
        //set alert dialog unclosable using builder
        poDialogx.setCancelable(false);
        //set view for alert dialog builder
        poDialogx.setView(view);

        lbl_updatedLog = view.findViewById(R.id.lbl_updatedLog);
        lbl_updatedFixedConcerns = view.findViewById(R.id.lbl_updatedFixedConcerns);

        //get current view layout ids for recycler view
        rec_updatedNewFeatures = view.findViewById(R.id.rec_updatelogs);
        rec_updatedFixedConcerns = view.findViewById(R.id.rec_updatedFixedConcerns);

        //attach list of version updates to the Adapter and ListView Object
        setListToAdapter(context, R.layout.update_version_logs, rec_updatedNewFeatures, getListFromVersionInfo("New Features"));
        setListToAdapter(context, R.layout.update_version_logs_fixed_concerns, rec_updatedFixedConcerns, getListFromVersionInfo("Fixed Concerns"));
    }
    public List<HashMap<String, String>> getListFromVersionInfo(String verioninfoCategory){
        HashMap<String, String> hmVersionInfo = new HashMap<>();
        List<HashMap<String, String>> listVersionInfo = new ArrayList<>();

        String feature;
        String descript;
        String fixedconcerns;

        //if return list of version updates are available, continue
        if(infoList.size() > 0){
            //loop through versioninfo list, then loop again on its sublist
            for (int x = 0; x < infoList.size(); x++){
                try {
                    if(verioninfoCategory.equals("New Features")){
                        //set text on recycler view label
                        lbl_updatedLog.setText("New Features");
                        //if passed arg is New Features, get the list size of New Features from VersionInfo
                        for (int y = 0; y < infoList.get(x).getNewFeatures().size(); y++){
                            feature = infoList.get(x).getNewFeatures().get(y).getsFeaturex();
                            descript = infoList.get(x).getNewFeatures().get(y).getsDescript();
                            //map feature as KEY, descript as VALUE
                            hmVersionInfo.put(feature, descript);
                            //add to list
                            listVersionInfo.add(hmVersionInfo);
                        }
                    }
                    //if passed arg is New Features, get the list size of Fixed Concerns from VersionInfo
                    if(verioninfoCategory.equals("Fixed Concerns")){
                        //set text on recycler view label
                        lbl_updatedFixedConcerns.setText("Fixed Concerns");
                        for (int y = 0; y < infoList.get(x).getOthers().size(); y++){
                            fixedconcerns = infoList.get(x).getOthers().get(y);
                            //map fixed concerns as both KEY and VALUE
                            hmVersionInfo.put(fixedconcerns, fixedconcerns);
                            //add to list
                            listVersionInfo.add(hmVersionInfo);
                        }
                    }
                }catch (Exception e){
                    Log.d( this.getClass().getSimpleName(), e.getMessage());
                }
            }
        }
        return listVersionInfo;
    }
    public void setListToAdapter(Context context, @LayoutRes int res, RecyclerView recyclerView, List<HashMap<String, String>> listVersionInfo){
        //attach list of version updates to the Adapter and ListView Object
        versionAdapter = new RecyclerViewAppVersionAdapter(listVersionInfo, context);
        versionAdapter.resource = res;

        //validate recycler view obj if visible before attaching the adapter
        if(recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setAdapter(versionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }
    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }
}
