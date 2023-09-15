package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GConnect.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ETownInfo;

import java.util.List;

@Dao
public interface DBranchInfo {

    @Insert
    void insert(EBranchInfo branchInfo);

    @Query("SELECT * FROM BranchInfo")
    LiveData<List<EBranchInfo>> getAllBranchs();

    @Query("SELECT * FROM BranchInfo " +
            "WHERE sBranchCd LIKE 'M%' " +
            "ORDER BY sTownIDxx = (SELECT sTownIDx1 FROM Client_Profile_Info) DESC, sBranchCd ASC")
    LiveData<List<EBranchInfo>> getMotorBranches();

    @Query("SELECT * FROM BranchInfo " +
            "WHERE sBranchCd LIKE 'C%' " +
            "ORDER BY sTownIDxx = (SELECT sTownIDx1 FROM Client_Profile_Info) DESC, sBranchCd ASC")
    LiveData<List<EBranchInfo>> getMobileBranches();

    @Query("SELECT * FROM BranchInfo WHERE sBranchCd=:BranchCde")
    EBranchInfo getBranchIfExist(String BranchCde);

    @Query("SELECT * FROM Town_Info WHERE sTownIDxx =:args")
    LiveData<List<ETownInfo>> GetTownList(String args);

    @Query("SELECT * FROM Province_Info")
    LiveData<List<EProvinceInfo>> GetProvinceList();

    @Query("SELECT * FROM BranchInfo WHERE sBranchCd LIKE 'M%' AND sTownIDxx =:Town")
    LiveData<List<EBranchInfo>> GetMCBranches(String Town);

    @Query("SELECT * FROM BranchInfo WHERE sBranchCd LIKE 'C%' AND sTownIDxx =:Town")
    LiveData<List<EBranchInfo>> GetMPBranches(String Town);

    @Query("UPDATE BranchInfo SET " +
            "sBranchNm =:BranchNm, " +
            "sDescript =:Descript, " +
            "sAddressx =:Addressx, " +
            "sContactx =:Contactx, " +
            "sTelNumbr =:TelNumbr, " +
            "sEmailAdd =:EmailAdd " +
            "WHERE sBranchCd =:BranchCd")

    void UpdateBranchInfo(String BranchCd,
                            String BranchNm,
                            String Descript,
                            String Addressx,
                            String Contactx,
                            String TelNumbr,
                            String EmailAdd);
}
