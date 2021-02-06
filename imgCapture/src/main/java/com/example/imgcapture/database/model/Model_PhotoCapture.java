package com.example.imgcapture.database.model;

public class Model_PhotoCapture {
    public static final String TABLE_NAME = "tbl_photoCapture";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PHOTO_USAGE = "photoUsage";
    public static final String COLUMN_DIRECTORY = "photoDirectory";
    public static final String COLUMN_LATITUDE = "photoTakeLatitude";
    public static final String COLUMN_LONGITUDE = "photoTakeLongitude";
    public static final String COLUMN_TIMESTAMP = "photoTimestamp";

    private int id;
    private String usage;
    private String directory;
    private double latitude;
    private double longitude;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PHOTO_USAGE + " TEXT,"
                    + COLUMN_DIRECTORY + " TEXT,"
                    + COLUMN_LATITUDE + " DOUBLE,"
                    + COLUMN_LONGITUDE + " DOUBLE,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Model_PhotoCapture() {
    }

    public Model_PhotoCapture(int id, String usage, String directory, double latitude, double longitude, String timestamp) {
        this.id = id;
        this.usage = usage;
        this.directory = directory;
        this.latitude= latitude;
        this.longitude= longitude;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage= usage;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory= directory;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude= latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude= longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
