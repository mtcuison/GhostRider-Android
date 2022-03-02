package org.guanzongroup.com.creditevaluation.Core;

public class CIAPIs {

    private boolean isUnitTest = false;

    private static final String LOCAL = "";
    private static final String LIVE = "";

    private static final String URL_DOWNLOAD_APPLICATIONS = "";
    private static final String URL_SUBMIT_RESULT = "";

    public CIAPIs(boolean isUnitTest) {
        this.isUnitTest = isUnitTest;
    }

    public String getUrlDownloadApplications() {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_APPLICATIONS;
        }
        return LOCAL + URL_DOWNLOAD_APPLICATIONS;
    }

    public String getUrlSubmitResult() {
        if(isUnitTest){
            return LIVE + URL_SUBMIT_RESULT;
        }
        return LOCAL + URL_SUBMIT_RESULT;
    }
}
