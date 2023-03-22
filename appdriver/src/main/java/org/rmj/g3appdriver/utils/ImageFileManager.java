package org.rmj.g3appdriver.utils;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.rmj.g3appdriver.R;

public class ImageFileManager {
    private static final String TAG = ImageFileManager.class.getSimpleName();

    public static void LoadImageToView(String link, ShapeableImageView view){
        Picasso.get().load(link).placeholder(R.drawable.ic_image_place_holder)
                .error(R.drawable.ic_broken_image_24).into(view);
    }
}
