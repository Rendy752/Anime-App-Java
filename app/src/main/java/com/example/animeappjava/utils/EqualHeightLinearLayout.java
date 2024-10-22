package com.example.animeappjava.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class EqualHeightLinearLayout extends LinearLayout {

    public EqualHeightLinearLayout(Context context) {
        super(context);
    }

    public EqualHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EqualHeightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
        }

        if (maxHeight > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    LayoutParams params = (LayoutParams) child.getLayoutParams();
                    params.height = maxHeight;
                    child.setLayoutParams(params);
                }
            }

            setMeasuredDimension(getMeasuredWidth(), maxHeight);
        }
    }
}