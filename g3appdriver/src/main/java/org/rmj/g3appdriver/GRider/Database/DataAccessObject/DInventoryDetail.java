package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.room.Insert;

import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;

import java.util.List;

public interface DInventoryDetail {

    @Insert
    void insertInventoryDetail(List<EInventoryDetail> foDetail);
}
