package com.jyt.video.common.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by chenweiqi on 2017/6/20.
 */

public class SoftInputUtil {
    public static void showSoftKeyboard(Context context, View inputView){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (!imm.isActive())
            inputView.requestFocus();
            imm.showSoftInput(inputView,InputMethodManager.SHOW_IMPLICIT);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftKeyboard(Context context, View inputView){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputView.getWindowToken(), 0);
    }
}
