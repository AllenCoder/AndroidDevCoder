package com.allen.linechart;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.DisplayUtils;
import util.MLog;
import util.NumberUtil;
import util.TouchEventUtil;
import util.Util;


/**
 * 指数大师图表
 */
public class LineChart extends View {
    private int ScreenHeight;
    private int ScreenWidth;
    private Region region;
    /**
     * 当前值
     */
    private float nowValue;
    /**
     * 起始值
     */
    private float startValue;
    /**
     * 当前日期
     */
    private Paint mPaint;

    private TextPaint mTextPaint;
    private String nowTime;
    private List<Float> lineData;
    private float locationY;
    private float locationX;

    private Paint PaintText;
    private Paint PaintBottom;

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    private boolean isOnDraw = false;
    private Paint mTextPaint1;
    private Paint mTextPaint2;

    public boolean isOnDraw() {
        return isOnDraw;
    }

    public void setOnDraw(boolean onDraw) {
        isOnDraw = onDraw;
    }

    private float maxValue;
    private boolean isLoading = true;
    private float lnSpace;
    private float startx;
    private float endx;
    private float starty;
    private float endy;
    private float initX;
    private float initY;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<Float> getLineData() {
        return lineData;
    }

    private Paint[] arrPaintArc;

    private Context mContext;

    public void setLineData(List<Float> lineData) {
        this.lineData = lineData;
    }

    public Paint indicationPaint;
    private HashMap<Integer, Float> valueMap;
    /**
     * 默认大盘上涨
     */
    private boolean isUp = true;

    public boolean isUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public static final float SCALE = 1.0f;
    private float[] scaleFloats = new float[]{SCALE, SCALE, SCALE};
    private Paint LoadingPaint;
    private int[] PaintColor = {getColor(R.color.color_1), getColor(R.color.color_2), getColor(R.color.color_3)};
    public static final int DEFAULT_SIZE = 45;
    private List<Animator> mAnimators;

    public List<Animator> getmAnimators() {
        return mAnimators;
    }

    public void setmAnimators(List<Animator> mAnimators) {
        this.mAnimators = mAnimators;
    }

