package com.jyt.video.common;

import android.os.Environment;

import java.io.File;

public class Constant {
    public static File getAppCacheFile(){
        return new File( Environment.getExternalStorageDirectory().getAbsolutePath()+"/avideo");
    }

}
