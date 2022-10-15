package org.rmj.g3appdriver.lib.ApprovalCode;

public interface iAppCode {
    String GenerateCode(Object foArgs);
    boolean Upload(String args);
    CreditApp LoadApplication(String args, String args1, String args2);
}
