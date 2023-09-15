package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Raffle_Status")
public class ERaffleStatus {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "sStatusID")
    private Integer StatusID;
    private Integer HasRffle = 0; //0 = Not Started, 1 = Starting Notice, 2 = OnGoing, 3 = Ended

    public ERaffleStatus() {
    }

    public Integer getStatusID() {
        return StatusID;
    }

    public void setStatusID(Integer statusID) {
        StatusID = statusID;
    }

    public Integer getHasRffle() {
        return HasRffle;
    }

    public void setHasRffle(Integer hasRffle) {
        HasRffle = hasRffle;
    }
}