    public LineChart(Context context) {
        super(context);
        init(null, 0);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /**
     * 是否移动
     */
    private boolean isMoved;

    /**
     * 是否释放
     */
    private boolean isReleased;
    /**
     * 计数器
     */
    private int mCounter;

    /**
     * @param attrs
     * @param defStyle
     */
    private static final int TOUCH_SLOP = 20;

    private void init(AttributeSet attrs, int defStyle) {
        mContext = getContext();
        setWillNotDraw(false);
        valueMap = new HashMap<>();
        /**
         * 此处不能开启canvas绘图硬件加速 会导致部分api 显示不正常
         */
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        ScreenWidth = displayMetrics.widthPixels;
        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(1, BlurMaskFilter.Blur.INNER);
        arrPaintArc = new Paint[5];
        for (int i = 0; i < 5; i++) {
            arrPaintArc[i] = new Paint();
            arrPaintArc[i].setColor(getColor(R.color.line_chart_dash_line));
            arrPaintArc[i].setStyle(Paint.Style.FILL);
            arrPaintArc[i].setStrokeWidth(4);
            arrPaintArc[i].setMaskFilter(PaintBGBlur);
        }
        PaintText = new Paint();
        PaintText.setColor(Color.BLUE);
        PaintText.setTextSize(30);
        PaintText.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint1 = new TextPaint();
        mTextPaint1.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint1.setTextAlign(Paint.Align.LEFT);
        mTextPaint2 = new TextPaint();
        mTextPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint2.setTextAlign(Paint.Align.LEFT);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(3);
        indicationPaint = new Paint();
        LoadingPaint = new Paint();
        LoadingPaint.setColor(Color.WHITE);
        LoadingPaint.setStyle(Paint.Style.FILL);
        LoadingPaint.setAntiAlias(true);
        arrPaintArc[0].setTextSize(25);
        arrPaintArc[0].setColor(getColor(R.color.line_chart_dash_line));
        arrPaintArc[3].setTextSize(25);
        arrPaintArc[0].setStyle(Paint.Style.STROKE);
        mLongPressRunable = new Runnable() {
            @Override
            public void run() {
                mCounter--;
                if (mCounter > 0 || isReleased || isMoved) {
                    return;
                }
                peformLongClick();
            }
        };
        PaintBottom = new TextPaint();
        PaintBottom.setFlags(Paint.ANTI_ALIAS_FLAG);
        PaintBottom.setTextAlign(Paint.Align.LEFT);
        PaintBottom.setAlpha((int) (255 * 0.3f));
        PaintBottom.setStrokeWidth(1);
        PaintBottom.setColor(getColor(R.color.white));
    }

    private void peformLongClick() {

        isOnDraw = true;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
/**
 * 沪深交易时间为4个小四一个有240个点
 */
        lnSpace = ScreenWidth / 242f;//标识间距
        startx = 0;
        endx = startx;
        starty = getBottom() - ConvertdptoPx(100);
        endy = 0;
        initX = startx;
        initY = starty;

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (isLoading) {
            float circleSpacing = 10;
            float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 48;
            float x = getWidth() / 2 - (radius * 2 + circleSpacing);
            float y = getHeight() / 2;
            LoadingPaint.setColor(Color.RED);
            for (int i = 0; i < 3; i++) {
                canvas.save();
                float translateX = x + (radius * 2) * i + circleSpacing * i;
                canvas.translate(translateX, y);
                canvas.scale(scaleFloats[i], scaleFloats[i]);
                LoadingPaint.setColor(PaintColor[i]);
                canvas.drawCircle(0, 0, radius, LoadingPaint);
//                canvas.drawRect(0,0,radius,radius,LoadingPaint);
                canvas.restore();
            }
        } else {
            /**
             * 画布背景
             */
            if (isUp) {
                canvas.drawColor(getColor(R.color.line_chart_up));
            } else {
                canvas.drawColor(getColor(R.color.line_chart_down));
            }
            mPaint.setColor(Color.YELLOW);
            /////////////////////////////
            //折线图
            /////////////////////////////
            Path p = new Path();
            Path path = new Path();

            if (lineData != null && lineData.size() > 0) {
                if (startValue - lineData.get(0) > 0) {
                    endy = initY + ((startValue - lineData.get(0)) / (2 * maxValue)) * (ConvertdptoPx(120));
                    /**
                     * 如果上涨
                     */
                } else {
                    endy = initY - ((lineData.get(0) - startValue) / (2 * maxValue)) * (ConvertdptoPx(120));

                }
                p.moveTo(initX, endy);
                path.moveTo(initX, endy);
                for (int i = 0; i < lineData.size(); i++) {
                    startx = initX + (i) * lnSpace;
                    endx = startx;
                    /**
                     * 如果下跌
                     */
                    if (startValue - lineData.get(i) > 0) {
                        endy = initY + ((startValue - lineData.get(i)) / (2 * maxValue)) * (ConvertdptoPx(120));
                        /**
                         * 如果上涨
                         */
                    } else {
                        endy = initY - ((lineData.get(i) - startValue) / (2 * maxValue)) * (ConvertdptoPx(120));

                    }
                    valueMap.put(i, endy);
                    p.lineTo(endx, endy);
                    path.lineTo(endx, endy);
                }
                mPaint.setColor(getColor(R.color.index_item_down_color));
                path.lineTo(endx, 0);
                path.lineTo(0, 0);
                path.close();
                Paint pathPaint = new Paint();
                pathPaint.setAntiAlias(true);
                pathPaint.setAntiAlias(true);
                pathPaint.setFilterBitmap(true);
                /**
                 * 设置渐变色图层
                 */
                Shader mShasder = new LinearGradient(0, getBottom() - ConvertdptoPx(35), 0, ScreenHeight / 2, getColor(R.color.line_color0), getColor(R.color.line_color), Shader.TileMode.MIRROR);
                pathPaint.setShader(mShasder);
                canvas.save();
                canvas.drawRect(0, ScreenHeight / 2 - 200, endx, getBottom() - ConvertdptoPx(35), pathPaint);
                canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
                canvas.clipPath(path);
                /**
                 * 画布背景
                 */
                if (isUp) {
                    canvas.drawColor(getColor(R.color.line_chart_up));
                } else {
                    canvas.drawColor(getColor(R.color.line_chart_down));
                }
                canvas.restore();

                /**
                 * 绘制折线
                 */
                arrPaintArc[0].setStrokeWidth(ConvertdptoPx(2));
                arrPaintArc[0].setAntiAlias(true);
                arrPaintArc[0].setFilterBitmap(true);
                canvas.drawPath(p, arrPaintArc[0]);
                /**
                 * 画指示值
                 */

                if (isOnDraw) {

                    int index = (int) (locationX / ScreenWidth * 242);
                    if (index < valueMap.size()) {
                        indicationPaint.setColor(Color.WHITE);
                        indicationPaint.setStrokeWidth(1);

                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.upsdowns_frame_icon);
                        indicationPaint.setColor(Color.WHITE);
                        indicationPaint.setTextSize(ConvertdptoPx(6));


                        canvas.drawBitmap(bitmap, locationX - bitmap.getWidth() / 2, getBottom() - ConvertdptoPx(182) - Util.calcTextHeight(indicationPaint, "+3.69%"), indicationPaint);
                        float value = lineData.get(index);
                        indicationPaint.setColor(getColor(R.color.number_color));


                        String text;
                        if (value < startValue) {
                            text = NumberUtil.getPercentValue((value - startValue) / startValue);
                        } else {
                            text = "+" + NumberUtil.getPercentValue((value - startValue) / startValue);
                        }
                        canvas.drawText(text + "%", locationX - bitmap.getWidth() / 2 + ConvertdptoPx(4), getBottom() - ConvertdptoPx(178), indicationPaint);
                        if (index < valueMap.size()) {
                            drawShadePoint(canvas, index * lnSpace, valueMap.get(index), text);
                        }
                        indicationPaint.setColor(getColor(R.color.white));
                        indicationPaint.setAlpha((int) (255 * 0.3f));
                        canvas.drawLine(locationX, getBottom() - ConvertdptoPx(38), locationX, getBottom() - ConvertdptoPx(176), indicationPaint);
                    }


                }
                drawString(canvas);

                drawShadePoint(canvas, endx, endy);
            }

        }
    }

