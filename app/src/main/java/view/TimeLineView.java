package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 洒笑天涯 on 2016/1/10.
 */
public class TimeLineView extends View {
    /**
     * 线条的颜色,绘制的圆的主要颜色
     */
    private int mLineColor = Color.BLUE;
    /***
     * 主要画笔的颜色
     */
    private int mainColor = Color.BLUE;
    /**
     * 中心绘制的圆的半径
     */
    private int radius = dip2px(8);
    /***
     * 主要的画笔,线条的画笔
     */
    private Paint mainPaint, linePaint;
    /**
     * 第一个需不需要绘制头部的线
     */
    private boolean isFrist = false;
    /***
     * 是不是最后一个不需要绘制后半部分的线
     */
    private boolean isLast = false;
    /***
     * 位置的风格
     */
    private int mPositiontype = 1;
    /***
     * 中间需要绘制的东西的风格
     */
    private int mCenterType;
    /***
     * 如果是圆环,就可以设置圆环的长度了
     */
    private float strokeWidth = dip2px(1);
    /***
     * 线与中心圆的距离
     */
    private int margin = dip2px(1);

    /***
     * 前半部分的线条长度,后半部分的线条长度
     */
    private int beginLineHeight, endLineHeight;
    /***
     * 若用户需要对准的对象,并非是规规矩矩的顶部,中间,底部的时候,需要更具这个属性,来确定其,离顶部有多远
     */
    private int marginTop;
    //    private Drawable drawable;
    private Bitmap mCenterBmp;

    /***
     * @return
     */
    private int LineWidth = dip2px(1);

    public TimeLineView(Context context) {
        super(context, null);
    }


