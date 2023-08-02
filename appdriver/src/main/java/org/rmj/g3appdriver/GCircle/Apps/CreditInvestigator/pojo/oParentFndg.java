package org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo;

public class oParentFndg {

    private final String sFieldx;
    private final String sParent;

    public oParentFndg(String Field, String sParent) {
        this.sFieldx = Field;
        this.sParent = sParent;
    }

    public String getField() {
        return sFieldx;
    }

    public String getParent() {
        return sParent;
    }

    public String getTitle(){
        switch (sFieldx){
            case "sAddrFndg":
                return "Address Info Findings";

            case "sAsstFndg":
                return "Assets Info Findings";

            default:
                return "Income Info Findings";
        }
    }

    public String getParentDescript(){
        if(sParent == null){
            return "";
        } else {
            switch (sParent) {
                case "present_address":
                    return "Present Address";

                case "primary_address":
                    return "Primary Address";

                case "employed":
                    return "Employment";

                case "self_employed":
                    return "Business";

                case "financed":
                    return "Financier";

                case "pensioner":
                    return "Pension";

                default:
                    return "";
            }
        }
    }
}
