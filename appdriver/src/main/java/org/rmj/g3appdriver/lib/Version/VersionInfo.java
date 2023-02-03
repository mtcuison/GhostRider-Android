package org.rmj.g3appdriver.lib.Version;

public class VersionInfo {

    private String sVrsionCd = "";
    private String sVrsionNm = "";
    private String sTargetNm = "";
    private String sVrsnNote = "";

    public VersionInfo() {
    }

    public String getsVrsionCd() {
        return sVrsionCd;
    }

    public void setsVrsionCd(String sVrsionCd) {
        this.sVrsionCd = sVrsionCd;
    }

    public String getsVrsionNm() {
        return sVrsionNm;
    }

    public void setsVrsionNm(String sVrsionNm) {
        this.sVrsionNm = sVrsionNm;
    }

    public String getsTargetNm() {
        return sTargetNm;
    }

    public void setsTargetNm(String sTargetNm) {
        this.sTargetNm = sTargetNm;
    }

    public String getsVrsnNote() {
        return sVrsnNote;
    }

    public void setsVrsnNote(String sVrsnNote) {
        this.sVrsnNote = sVrsnNote;
    }
}
