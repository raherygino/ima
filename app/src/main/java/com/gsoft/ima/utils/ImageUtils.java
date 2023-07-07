package com.gsoft.ima.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static boolean saveBitmapToExternalStorage(Bitmap bitmap, String filename) {
        // Get the directory for the user's public pictures directory.
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Create a new file with the specified filename.
        File file = new File(directory, filename);

        FileOutputStream outputStream = null;
        try {
            // Create the FileOutputStream.
            outputStream = new FileOutputStream(file);

            // Compress the bitmap to PNG format (you can choose JPEG or other formats)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            // Flush and close the output stream.
            outputStream.flush();
            outputStream.close();

            return true; // File saved successfully
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Error occurred while saving file
    }
}
