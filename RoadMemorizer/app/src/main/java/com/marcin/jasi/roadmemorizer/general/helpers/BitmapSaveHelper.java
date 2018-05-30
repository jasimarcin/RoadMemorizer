package com.marcin.jasi.roadmemorizer.general.helpers;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapSaveHelper {

    public static final int BITMAP_QUALITY = 100;

    private String filesDir;

    public BitmapSaveHelper(String filesDir) {
        this.filesDir = filesDir;
    }

    public void trySaveBitmap(Bitmap bitmap, String filename) {
        FileOutputStream out = null;
        File file = new File(filesDir, filename);

        try {
            out = new FileOutputStream(file);
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

    public static String generateBitmapFilename(long id) {
        return String.format("road_%s.png", id);
    }

}
