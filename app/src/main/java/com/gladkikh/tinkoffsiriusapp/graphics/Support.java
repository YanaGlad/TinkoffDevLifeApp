package com.gladkikh.tinkoffsiriusapp.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gladkikh.tinkoffsiriusapp.R;
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;

import java.io.IOException;
import java.io.InputStream;

public class Support {

    public static Bitmap loadBitmapImage(AssetManager assetManager, String name) { //Загрузка изображения из assets
        InputStream inputStream;
        Bitmap bitmap;
        try {
            inputStream = assetManager.open(name);
            bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap == null) {
                throw new RuntimeException("Unable to load file " + name);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load file " + name);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
