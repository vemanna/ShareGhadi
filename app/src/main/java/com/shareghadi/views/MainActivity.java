package com.shareghadi.views;

/**
 * Created by BVN on 12/24/2015.
 */

import android.os.Environment;
import android.os.Bundle;
import com.shareghadi.R;
import com.shareghadi.util.DLManager;
import com.shareghadi.util.LogUtil;

import java.io.File;

public class MainActivity extends BaseActivity {

    private static final String TAG = LogUtil.makeLogTag(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private File getDownloadedFile(String fileName, String downloadingUrl){

       /* File imgFile = new  File("/storage/sdcard0/Android/data/com.shareghadi/files/"+ signup.getFirstName()+".jpg");
        LOGD(TAG, "" + imgFile.exists());*/
        /* File profileImgFile = getDownloadedFile(signup.getFirstName(), signup.getProfileImageURL());
        if(profileImgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(profileImgFile.getAbsolutePath());
            circleImageView.setImageBitmap(myBitmap);
        }

        File coverImgFile = getDownloadedFile(signup.getFirstName()+"_"+signup.getLastName(), signup.getCoverImageURL());
        if(coverImgFile.exists()){
            Bitmap myBitmap1 = BitmapFactory.decodeFile(coverImgFile.getAbsolutePath());
            Drawable d =new BitmapDrawable(getResources(),myBitmap1);
            headerLayout.setBackground(d);

        }*/
        File SDCardRoot = Environment.getExternalStorageDirectory();
        File imgFile = new File(SDCardRoot + fileName+".jpg");
        if(imgFile.exists()){
            return imgFile;
        }else{
            DLManager.useDownloadManager(downloadingUrl, fileName, MainActivity.this);
          /*  String[] params = {fileName,downloadingUrl};
            new AsynchronusTask().execute(params);*/
            File imgFile1 = new  File(SDCardRoot + fileName+".jpg");
            return imgFile1;
        }
    }


}

