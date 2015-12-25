package com.shareghadi.util;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by BVN on 12/25/2015.
 */
public class DLManager {
    @SuppressLint("NewApi")
    public static void useDownloadManager(String url, String name, Context c) {
        DownloadManager dm = (DownloadManager) c .getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request downloadManageRequest = new DownloadManager.Request(Uri.parse(url));
        downloadManageRequest
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(name)
                .setDescription("Download in progress")
                .setDestinationInExternalFilesDir(c,null,name + ".jpg")
                .allowScanningByMediaScanner();

        dm.enqueue(downloadManageRequest);

    }
}
