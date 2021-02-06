package org.rmj.guanzongroup.promotions.Etc;

public interface RaffleEntryCallback {
    void OnSendingEntry(String Title, String Message);
    void OnSuccessEntry();
    void OnFailedEntry(String message);
}
