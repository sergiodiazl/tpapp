package com.example.sergio_pieza.aplicaciontp.helper;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.android.volley.Request;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by sergio-pieza on 22/11/2017.
 */

public class DownloadHelper {
    public static void descargarImagen(String url,Context context,String nombre){
        Uri imagenUri=Uri.parse(url);

        String filename = nombre+".jpg";
        File direct =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/imagenesTP/");


        if (!direct.exists()) {
            direct.mkdir();

        }

        DownloadManager dm = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(imagenUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        File.separator +"imagenesTP" + File.separator + filename);

        dm.enqueue(request);
    }
}
