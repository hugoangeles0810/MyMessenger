package com.hugoangeles.android.mymessenger.lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hugoangeles.android.mymessenger.R;

/**
 * Created by hugo on 22/06/16.
 */
public class GlideImageLoader implements ImageLoader {

    private RequestManager requestManager;

    public GlideImageLoader(Context context) {
        this.requestManager = Glide.with(context);

    }

    @Override
    public void load(ImageView imageView, String s) {
        requestManager.load(s).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
    }
}
