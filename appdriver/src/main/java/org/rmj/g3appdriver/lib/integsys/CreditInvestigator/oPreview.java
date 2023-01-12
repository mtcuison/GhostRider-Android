package org.rmj.g3appdriver.lib.integsys.CreditInvestigator;

import android.view.View;

public class oPreview {

    private final String psTitlex;
    private final String psDescpt;
    private final String psLabelx;
    private final String psValuex;

    public oPreview(String fsTitle, String fsDecript, String fsLabel, String fsValue) {
        this.psTitlex = fsTitle;
        this.psDescpt = fsDecript;
        this.psLabelx = fsLabel;
        this.psValuex = fsValue;
    }

    public int isHeader() {
        if(!"".equalsIgnoreCase(psTitlex) && psTitlex != null) {
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public int isContent() {
        if(!"".equalsIgnoreCase(psValuex) && psValuex != null) {
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public String getTitle() {
        return psTitlex;
    }

    public String getDescription() {
        return psDescpt;
    }

    public String getLabel() {
        return psLabelx;
    }

    public String getValue() {
        return psValuex;
    }
}
