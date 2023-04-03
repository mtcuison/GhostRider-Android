package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;

import java.util.List;

@Dao
public interface DPacita {

    @Query("SELECT * FROM Branch_Info")
    LiveData<List<EBranchInfo>> GetBranchList();
}
