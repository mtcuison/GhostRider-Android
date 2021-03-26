package org.rmj.g3appdriver.utils;

import android.os.Environment;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;

import java.io.File;

public class UpdateFileNameAndFolder {
    public UpdateFileNameAndFolder() {
    }
    public String UpdateFileNameAndFolder(String newTransNox, String oldTransNox, int entryNox, String fileCode, String subFolder, String oldImgName){
        String root = Environment.getExternalStorageDirectory().toString();
        File dir = new File(root + "/"+
                AppConstants.APP_PUBLIC_FOLDER + "/" +
                subFolder + "/" +
                oldTransNox + "/");
        File newDir = new File(root + "/" +
                AppConstants.APP_PUBLIC_FOLDER + "/" +
                subFolder + "/" +
                newTransNox + "/");
        File newPhotoPath = null;
        File oldImgFile = null;
        String newImgName = "";
        boolean isSuccess = false;
        if(dir.exists()){
            oldImgFile = new File(dir,oldImgName);
            newImgName = newTransNox + "_" + entryNox + "_" +fileCode + ".png";
            newPhotoPath = new File(dir,newImgName);
            if(oldImgFile.exists())
                isSuccess = oldImgFile.renameTo(newPhotoPath);

        }
        if (isSuccess) {
            dir.renameTo(newDir);
            newPhotoPath = new File(newDir,newImgName);
            return (newPhotoPath.getAbsolutePath() + ","+ newImgName);
        }
        else{
            return (oldImgFile.getAbsolutePath() + ","+ oldImgName);
        }

    }
}
