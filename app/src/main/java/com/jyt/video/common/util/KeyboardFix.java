package com.jyt.video.common.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.List;

public class KeyboardFix implements ViewTreeObserver.OnGlobalLayoutListener  {
    /**
     * 父容器
     */
    private View vRootView;
    /**
     * 父容器的高度
     */
    private int mContentHeight;
    /**
     * 标记，第一次回调时保存父容器的高度
     */
    private boolean isFirst = true;
    /**
     * 最后一次显示父容器的高度，用于判断是否显示虚拟键盘
     */
    private int mLastShowHeight;

    /**
     * 键盘监听
     */
    private List<OnKeyboardChangeCallback> mOnKeyboardChangeCallbacks;


    //1.首先我们传入rootView构造KeyboardFix。
    public KeyboardFix(View rootView) {
        if (rootView != null) {
            //2. 给rootView设置addOnGlobalLayoutListener回调。
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }

    @Override
    public void onGlobalLayout() {
        //3. addOnGlobalLayoutListener的onGlobalLayout回调时，就是键盘弹起和下降。
        possiblyResizeChildOfContent();
    }

    public void addOnKeyboardChangeListener(OnKeyboardChangeCallback onKeyboardChangeCallback) {
        mOnKeyboardChangeCallbacks.add(onKeyboardChangeCallback);
    }


    /**
     * 重新调整跟布局的高度
     */
    private void possiblyResizeChildOfContent() {
        //4. possiblyResizeChildOfContent()，computeUsableHeight()计算可见高度。
        //计算内容的可见高度
        int usableHeightNow = computeUsableHeight();
        if (isFirst) {
            //兼容华为等机型
            mContentHeight = usableHeightNow;
            isFirst = false;
        }
        //没有改变，忽略
        if (usableHeightNow == mLastShowHeight) {
            return;
        }
        mLastShowHeight = usableHeightNow;
        //5. 可见高度小于原始高度一定阀值，则当做为键盘弹起。否则为下降软键盘。
        boolean isShowKeyboard = usableHeightNow + 200 < mContentHeight;
        //6. 最后回调监听者。
        for (OnKeyboardChangeCallback listener : mOnKeyboardChangeCallbacks) {
            listener.onChange(isShowKeyboard, mContentHeight, usableHeightNow);
        }
    }

    /**
     * 计算内容的可见高度
     *
     * @return 父容器的可见高度
     */
    private int computeUsableHeight() {
        Rect rect = new Rect();
        vRootView.getWindowVisibleDisplayFrame(rect);
        return (rect.bottom - rect.top);
    }

    /**
     * 界面暂时时调用
     */
    public void onPause() {
        if (mOnKeyboardChangeCallbacks != null) {
            for (OnKeyboardChangeCallback callback : mOnKeyboardChangeCallbacks) {
                callback.onPause();
            }
        }
    }

    /**
     * 界面销毁时调用，取消注册，防止内存泄漏
     */
    public void onDestroy() {
        if (vRootView != null) {
            vRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            vRootView = null;
        }
        if (mOnKeyboardChangeCallbacks != null) {
            for (OnKeyboardChangeCallback callback : mOnKeyboardChangeCallbacks) {
                callback.onDestroy();
            }
            mOnKeyboardChangeCallbacks.clear();
            mOnKeyboardChangeCallbacks = null;
        }
    }


    /**
     * 虚拟键盘显示隐藏的监听
     */
    public interface OnKeyboardChangeCallback {
        /**
         * 虚拟键盘显示发生变化时调用
         *
         * @param isVisible     true为可见，false为不可见
         * @param contentHeight 原始内容高度
         * @param usableHeight  当前可用高度
         */
        void onChange(boolean isVisible, int contentHeight, int usableHeight);

        /**
         * 界面暂时回调
         */
        void onPause();

        /**
         * 界面销毁时回调
         */
        void onDestroy();
    }


    /**
     * 回调空实现
     */
    public static class OnKeyboardChangeAdapter implements OnKeyboardChangeCallback {

        @Override
        public void onChange(boolean isVisible, int contentHeight, int usableHeight) {
        }

        @Override
        public void onPause() {
        }

        @Override
        public void onDestroy() {
        }
    }

    /**
     * 存在输入框场景的监听，键盘弹起时，设置输入框距离底部一个键盘高度，键盘下降时取消距离
     */
    public static class CallbackToInput extends OnKeyboardChangeAdapter {
        /**
         * 输入框容器
         */
        private View vInputContainer;

        public CallbackToInput(View inputContainer) {
            vInputContainer = inputContainer;
        }

        @Override
        public void onChange(boolean isVisible, int contentHeight, int usableHeight) {
            super.onChange(isVisible, contentHeight, usableHeight);
            if (isVisible) {
                showInputView(usableHeight, contentHeight);
            } else {
                setBottomMargin(0);
            }
        }

        @Override
        public void onPause() {
            super.onPause();
            //界面暂停时，下降输入框
            setBottomMargin(0);
        }

        /**
         * 显示虚拟键盘时调用,显示输入框，如果绑定了列表控件则滚动到后一个item
         *
         * @param height 用于计算虚拟键盘的高度
         */
        private void showInputView(int height, int contentHeight) {
            if (vInputContainer == null) {
                return;
            }
            //虚拟键盘的高度
            int keyboardHeight = contentHeight - height;
            setBottomMargin(keyboardHeight);
        }

        /**
         * 重新设置inputContainer的底部边距
         */
        private void setBottomMargin(int bottomMargin) {
            if (vInputContainer == null) {
                return;
            }
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) vInputContainer.getLayoutParams();
            if (params.bottomMargin != bottomMargin) {
                params.bottomMargin = bottomMargin;
                vInputContainer.requestLayout();
            }
        }
    }

    /**
     * 存在列表场景的监听，一般键盘弹起时，列表需要滚动到底部，就可以添加该类型监听
     */
    public static class CallbackToList extends OnKeyboardChangeAdapter {
        private RecyclerView vRecyclerView;
        private boolean mIsReverse;

        /**
         * 是否反转，反转则滚动到第0位，非反转则滚动到列表的最后1位
         *
         * @param isReverse true为反转
         */
        public CallbackToList(RecyclerView recyclerView, boolean isReverse) {
            vRecyclerView = recyclerView;
            mIsReverse = isReverse;
        }

        @Override
        public void onChange(boolean isVisible, int contentHeight, int usableHeight) {
            super.onChange(isVisible, contentHeight, usableHeight);
            //键盘弹起时滚动到底部
            if (isVisible) {
                int position;
                if (mIsReverse) {
                    position = 0;
                } else {
                    if (vRecyclerView.getAdapter() == null) {
                        return;
                    }
                    position = vRecyclerView.getAdapter().getItemCount() - 1;
                }
                if (vRecyclerView != null && vRecyclerView.getAdapter() != null) {
                    vRecyclerView.scrollToPosition(position);
                }
            }
        }
    }
}
