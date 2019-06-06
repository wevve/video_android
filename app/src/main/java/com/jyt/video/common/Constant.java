package com.jyt.video.common;

import android.os.Environment;

import java.io.File;

public class Constant {
    public static File getAppCacheFile(){
        File f = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+"/avideo");
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }



}
