package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogChildModel;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogHeader;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogChild;

import android.util.Log;

import org.rmj.guanzongroup.ghostrider.settings.Model.AppVersion_Model;


public class VMAppVersion {
    public int imgicon;
    public Boolean isGroup, hasChild;
    /*
    public void setUpdatedLogData(String listLogs, String headerIndex, String dataPosition, int index){
        if (dataPosition == "asHead"){
            AppVersion_Model updatedLogModel = new AppVersion_Model(listLogs, index, imgicon, isGroup, hasChild);
            listUpdatedLogHeader.add(updatedLogModel);
        } else if (dataPosition == "asChild") {
            if(hasChild == true){
                index = getKeyHeader(listUpdatedLogHeader, headerIndex);
                AppVersion_Model updatedLogModel = new AppVersion_Model(listLogs, index, imgicon, isGroup, hasChild);
                listUpdatedLogChildModel.add(updatedLogModel);
                if(index >= 0) {
                    listUpdatedLogChild.put(listUpdatedLogHeader.get(index), listUpdatedLogChildModel);
                }
            }
        }
    }
    public int getKeyHeader(List<AppVersion_Model> listHeader, String listHeaderID){
        int headerIndex = -1;
        if(listHeader.size() > 0 && listHeaderID.trim().isEmpty() == false){
            for (int i = 0; i < listHeader.size(); i++){
                String headerName = listHeader.get(i).moduleName;
                if (headerName == listHeaderID){
                    headerIndex =  listHeader.get(i).index;
                    break;
                }
            }
        }
        return headerIndex;
    }*/
}
