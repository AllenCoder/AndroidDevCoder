package com.allen.androiddevcoder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class KLineView extends View {

    public KLineView(Context context) {
        super(context);
        init(null, 0);
    }

    public KLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public KLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.KLineView, defStyle, 0);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
