package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogChildModel;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogHeader;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_AppVersion.listUpdatedLogChild;
import org.rmj.guanzongroup.ghostrider.settings.Model.AppVersion_Model;


public class VMAppVersion {
    public void setUpdatedLogDataHeader(List<String> listLogs, int imgicon, Boolean isGroup, Boolean hasChild){
        if(listLogs.size() > 0) {
            for (int i = 0; i < listLogs.size(); i++) {
                AppVersion_Model updatedLogModel = new AppVersion_Model(listLogs.get(i), imgicon, isGroup, hasChild);
                listUpdatedLogHeader.add(updatedLogModel);
            }
        }
    }
    public void setUpdatedDataDetails(String listLogs, int imgicon, Boolean isGroup, Boolean hasChild, int headerIndex){
        AppVersion_Model updatedLogModel = new AppVersion_Model(listLogs, imgicon, isGroup, hasChild);
        listUpdatedLogChildModel.add(updatedLogModel);
        listUpdatedLogChild.put(listUpdatedLogHeader.get(headerIndex), listUpdatedLogChildModel);
    }
}
