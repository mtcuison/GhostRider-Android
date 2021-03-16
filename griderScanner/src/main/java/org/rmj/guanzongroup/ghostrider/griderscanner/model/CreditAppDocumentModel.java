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
