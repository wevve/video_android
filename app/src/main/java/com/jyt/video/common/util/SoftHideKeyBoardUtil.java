package com.jyt.video.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

/**
 * 解决键盘档住输入框
 * Created by SmileXie on 2017/4/3.
 */
public class SoftHideKeyBoardUtil {
    public static void assistActivity (Activity activity) {
        new SoftHideKeyBoardUtil(activity);
    }
    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;
    //为适应华为小米等手机键盘上方出现黑条或不适配
    private int contentHeight;//获取setContentView本来view的高度
    private boolean isfirst = true;//只用获取一次
    private  int statusBarHeight;//状态栏高度
    private SoftHideKeyBoardUtil(Activity activity) {
        //1､找到Activity的最外层布局控件，它其实是一个DecorView,它所用的控件就是FrameLayout
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);

        statusBarHeight = getStatusBarHeight(((Context) activity));

        //2､获取到setContentView放进去的View
//        mChildOfContent = content.getChildAt(0);
        mChildOfContent = content;
        //3､给Activity的xml布局设置View树监听，当布局有变化，如键盘弹出或收起时，都会回调此监听
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //4､软键盘弹起会使GlobalLayout发生变化
            public void onGlobalLayout() {
                if (isfirst) {
                    contentHeight = mChildOfContent.getHeight();//兼容华为等机型
                    isfirst = false;
                }
                //5､当前布局发生变化时，对Activity的xml布局进行重绘
                possiblyResizeChildOfContent();
            }
        });
        //6､获取到Activity的xml布局的放置参数
        frameLayoutParams = mChildOfContent.getLayoutParams();

        mChildOfContent.setBackgroundColor(Color.RED);
    }

    // 获取界面可用高度，如果软键盘弹起后，Activity的xml布局可用高度需要减去键盘高度
    private void possiblyResizeChildOfContent() {
        //1､获取当前界面可用高度，键盘弹起后，当前界面可用布局会减少键盘的高度
        int usableHeightNow = computeUsableHeight();
        //2､如果当前可用高度和原始值不一样
        if (usableHeightNow != usableHeightPrevious) {
            //3､获取Activity中xml中布局在当前界面显示的高度
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            //4､Activity中xml布局的高度-当前可用高度
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            //5､高度差大于屏幕1/4时，说明键盘弹出
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // 6､键盘弹出了，Activity的xml布局高度应当减去键盘高度

//                需要注意的是，在不同的SDK版本下，该FrameLayout所指的显示区域也不同。具体如下
//                在sdk 14+(native ActionBar),该显示区域指的是ActionBar下面的部分
//                在Support Library revision lower than 19，使用AppCompat，则显示区域包含ActionBar
//                在Support Library revision 19 (or greater)，使用AppCompat，则显示区域不包含ActionBar，即行为与第一种情况相同。
//                所以如果不使用Support Library或使用Support Library的最新版本，则R.id.content所指的区域都是ActionBar以下

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight;
                } else {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                }
            } else {
                frameLayoutParams.height = contentHeight;
            }
            //7､ 重绘Activity的xml布局
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        // 全屏模式下：直接返回r.bottom，r.top其实是状态栏的高度
        return (r.bottom - r.top);
    }
    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}