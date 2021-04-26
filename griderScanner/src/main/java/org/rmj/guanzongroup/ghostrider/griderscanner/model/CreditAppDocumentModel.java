/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.griderScanner
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.griderscanner.model;

public class CreditAppDocumentModel {

    private String docTransNox;
    private String docFileCode;
    private int docEntryNox;
    private String docImageName;
    private String docFilePath;

    private String message;
    public CreditAppDocumentModel() {
    }
    public void setDocTransNox(String TransNox){
        docTransNox = TransNox;
    }
    public void setDocFileCode(String FileCode){
        docFileCode = FileCode;
    }
    public void setDocEntryNox(int EntryNox){
        docEntryNox = EntryNox;
    }
    public void setDocImageName(String ImageName){
        docImageName = ImageName;
    }
    public void setDocFilePath(String FilePath){
        docFilePath = FilePath;
    }

    public String getDocTransNox(){
        return docTransNox;
    }
    public String getDocFileCode(){
        return docFileCode;
    }
    public int getDocEntryNox(){
        return docEntryNox;
    }
    public String getDocImageName(){
        return docImageName;
    }
    public String getDocFilePath(){
        return docFilePath;
    }

}