    private int getColor(int res) {
        return getResources()
                .getColor(res);
    }

    /**
     * 绘制小圆点
     *
     * @param canvas
     * @param endx
     * @param endy
     */
    private void drawShadePoint(Canvas canvas, float endx, float endy) {
        /**
         * 绘制最后一个数据点
         */
        mPaint.setColor(getColor(R.color.white));
        canvas.drawCircle(endx, endy, ConvertdptoPx(3), mPaint);
        mPaint.setAlpha(125);
        canvas.drawCircle(endx, endy, ConvertdptoPx(6), mPaint);
    }

    /**
     * 绘制小圆点
     *
     * @param canvas
     * @param endx
     * @param endy
     */
    private void drawShadePoint(Canvas canvas, float endx, float endy, String text) {
        /**
         * 绘制最后一个数据点
         */
        mPaint.setColor(getColor(R.color.white));
        canvas.drawCircle(endx, endy, ConvertdptoPx(3), mPaint);
        mPaint.setAlpha(125);

        canvas.drawCircle(endx, endy, ConvertdptoPx(6), mPaint);


    }

    /**
     * 绘制数字净值
     *
     * @param canvas
     */
    private void drawString(Canvas canvas) {
        /**
         * 当前净值
         */
        mTextPaint.setTextSize(ConvertdptoPx(9));
        mTextPaint.setColor(Color.WHITE);
        //透明度80%
        mTextPaint.setAlpha((int) (255 * 0.8f));

        mTextPaint1.setTextSize(ConvertdptoPx(26));
        mTextPaint1.setColor(Color.WHITE);
        mTextPaint1.setTypeface(Typeface.DEFAULT_BOLD);

        mTextPaint2.setTextSize(ConvertdptoPx(9));
        mTextPaint2.setColor(Color.WHITE);
        /**
         * 3793.37
         */
        float positiony1 = getTop() + ConvertdptoPx(36);
        /**
         * 2015-11-06
         */
        float positiony2 = getTop() + ConvertdptoPx(56);
        /**
         * 3715.75
         */
        final float positiony3 = getTop() + ConvertdptoPx(88);
        final float positiony4 = getBottom() - ConvertdptoPx(43);
        final float positiony5 = getBottom() - ConvertdptoPx(25);
        /**
         * 这里可以用setTextSize()的另外一种形式，可以指定单位：
         setTextSize(int unit, int size)
         TypedValue.COMPLEX_UNIT_PX : Pixels
         TypedValue.COMPLEX_UNIT_SP : Scaled Pixels
         TypedValue.COMPLEX_UNIT_DIP : Device Independent Pixels

         */

        /**
         * 产品名字
         */
        if (lineData != null) {
            float textwidth = mTextPaint2.measureText("以上数据仅供参考，不作为结算依据");
            canvas.drawText("以上数据仅供参考，不作为结算依据", getRight() / 2 - textwidth / 2, getBottom() - ConvertdptoPx(9), mTextPaint2);
            /**
             * 当前大盘值 3793.37
             */
            canvas.drawText(nowValue + "", ConvertdptoPx(36), positiony1,
                    mTextPaint1);

            /**
             * 当前上涨幅度
             */
            float upValueRate = mTextPaint1.measureText("+2.36%");
            if (nowValue < startValue) {
                canvas.drawText(NumberUtil.getPercentValue((nowValue - startValue) / startValue) + "%", getRight() - ConvertdptoPx(36) - upValueRate, positiony1
                        ,
                        mTextPaint1);
            } else {
                canvas.drawText("+" + NumberUtil.getPercentValue((nowValue - startValue) / startValue) + "%", getRight() - ConvertdptoPx(36) - upValueRate, positiony1
                        ,
                        mTextPaint1);
            }


            /**
             * 正在时间2015
             */
            mTextPaint.setTextSize(ConvertdptoPx(11));
            canvas.drawText(nowTime, getLeft() + ConvertdptoPx(36), positiony2
                    ,
                    mTextPaint);
            mTextPaint.setTextSize(ConvertdptoPx(10));
            /**
             * 当前上涨幅度 87.4
             */
            /**
             * 当前上涨幅度
             */
            float up = nowValue - startValue;
            mTextPaint.setTextSize(ConvertdptoPx(11));
            float upTextHeight = Util.calcTextWidth(mTextPaint, NumberUtil.getAmountValue(up));

            if (up < 0) {
                canvas.drawText("" + NumberUtil.getAmountValue(up), getRight() - ConvertdptoPx(40) - upTextHeight, positiony2
                        ,
                        mTextPaint);
            } else {
                canvas.drawText("+" + NumberUtil.getAmountValue(up), getRight() - ConvertdptoPx(40) - upTextHeight, positiony2
                        ,
                        mTextPaint);
            }
            mTextPaint.setTextSize(ConvertdptoPx(10));
            canvas.drawText(NumberUtil.getRoundUp(startValue + maxValue, 2), getLeft() + ConvertdptoPx(8), positiony3, mTextPaint);
            float width = mTextPaint.measureText("-7.78%");
            /**
             * +9.78
             */
            canvas.drawText("+" + NumberUtil.getPercentValue(maxValue / startValue) + "%", getRight() - ConvertdptoPx(8) - width, positiony3, mTextPaint);
            /**
             * -7.78%
             */

            canvas.drawText("-" + NumberUtil.getPercentValue(maxValue / startValue) + "%", getRight() - ConvertdptoPx(8) - mTextPaint.measureText("-7.78%"), positiony4, mTextPaint);
            /**
             * 3698.19
             */
            canvas.drawText(NumberUtil.getRoundUp(startValue - maxValue, 2), getLeft() + ConvertdptoPx(8), positiony4, mTextPaint);

            canvas.drawText("09:30", getLeft() + ConvertdptoPx(8), positiony5, mTextPaint);
            canvas.drawText("11：30/13：00", ScreenWidth / 2 - Util.calcTextWidth(mTextPaint, "11：30/13：00") / 2, positiony5, mTextPaint);

            width = mTextPaint.measureText("15:00");
            canvas.drawText("15:00", getRight() - ConvertdptoPx(8) - width, positiony5, mTextPaint);
            /**
             * 画分割线
             */
            final int positony6 = getBottom() - ConvertdptoPx(37);
            PaintBottom.setStrokeWidth(0.4f);
            canvas.drawLine(0, positony6, getRight(), positony6, PaintBottom);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setStyle(Paint.Style.STROKE);
            p.setColor(Color.WHITE);
            p.setStrokeWidth(1);
            PathEffect effect = new DashPathEffect(new float[]{ConvertdptoPx(4), ConvertdptoPx(4), ConvertdptoPx(4), ConvertdptoPx(4)}, 1);
            p.setPathEffect(effect);
            p.setAlpha(125);//透明度50%
            p.setColor(getColor(R.color.line_chart_dash_line));
            final int stopY = getBottom() - ConvertdptoPx(100);
            /**
             * X轴
             */
            canvas.drawLine(0, stopY, ScreenWidth, stopY, p);
            /**
             * Y轴
             */
            canvas.drawLine(ScreenWidth / 2, getBottom() - ConvertdptoPx(173), ScreenWidth / 2, positony6, p);
        }

    }

