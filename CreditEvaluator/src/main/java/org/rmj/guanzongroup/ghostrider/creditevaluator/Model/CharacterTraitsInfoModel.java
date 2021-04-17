package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

public class CharacterTraitsInfoModel {
    private String cbGambler;
    private String cbWomanizer;
    private String cbHeavyBrrw;
    private String cbQuarrel;
    private String cbRepo;
    private String cbMortage;
    private String cbArrogance;
    private String cbOthers;
    private String cTranstat;
    private String sRemarks;
    private String message;
    public CharacterTraitsInfoModel() {
    }
    public String getcTranstat() {
        return cTranstat;
    }

    public void setcTranstat(String cTranstat) {
        this.cTranstat = cTranstat;
    }

    public boolean isValidCharaterData(){
        return isApproved() && isReason();
    }
    public boolean isReason(){
        if (this.sRemarks == null || this.sRemarks.trim().isEmpty()){
            message = "Please enter reason for approval/disapproval evaluation.";
            return false;
        }
        return true;
    }
    public boolean isApproved(){
        if (this.cTranstat == null || this.cTranstat.equalsIgnoreCase("0")){
            message = "Please select approval/disapproval status.";
            return false;
        }
        return true;
    }
    public String getsRemarks() {
        return sRemarks;
    }

    public void setsRemarks(String sRemarks) {
        this.sRemarks = sRemarks;
    }

    public String getMessage() { return message; }

    public String getCbGambler() {
        return cbGambler;
    }

    public void setCbGambler(String cbGambler) {
        this.cbGambler = cbGambler;
    }

    public String getCbWomanizer() {
        return cbWomanizer;
    }

    public void setCbWomanizer(String cbWomanizer) {
        this.cbWomanizer = cbWomanizer;
    }

    public String getCbHeavyBrrw() {
        return cbHeavyBrrw;
    }

    public void setCbHeavyBrrw(String cbHeavyBrrw) {
        this.cbHeavyBrrw = cbHeavyBrrw;
    }

    public String getCbQuarrel() {
        return cbQuarrel;
    }

    public void setCbQuarrel(String cbQuarrel) {
        this.cbQuarrel = cbQuarrel;
    }

    public String getCbRepo() {
        return cbRepo;
    }

    public void setCbRepo(String cbRepo) {
        this.cbRepo = cbRepo;
    }

    public String getCbMortage() {
        return cbMortage;
    }

    public void setCbMortage(String cbMortage) {
        this.cbMortage = cbMortage;
    }

    public String getCbArrogance() {
        return cbArrogance;
    }

    public void setCbArrogance(String cbArrogance) {
        this.cbArrogance = cbArrogance;
    }

    public String getCbOthers() {
        return cbOthers;
    }

    public void setCbOthers(String cbOthers) {
        this.cbOthers = cbOthers;
    }
}
