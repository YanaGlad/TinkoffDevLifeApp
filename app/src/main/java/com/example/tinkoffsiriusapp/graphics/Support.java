package com.example.tinkoffsiriusapp.graphics;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
