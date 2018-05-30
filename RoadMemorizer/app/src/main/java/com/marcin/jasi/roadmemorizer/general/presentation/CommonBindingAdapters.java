package com.marcin.jasi.roadmemorizer.general.presentation;

import android.databinding.BindingAdapter;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.marcin.jasi.roadmemorizer.R;

import java.io.File;

public class CommonBindingAdapters {

    @BindingAdapter("image_path")
    public static void setImage(ImageView view, String path) {
        Glide.with(view)
                .load(new File(path))
                .apply(new RequestOptions()
                        .placeholder(new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.colorPrimary)))
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(view);
    }

}
