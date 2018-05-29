package com.marcin.jasi.roadmemorizer.general.helpers;

import android.app.DownloadManager;
import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapSaveHelper {

    public static final int BITMAP_QUALITY = 100;

    public void trySaveBitmap(Bitmap bitmap, String filename) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, BITMAP_QUALITY, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
