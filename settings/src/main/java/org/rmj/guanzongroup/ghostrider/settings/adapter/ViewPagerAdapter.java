/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 7/8/21 2:08 PM
 * project file last modified : 7/8/21 2:08 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.rmj.guanzongroup.ghostrider.settings.R;

import java.util.Objects;

public class ViewPagerAdapter  extends PagerAdapter {

    //Context object
    Context context;

    //Array of images
    private int[] images;

    //Layout Inflater
    private LayoutInflater mLayoutInflater;

    private ImageView imageView;

    //Viewpager Constructor
    public ViewPagerAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        //return the number of images
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        //inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.help_dcp_item, container, false);

        //referencing the image view from the item.xml file
        imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);

        //setting the image in the imageView
        imageView.setImageResource(images[position]);

        //Adding the View
        Objects.requireNonNull(container).addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}