package org.rmj.g3appdriver.lib.SelfieLog;

public interface AHM {
    int Validate();
    String Save(Object args);
    boolean Upload(String args);
    int ValidateUser();
    boolean Import();
    boolean UploadRecords();
}
