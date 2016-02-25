package com.allen.androiddevcoder.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者: allen on 16/1/28.
 */
public class VerticalTextView extends View {
    private Paint mPaint;
    public VerticalTextView(Context context) {
        super(context);
        init();
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
  private  void   init(){
      mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
      mPaint.setTextSize(25);
  }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("铝腚",20,120,20,120,mPaint);
        canvas.translate(-90,-90);
    }
}
