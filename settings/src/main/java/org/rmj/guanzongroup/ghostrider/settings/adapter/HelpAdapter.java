/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 7/8/21 10:16 AM
 * project file last modified : 7/8/21 10:16 AM
 */

package org.rmj.guanzongroup.ghostrider.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import org.rmj.guanzongroup.ghostrider.settings.R;

import java.util.Objects;


public class HelpAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        private int[] layouts;
        public HelpAdapter(int[] layouts) {
            this.layouts = layouts;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageViewMain);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

}