    private int ConvertdptoPx(int dp) {
        return DisplayUtils.dip2px(mContext, dp);
    }


    public float getNowValue() {
        return nowValue;
    }

    public void setNowValue(float nowValue) {
        this.nowValue = nowValue;
    }

    public float getStartValue() {
        return startValue;
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    private float xDistance, yDistance, xLast, yLast;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        MLog.d("dispatchTouchEvent", TouchEventUtil.getTouchAction(event.getAction()));

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = event.getX();
                yLast = event.getY();
                mCounter++;
                isReleased = false;
                isMoved = false;
                locationY = event.getY();
                locationX = event.getX();
                postDelayed(mLongPressRunable, ViewConfiguration.getLongPressTimeout());
                break;
            case MotionEvent.ACTION_MOVE:

                final float curX = event.getX();
                final float curY = event.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                MLog.d("dispatchTouchEvent", "xDistance>>>" + xDistance + "yDistance>>>" + yDistance);
                isOnDraw = true;
                locationY = event.getY();
                locationX = event.getX();

                isMoved = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isReleased = true;
                isOnDraw = false;
                invalidate();
                break;

            default:
                break;
        }
        /**
         * /*
         * 备注1：此处一定要将return super.onTouchEvent(event)修改为return true，原因是：
         * 1）父类的onTouchEvent(event)方法可能没有做任何处理，但是返回了false。
         * 2)一旦返回false，在该方法中再也不会收到MotionEvent.ACTION_MOVE及MotionEvent.ACTION_UP事件。
         */
        return true;


    }


    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(DisplayUtils.dip2px(getContext(), DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(DisplayUtils.dip2px(getContext(), DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<>();
        int[] delay = new int[]{120, 240, 360};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1f, 0.3f, 1);
            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delay[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setAnimationStatus(List<Animator> mAnimators, AnimStatus animStatus) {
        if (mAnimators == null) {
            return;
        }
        int count = mAnimators.size();
        setmAnimators(mAnimators);
        for (int i = 0; i < count; i++) {
            Animator animator = mAnimators.get(i);
            boolean isRunning = animator.isRunning();
            switch (animStatus) {
                case START:
                    if (!isRunning) {
                        animator.start();
                    }
                    break;
                case END:
                    if (isRunning) {
                        animator.end();
                    }
                    break;
                case CANCEL:
                    if (isRunning) {
                        animator.cancel();
                    }
                    break;
            }
        }
    }

    public enum AnimStatus {
        START, END, CANCEL
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAnimationStatus(mAnimators, AnimStatus.CANCEL);
    }

    private Runnable mLongPressRunable;
}
