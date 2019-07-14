package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class Clock extends View {

    private final static String TAG = Clock.class.getSimpleName();

    private static final int FULL_ANGLE = 360;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private int mWidth, mCenterX, mCenterY, mRadius;

    /**
     * properties
     */
    private int centerInnerColor;
    private int centerOuterColor;

    private int secondsNeedleColor;
    private int hoursNeedleColor;
    private int minutesNeedleColor;
    private int degreesColor;
    private int hoursValuesColor;
    private int numbersColor;
    private boolean mShowAnalog = true;
    private int hour ;
    private int minute;
    private int second;
    private int mH, mM, mS;
    private Paint mTextPaint,mPointerPaint;
    private float
            mPointR,
            mHourPointerLength, mHourPointerWidth,
            mMinutePointerLength, mMinutePointerWidth,
            mSecondPointerLength, mSecondPointerWidth;

    public Clock(Context context) {
        super(context);
        init(context, null);
        startTime();
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        startTime();
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        startTime();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        size = Math.min(widthWithoutPadding, heightWithoutPadding);
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.centerInnerColor = Color.LTGRAY;
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR;

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR;
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR;
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR;

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR;

        mTextPaint=new Paint();
        mPointerPaint=new Paint();
        numbersColor = Color.WHITE;
        mHourPointerLength = mRadius / 2;
        mHourPointerWidth =10;
        mMinutePointerLength = mRadius / 2;
        mMinutePointerWidth =20;
        mSecondPointerLength = mRadius / 3 * 2;
        mSecondPointerWidth = 10;

        mPointR=30;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mWidth = Math.min(getWidth(), getHeight());

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;

        if (mShowAnalog) {
            drawHours(canvas);
            drawDegrees(canvas);
            drawNeedles(canvas);
            drawCenter(canvas);
        } else {
            drawNumbers(canvas);
        }

    }

    private void drawDegrees(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);
        int rEnd = mCenterX - (int) (mWidth * 0.05f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0)
                paint.setAlpha(CUSTOM_ALPHA);
            else {
                paint.setAlpha(FULL_ALPHA);
            }

            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, paint);

        }
    }

    private void startTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getTime();
                }
            }
        }).start();
    }

    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        hour = hour % 12;
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        if (hour != mH || minute != mM || second != mS) {
            setTime(hour, minute, second);
            postInvalidate();
        }
    }

    private void setTime(int h, int m, int s) {
        mH = h;
        mM = m;
        mS = s;
    }
    private void drawNumbers(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(mWidth * 0.2f);
        textPaint.setColor(numbersColor);
        textPaint.setColor(numbersColor);
        textPaint.setAntiAlias(true);

        Calendar calendar = Calendar.getInstance();

        int amPm = calendar.get(Calendar.AM_PM);

        String time = String.format("%s:%s:%s%s",
                String.format(Locale.getDefault(), "%02d", mH),
                String.format(Locale.getDefault(), "%02d", mM),
                String.format(Locale.getDefault(), "%02d", mS),
                amPm == AM ? "AM" : "PM");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(time);
        spannableString.setSpan(new RelativeSizeSpan(0.3f), spannableString.toString().length() - 2, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // se superscript percent

        StaticLayout layout = new StaticLayout(spannableString, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        canvas.translate(mCenterX - layout.getWidth() / 2f, mCenterY - layout.getHeight() / 2f);
        layout.draw(canvas);
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private  void drawHours(final Canvas canvas){
         setTextPaint();

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();

        float ascent = Math.abs(fontMetrics.ascent);
        float descent = Math.abs(fontMetrics.descent);
        float fontHeight = ascent + descent;
        float offsetY = fontHeight / 2 - Math.abs(fontMetrics.descent);
        float fontWidth;
        float mR=320;
        float rad;
        mTextPaint.setColor(numbersColor);
        String h = "12";
        rad=360;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "01";
        rad=30;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "02";
        rad=60;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "03";
        rad=90;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "04";
        rad=120;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "05";
        rad=150;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "06";
        rad=180;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "07";
        rad=210;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "08";
        rad=240;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "09";
        rad=270;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "10";
        rad=300;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);
        h = "11";
        rad=330;
        canvas.drawText(h, (float) (mCenterX+mR*Math.sin(Math.PI*(rad/180))), (float) (mCenterY+15-mR*Math.cos(Math.PI*(rad/180))), mTextPaint);

    }
    private void setTextPaint() {

        mTextPaint.setTextSize(50);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }
    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        drawSecondPointer(canvas);
        drawHourPointer(canvas);
        drawMinutePointer(canvas);


    }
    private void drawHourPointer(Canvas canvas) {

        mPointerPaint.setStrokeWidth(mHourPointerWidth);
        mPointerPaint.setColor(hoursNeedleColor);
        float s = mH * 60 * 60 + mM * 60 + mS;
        float percentage = s / (12 * 60 * 60);
        float angle = 270 + 360 * percentage;
        mHourPointerLength=mRadius/2;
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        final float x = (float) (mHourPointerLength * Math.cos(Math.PI * 2 / 360 * angle));
        final  float y = (float) (mHourPointerLength * Math.sin(Math.PI * 2 / 360 * angle));
        canvas.drawLine(mCenterX,mCenterY+15, x+mCenterX, y+mCenterY+15, mPointerPaint);
    }

    private void drawMinutePointer(Canvas canvas) {

        mPointerPaint.setStrokeWidth(mMinutePointerWidth/2);
        mPointerPaint.setColor(minutesNeedleColor);
        float s = mM * 60 + mS;
        float percentage = s / (60 * 60);
        float angle = 270 + 360 * percentage;
        mMinutePointerLength=mRadius/3;
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        float x = (float) (mMinutePointerLength * Math.cos(Math.PI * 2 / 360 * angle));
        float y = (float) (mMinutePointerLength * Math.sin(Math.PI * 2 / 360 * angle));
        canvas.drawLine(mCenterX,mCenterY+15, x+mCenterX, y+mCenterY+15, mPointerPaint);
    }


    private void drawSecondPointer(Canvas canvas) {

        mPointerPaint.setStrokeWidth(mSecondPointerWidth/2);
        mPointerPaint.setColor(secondsNeedleColor);
        float s = mS;
        float percentage = s / 60;
        float angle = 270 + 360 * percentage;
        mSecondPointerLength=mRadius-120;
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        float x = (float) (mSecondPointerLength * Math.cos(Math.PI * 2 / 360 * angle));
        float y = (float) (mSecondPointerLength * Math.sin(Math.PI * 2 / 360 * angle));
        canvas.drawLine(mCenterX,mCenterY+15, x+mCenterX, y+mCenterY+15, mPointerPaint);
    }

    /**
     * Draw Center Dot
     *
     * @param canvas
     */
    private void drawCenter(Canvas canvas) {
        mPointerPaint.setColor(hoursNeedleColor);
        canvas.drawCircle(mCenterX,mCenterY+15, 15, mPointerPaint);
        mPointerPaint.setColor(secondsNeedleColor);
        canvas.drawCircle(mCenterX,mCenterY+15, 10, mPointerPaint);


    }

    public void setShowAnalog(boolean showAnalog) {
        mShowAnalog = showAnalog;
        invalidate();
    }

    public boolean isShowAnalog() {
        return mShowAnalog;
    }

}