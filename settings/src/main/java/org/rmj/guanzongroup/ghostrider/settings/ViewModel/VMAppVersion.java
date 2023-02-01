package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogChildModel;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogHeader;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogChild;

import android.util.Log;

import org.rmj.guanzongroup.ghostrider.settings.Model.AppVersion_Model;


public class VMAppVersion {
    public int imgicon;
    public Boolean isGroup, hasChild;
    public void setUpdatedLogData(String listLogs, String headerIndex, String dataPosition){
        AppVersion_Model updatedLogModel = new AppVersion_Model(listLogs, imgicon, isGroup, hasChild);

        if (dataPosition == "asHead"){
            listUpdatedLogHeader.add(updatedLogModel);
            //Log.d("String Index", String.valueOf(listUpdatedLogHeader));
        } else if (dataPosition == "asChild") {
            listUpdatedLogChildModel.add(updatedLogModel);

            if(hasChild == true){
                int index = 0;//listUpdatedLogHeader.indexOf("Employee Loan");
                //Log.d("String Index", String.valueOf(listUpdatedLogHeader.indexOf("Employee Loan")));

                if(index >= 0) {
                    Log.d("Index", String.valueOf(listUpdatedLogHeader.get(index)));
                    listUpdatedLogChild.put(listUpdatedLogHeader.get(index), listUpdatedLogChildModel);
                }
            }
        }
    }
}
