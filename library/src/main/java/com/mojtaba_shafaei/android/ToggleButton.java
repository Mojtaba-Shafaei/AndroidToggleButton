package com.mojtaba_shafaei.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by mojtaba on 10/3/16.
 */

public class ToggleButton extends RelativeLayout {
    private final String TAG = "MyToggleButton";
    private Integer value = null;
    private TextView title, error;
    private android.widget.ToggleButton toggleButtonA, toggleButtonB;
    private boolean isMandatory = false;
    /////////////////////////////////////////////////////////
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;

    /////////////////////////////////////////////////////////
    public ToggleButton(Context context) {
        super(context);
        initWithAttrs(context, null);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithAttrs(context, attrs);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithAttrs(context, attrs);
    }

    @TargetApi(21)
    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithAttrs(context, attrs);
    }

    private void refreshButtons() {
        try {
            if (null == value) {
                toggleButtonA.setChecked(false);
                toggleButtonB.setChecked(false);
            } else if (value == 1) {
                toggleButtonA.setChecked(true);
                toggleButtonB.setChecked(false);
            } else if (value == 0) {
                toggleButtonA.setChecked(false);
                toggleButtonB.setChecked(true);
            }
            toggleButtonA.requestLayout();
            toggleButtonB.requestLayout();

        } catch (Exception e) {
            Log.e(TAG, "refreshButtons: " + e);
        }
    }

    private void initWithAttrs(Context context, AttributeSet attrs) {
        TypedArray a = null;
        View root;

        if (attrs == null) {
            root = inflate(context, com.mojtaba_shafaei.android.R.layout.ltr, this);

        } else {
            a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    com.mojtaba_shafaei.android.R.styleable.ToggleButton,
                    0, 0);
            root = inflate(context, a.getInt(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_layout_direction, LAYOUT_DIRECTION_LTR) == LAYOUT_DIRECTION_LTR ? com.mojtaba_shafaei.android.R.layout.ltr : com.mojtaba_shafaei.android.R.layout.rtl, this);
        }


        try {

            toggleButtonA = root.findViewById(com.mojtaba_shafaei.android.R.id.toggleButtonA);
            toggleButtonB = root.findViewById(com.mojtaba_shafaei.android.R.id.toggleButtonB);

            title = findViewById(com.mojtaba_shafaei.android.R.id.title);
            error = findViewById(com.mojtaba_shafaei.android.R.id.error);

            toggleButtonA.setChecked(false);
            toggleButtonB.setChecked(false);

            toggleButtonA.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != value && value == 1) {
                        if (isMandatory) {
                            value = 1;
                        } else {
                            value = null;
                        }
                    } else {
                        value = 1;
                    }
                    refreshButtons();
                }
            });

            toggleButtonB.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != value && value == 0) {
                        if (isMandatory) {
                            value = 0;
                        } else {
                            value = null;
                        }
                    } else {
                        value = 0;
                    }
                    refreshButtons();
                }
            });

            if (attrs == null) {
                isMandatory = true;
                toggleButtonA.setChecked(true);


            } else {
                isMandatory = a.getBoolean(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_isMandatory, false);
                final int defaultButton = a.getInteger(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_default_button, -1);
                switch (defaultButton) {
                    case 0:
                        toggleButtonB.setChecked(true);
                        break;

                    case 1:
                        toggleButtonA.setChecked(true);
                        break;

                    default:
                        toggleButtonB.setChecked(false);
                        toggleButtonA.setChecked(false);
                }

                final String t = a.getString(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_title);
                if (isMandatory) {
                    this.title.setText(t + " *");
                } else {
                    this.title.setText(t);
                }

                final int titleTextSize = a.getDimensionPixelSize(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_titleTextSize, -1);
                if (titleTextSize != -1) {
                    title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
                }

                final int titleColor = a.getColor(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_titleTextColor, ContextCompat.getColor(context, android.R.color.primary_text_light));
                title.setTextColor(titleColor);

                String tba = a.getString(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_A_text);
                this.toggleButtonA.setTextOff(tba);
                this.toggleButtonA.setTextOn(tba);
                this.toggleButtonA.setText(tba);

                String tbb = a.getString(com.mojtaba_shafaei.android.R.styleable.ToggleButton_tb_B_text);
                this.toggleButtonB.setTextOff(tbb);
                this.toggleButtonB.setTextOn(tbb);
                this.toggleButtonB.setText(tbb);

                toggleButtonA.requestLayout();
                toggleButtonB.requestLayout();
            }
        } catch (Exception e) {
            Log.e(TAG, "initWithAttrs: " + e);
        } finally {
            if (a != null)
                a.recycle();
        }
    }

    public synchronized Integer getValue() {
        return value;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setTitle(@StringRes int titleResId) {
        this.title.setText(titleResId);
    }

    /**
     * @param titleTextSize in DP
     */
    public void setTitleTextSize(@Dimension float titleTextSize) {
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, titleTextSize);
    }

    public void setTitleTextSize(@DimenRes int titleTextSizeRes) {
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getContext().getResources().getDimensionPixelSize(titleTextSizeRes));
    }

    public void setTitleTextColor(ColorStateList titleTextColor) {
        title.setTextColor(titleTextColor);
    }

    public void setTitleTextColor(@ColorRes int titleTextColorRes) {
        title.setTextColor(ContextCompat.getColor(getContext(), titleTextColorRes));
    }

    public void setTitleTypeface(Typeface typeface) {
        title.setTypeface(typeface);
    }

    public void setErrorEnabled(boolean b) {
        error.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    public void setError(String s) {
        error.setText(s);
    }

    public void setError(int s) {
        error.setText(s);
    }

    public void setValue(Integer i) {
        this.value = i;
        refreshButtons();
    }
}
