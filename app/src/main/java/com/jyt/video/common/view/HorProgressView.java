package com.jyt.video.common.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.jyt.video.common.util.DensityUtil;

public class HorProgressView extends View {

    Paint backPaint;
    Paint frontPaint;


    int width;
    int height;


    RectF backRectF;

    int radius = 0;

    float percent = 0.5f;

    public HorProgressView(Context context) {
        this(context,null);
    }

    public HorProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint(){
        backPaint = new Paint();
        backPaint.setStrokeCap(Paint.Cap.ROUND);
        backPaint.setAntiAlias(true);
        backPaint.setColor(Color.parseColor("#CBCBCB"));
        backPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        frontPaint = new Paint();
        frontPaint.setStrokeCap(Paint.Cap.ROUND);
        frontPaint.setAntiAlias(true);

        radius = DensityUtil.dpToPx(getContext(),5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(backRectF, radius,radius,backPaint);

        LinearGradient linearGradient =new LinearGradient(0,0,width*percent,0, Color.parseColor("#B95BEE"),Color.parseColor("#8B55F6"), Shader.TileMode.CLAMP);
        frontPaint.setShader(linearGradient);
        canvas.drawRoundRect(backRectF.left,backRectF.top,backRectF.right*percent,backRectF.bottom,radius,radius,frontPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        width = measureWidth(minimumWidth, widthMeasureSpec);
        height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);




        float top = getPaddingTop() + backPaint.getStrokeWidth()/2;
        float bottom = top+ (height - backPaint.getStrokeWidth()) - getPaddingBottom();
        float left = getPaddingLeft() + backPaint.getStrokeWidth()/2;;
        float right = left + (width - backPaint.getStrokeWidth() - getPaddingRight());


        backRectF = new RectF(left,top,right,bottom);

    }

    public int measureWidth(int defaultWidth, int measureSpec){
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = (int) (backPaint.getStrokeWidth()*3 + getPaddingLeft() + getPaddingRight());
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) (backPaint.getStrokeWidth()*3 + getPaddingTop() + getPaddingBottom());
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;
    }



}
