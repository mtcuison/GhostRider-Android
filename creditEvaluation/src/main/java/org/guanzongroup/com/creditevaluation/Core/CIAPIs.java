package org.guanzongroup.com.creditevaluation.Core;

public class CIAPIs {

    private boolean isUnitTest = false;

    private static final String LOCAL = "http://192.168.10.141/";
    private static final String LIVE = "https://restgk.guanzongroup.com.ph/";

    private static final String URL_DOWNLOAD_APPLICATIONS = "integsys/gocas/ci_request_for_evaluations.php";
    private static final String URL_SUBMIT_RESULT = "integsys/gocas/ci_submit_result.php";
    private static final String URL_POST_CI_APPROVAL = "integsys/gocas/ci_submit_approval.php";
    private static final String URL_DOWNLOAD_BH_PREVIEW = "integsys/gocas/bh_request_evaluation_preview.php";
    private static final String URL_POST_BH_APPROVAL = "integsys/gocas/bh_submit_approval.php";

    public CIAPIs(boolean isUnitTest) {
        this.isUnitTest = isUnitTest;
    }

    public String getUrlDownloadCIApplications() {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_APPLICATIONS;
        }
        return LOCAL + URL_DOWNLOAD_APPLICATIONS;
    }

    public String getUrlSubmitCIResult() {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_RESULT;
        }
        return LIVE + URL_SUBMIT_RESULT;
    }

    public String getUrlPostCiApproval(){
        if(isUnitTest) {
            return LOCAL + URL_POST_CI_APPROVAL;
        }
        return LIVE + URL_POST_CI_APPROVAL;
    }

    public String getUrlPostBhApproval(){
        if(isUnitTest) {
            return LOCAL + URL_POST_BH_APPROVAL;
        }
        return LIVE + URL_POST_BH_APPROVAL;
    }

    public String getUrlDownloadBhPreview(){
        if(isUnitTest) {
            return LOCAL + URL_DOWNLOAD_BH_PREVIEW;
        }
        return LIVE + URL_DOWNLOAD_BH_PREVIEW;
    }
}
