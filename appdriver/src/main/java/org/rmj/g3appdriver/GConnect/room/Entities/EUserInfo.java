package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_user")
public class EUserInfo {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "gcard_number")
    private String gcard_number;

    @ColumnInfo(name = "avl_points")
    private double avl_points;

    @ColumnInfo(name = "d_modified")
    private String d_modified;

    public EUserInfo(){

    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getGcard_number() {
        return gcard_number;
    }

    public void setGcard_number(String gcard_number) {
        this.gcard_number = gcard_number;
    }

    public double getAvl_points() {
        return avl_points;
    }

    public void setAvl_points(double avl_points) {
        this.avl_points = avl_points;
    }

    public String getD_modified() {
        return d_modified;
    }

    public void setD_modified(String d_modified) {
        this.d_modified = d_modified;
    }
}