    public TimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        iniPaint();
        Log.v("myDestine","我被创建出来");

    }

    public TimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniPaint();
    }

    /***
     * @return 设置线条的颜色
     */
    public void setLineWidth(int lineWidth) {
        LineWidth = dip2px(lineWidth);
        linePaint.setStrokeWidth(lineWidth);
    }
        /*
    *//***
     * @param textSize 需要对其的TextView 的字体大小;主要是对了对其
     * @return 离顶部的高度,
     *//*
    public void setMarginTop(int marginTop, int textSize) {
        this.marginTop = marginTop;
        this.textSize=textSize;
    }*/

    /***
     * @return 离顶部的高度,
     */
    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    /***
     * 设置圆圈所在的位置
     */
    public static class POSITIONTYPE {
        /***
         * 顶部
         */
        public static final int TOP = 0;
        /***
         * 中间
         */
        public static final int CENTER = 1;
        /***
         * 底部
         */
        public static final int BOTTOM = 2;
        /***
         * 随机
         */
        public static final int RANDOM = 3;
    }

    /***
     * 中间所需要绘制的东西的风格,可以是圆圈,环形,或者是一个圆形中间一个圆形,图片,或者是用户可以自定绘制一些图形;
     */
    public static class CENTERTYPE {
        /***
         * 圆形
         */
        public static final int CYCLE = 0;
        /***
         * 环形
         */
        public static final int RING = 1;
        /***
         * 圆形,中间有个小圆
         */
        public static final int RINGCYCLE = 2;
        /***
         * 绘制两个圆环
         */
        public static final int TWORING = 3;

        /***
         * 其他:包括图片或者自定shape
         */
        public static final int OTHER = 4;
    }

    /***
     * @return 绘制图片的半径
     */
    public void setRadius(int radius) {
        this.radius = dip2px(radius);
    }
    /**
     * 进行画笔的初始化
     */
    private void iniPaint() {
        mainPaint = iniPainter();
        linePaint = iniPainter();
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mainPaint.setColor(mainColor);
        linePaint.setColor(mLineColor);
    }

    public TimeLineView setCenterBmp(Bitmap mCenterBmp) {
        this.mCenterBmp = mCenterBmp;
        radius = 0;
        return this;
    }

    public TimeLineView setCenterBmp(int res) {
        this.mCenterBmp = BitmapFactory.decodeResource(getResources(), res);
        radius = 0;
        return this;
    }

    /**
     * 画笔初始化
     */
    private Paint iniPainter() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        return paint;
    }

    public TimeLineView setMainColor(int mainColor) {
        this.mainColor = mainColor;
        mainPaint.setColor(mainColor);
        return this;
    }

    public TimeLineView setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
        linePaint.setColor(mLineColor);
        return this;
    }

    /***
     * 绘制时间轴主要部分的类型
     *
     * @return
     */
    public TimeLineView setCenterType(int mCenterType) {
        this.mCenterType = mCenterType;
        return this;
    }

    /***
     * @return 设置显示的位置
     */
    public void setPositiontype(int mPositiontype) {
        this.mPositiontype = mPositiontype;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int parentHeight = canvas.getHeight();
        int parentWidth = canvas.getWidth();
        int lineHeight = (parentHeight - radius * 2) / 2;

        switch (mPositiontype) {
            case POSITIONTYPE.TOP:
                beginLineHeight = endLineHeight = dip2px(1);//使隔开小小的空间,防止误差
                if (CENTERTYPE.OTHER == mCenterType && mCenterBmp != null) {
                    endLineHeight = endLineHeight + mCenterBmp.getHeight();
                }
                break;
            case POSITIONTYPE.CENTER:
                beginLineHeight = endLineHeight = lineHeight;//居中
                if (CENTERTYPE.OTHER == mCenterType) {//如果绘制的是图片,长短就应该根据图片的大小来计算
                    if (mCenterBmp == null) return;
                    beginLineHeight = (parentHeight - mCenterBmp.getHeight()) / 2;
                    endLineHeight = parentHeight / 2 + mCenterBmp.getHeight() / 2;
                    //如何是绘制图片的话,就不需要radius,这里置为0,否则会对其他造成影响
                    radius = 0;
                }
                break;
            case POSITIONTYPE.BOTTOM:
                beginLineHeight = lineHeight * 2;
                endLineHeight = 0;
                break;
            case POSITIONTYPE.RANDOM:
                endLineHeight = beginLineHeight = dip2px(marginTop + 2) - margin;
                if (CENTERTYPE.OTHER == mCenterType && mCenterBmp != null) {//距离顶部有多少距离
                    endLineHeight = mCenterBmp.getHeight() + dip2px(marginTop);
                }
                break;
        }
        drawCenters(canvas, parentHeight, parentWidth);
    }

    /***
     * @return 绘制中间的主要部分
     */
    private void drawCenters(Canvas canvas, int parentHeight, int parentWidth) {
        switch (mCenterType) {
            case CENTERTYPE.CYCLE:
                Log.v("myDestine","为什么不执行我设置的呢?");
                drawCycle(canvas, parentHeight, parentWidth);
                break;
            case CENTERTYPE.RING:
                drawRing(canvas, parentHeight, parentWidth);
                break;
            case CENTERTYPE.RINGCYCLE:
                drawRingCycle(canvas, parentHeight, parentWidth);
                break;
            case CENTERTYPE.TWORING:
                drawTwoRing(canvas, parentHeight, parentWidth);
                break;
            case CENTERTYPE.OTHER:
                Log.v("myDestine","我在这里画画");
                drawDrawable(canvas, parentHeight, parentWidth);
                break;
        }
    }

    /***
     * @return 绘制的是图片的话;绘制图片的时候,只有居中的这一种情况是没有问题的;现在解决
     */
    private void drawDrawable(Canvas canvas, int parentHeight, int parentWidth) {

        drawLine(canvas, parentHeight, parentWidth);
        canvas.drawBitmap(mCenterBmp, parentWidth / 2 - mCenterBmp.getWidth() / 2, beginLineHeight, mainPaint);
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = dip2px(strokeWidth);
        mainPaint.setStrokeWidth(this.strokeWidth);
    }

    /****
     * 绘制圆环和中间的小圆
     *
     * @return
     */
    private void drawRingCycle(Canvas canvas, int parentHeight, int parentWidth) {
        mainPaint.setStyle(Paint.Style.FILL);
        drawLine(canvas, parentHeight, parentWidth);
        canvas.drawCircle(parentWidth / 2, beginLineHeight + radius, radius / 2, mainPaint);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(parentWidth / 2, beginLineHeight + radius, radius, mainPaint);
    }

    /****
     * 绘制两个圆环
     *
     * @return
     */
    private void drawTwoRing(Canvas canvas, int parentHeight, int parentWidth) {
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(strokeWidth);
        drawLine(canvas, parentHeight, parentWidth);
        canvas.drawCircle(parentWidth / 2, beginLineHeight + radius, radius / 2, mainPaint);
        canvas.drawCircle(parentWidth / 2, beginLineHeight + radius, radius, mainPaint);
    }

    /***
     * @return 绘制圆环
     */
    private void drawRing(Canvas canvas, int parentHeight, int parentWidth) {
        drawLine(canvas, parentHeight, parentWidth);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(parentWidth / 2, beginLineHeight + radius, radius, mainPaint);
    }

    /***
     * 绘制圆形
     *
     * @return
     */
    private void drawCycle(Canvas canvas, int parentHeight, int parentWidth) {
        mainPaint.setStyle(Paint.Style.FILL);
        drawLine(canvas, parentHeight, parentWidth);
        canvas.drawCircle(parentWidth / 2, beginLineHeight + radius, radius, mainPaint);

    }

    /***
     * @return 绘制两边的颜色
     */
    private void drawLine(Canvas canvas, int parentHeight, int parentWidth) {
        if (!isFrist && mPositiontype != POSITIONTYPE.TOP) {//不是第一,需要绘制前一半线条
            canvas.drawLine(parentWidth / 2, 0, parentWidth / 2, beginLineHeight - margin, linePaint);
        }
        if (!isLast)//不是最后,需要绘制后一半线条
            canvas.drawLine(parentWidth / 2, endLineHeight + radius * 2 + margin, parentWidth / 2, parentHeight, linePaint);
    }

    /***
     * @return 是不是第一个不需要绘制
     */
    public TimeLineView isFrist(boolean isFrist) {
        this.isFrist = isFrist;
        return this;
    }

    public TimeLineView setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    /***
     * @return 是不是最后一个不需要绘制
     */
    public TimeLineView setIsLast(boolean isLast) {
        this.isLast = isLast;
        return this;
    }

    public int dip2px(int height) {
        return (int) (getContext().getResources().getDisplayMetrics().density * height + 0.5f);
    }

    /***
     * @return sp2px
     */
    public int sp2px(int textSize) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (textSize * fontScale + 0.5f);
    }
}
