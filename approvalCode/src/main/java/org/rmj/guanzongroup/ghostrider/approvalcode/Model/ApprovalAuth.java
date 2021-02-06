package org.rmj.guanzongroup.ghostrider.approvalcode.Model;

class ApprovalAuth {

    private final String sCACodexx;
    private final String sCATitlex;
    private final String sCATypexx;

    public ApprovalAuth(String Code, String Title, String Type){
        this.sCACodexx = Code;
        this.sCATitlex = Title;
        this.sCATypexx = Type;
    }

    public String getsCACodexx() {
        return sCACodexx;
    }

    public String getsCATitlex() {
        return sCATitlex;
    }

    public String getsCATypexx() {
        return sCATypexx;
    }
}
