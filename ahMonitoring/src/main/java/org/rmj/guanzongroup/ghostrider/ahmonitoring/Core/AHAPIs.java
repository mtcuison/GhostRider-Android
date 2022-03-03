package org.rmj.guanzongroup.ghostrider.ahmonitoring.Core;

public class AHAPIs {

    private boolean isUnitTest = false;

    private static final String LIVE = "https://restgk.guanzongroup.com.ph/";
    private static final String LOCAL = "http://192.168.10.141/";

    private String URL_POST_SELFIELOG = "integsys/hcm/selfie_log.php";
    private String URL_REQUEST_RANDOM_STOCK_INVENTORY = "integsys/bullseye/random_stock_inventory_request.php";

    public AHAPIs(boolean isUnitTest) {
        this.isUnitTest = isUnitTest;
    }

    public String getUrlPostSelfieLog() {
        if(isUnitTest){
            return LOCAL + URL_POST_SELFIELOG;
        }
        return LIVE + URL_POST_SELFIELOG;
    }

    public String getUrlDownloadInventory(){
        if(isUnitTest){
            return LOCAL + URL_REQUEST_RANDOM_STOCK_INVENTORY;
        }
        return LIVE + URL_REQUEST_RANDOM_STOCK_INVENTORY;
    }
}
