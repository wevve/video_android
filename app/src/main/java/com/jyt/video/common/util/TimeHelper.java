package com.jyt.video.common.util;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;
import com.jyt.video.common.helper.UserInfo;

public class TimeHelper {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };
    TimerListener timerListener;
    TimerValueListener valueListener;
    private String oriText;
    private String timerText;
    TimerRunnable timerRunnable;
    private String tag;

    //等待时间  单位秒
    private int time = 60;

    private long endTime;

    public boolean isLoading = false;

    TextView textView;
    public boolean needRecord;
    public TimeHelper(TextView textView) {
        this.textView = textView;
        timerRunnable = new TimerRunnable();

    }

    public void setValueListener(TimerValueListener valueListener) {
        this.valueListener = valueListener;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setOriText(String oriText){
        this.oriText = oriText;
    }
    public void setTimerText(String timerText){
        this.timerText = timerText;
    }

    public void setTag(String tag){
            this.tag = tag;
    }

    public void start(){

        endTime = System.currentTimeMillis()+(time*1000);
        if (needRecord)
            UserInfo.add(tag,endTime);
        handler.post(timerRunnable);

        if (timerListener!=null)
            timerListener.timeStateChangeListener("start");


        if (valueListener!=null)
            valueListener.value(time);

    }

    public void stop(){
        handler.removeCallbacks(timerRunnable);

        if (timerListener!=null)
            timerListener.timeStateChangeListener("end");

    }

    public boolean isNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(boolean needRecord) {
        this.needRecord = needRecord;
    }

    public void resume(){
            endTime = UserInfo.get(tag,0L);
        handler.post(timerRunnable);
    }


    class TimerRunnable implements Runnable{

        @Override
        public void run() {

            long cur = System.currentTimeMillis();
            if (cur>=endTime){
                handler.removeCallbacks(this);
                isLoading = false;

                if (textView!=null && !TextUtils.isEmpty(oriText)) {
                    textView.setText(oriText);
                }
                if (timerListener!=null)
                    timerListener.timeStateChangeListener("end");

            }else {
                isLoading = true;
                handler.postDelayed(this,1000);

                int time = (int) ((endTime - cur)/1000);

                if (textView!=null && !TextUtils.isEmpty(timerText)) {
                    textView.setText(String.format(timerText, time));
                }

                if (valueListener!=null)
                    valueListener.value(time);

                if (timerListener!=null)
                    timerListener.timeStateChangeListener("loading");

            }

        }
    }

    public void setTimerListener(TimerListener timerListener) {
        this.timerListener = timerListener;
    }

    public interface TimerListener{
        void timeStateChangeListener(String state);
    }

    public interface TimerValueListener{
        void value(int value);
    }


}
