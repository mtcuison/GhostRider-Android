/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 5/5/21 1:32 PM
 * project file last modified : 5/5/21 1:32 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.adapter;

public class NewsEventsModel {
    private String image;
    private String title;
    private String newsPostedBy, newsDay, newsMonth;

    public NewsEventsModel(String image, String title,String newsDay, String newsMonth, String newsPostedBy) {
        this.image = image;
        this.title = title;
        this.newsPostedBy = newsPostedBy;
        this.newsDay = newsDay;
        this.newsMonth = newsMonth;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
    public String getNewsPostedBy() {
        return newsPostedBy;
    }

    public String getNewsDay() {
        return newsDay;
    }
    public String getNewsMonth() {
        return newsMonth;
    }

}
