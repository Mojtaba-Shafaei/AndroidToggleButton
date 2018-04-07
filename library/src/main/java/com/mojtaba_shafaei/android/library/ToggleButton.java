package com.mojtaba_shafaei.android.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mojtaba_shafaei.android.toggleButton.R;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mojtaba_shafaei.android.library.ToggleButton.ButtonBgEnum.BLUE;
import static com.mojtaba_shafaei.android.library.ToggleButton.ButtonBgEnum.GREEN;

public class ToggleButton extends LinearLayout {

    private final String TAG = "MyToggleButton";
    private Integer value = null;
    private TextView title, error;
    private android.widget.ToggleButton toggleButtonA, toggleButtonB;
    private boolean isMandatory = false;

    private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onChecked(int button) {
            //this is dummy
        }
    };

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

    private void refreshButtons() {
        try {
            if (null == value) {
                toggleButtonA.setChecked(false);
                toggleButtonB.setChecked(false);

                // setWeight(.5F, .5F);

            } else if (value == 1) {
                toggleButtonA.setChecked(true);
                toggleButtonB.setChecked(false);

                // setWeight(.6F, .4F);
            } else if (value == 0) {
                toggleButtonA.setChecked(false);
                toggleButtonB.setChecked(true);

                // setWeight(.4F, .6F);
            }
            toggleButtonA.requestLayout();
            toggleButtonB.requestLayout();

        } catch (Exception e) {
            Log.e(TAG, "refreshButtons: " + e);
        }
    }

    /**
     * @param weightOfA float <= 1F
     * @param weightOfB float <= 1F
     */
    private void setWeight(float weightOfA, float weightOfB) {
        LayoutParams lp = (LayoutParams) toggleButtonA.getLayoutParams();
        lp.weight = weightOfA;
        toggleButtonA.setLayoutParams(lp);

        lp = (LayoutParams) toggleButtonB.getLayoutParams();
        lp.weight = weightOfB;
        toggleButtonB.setLayoutParams(lp);

    }

    private void initWithAttrs(Context context, AttributeSet attrs) {
        TypedArray a = null;
        View root;

        if (attrs == null) {
            root = inflate(context, R.layout.inherited, this);

        } else {
            a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ToggleButton,
                    0, 0);

            final int ll = a.getInt(R.styleable.ToggleButton_tb_layoutDirection, 2);
            int layout;
            if (ll == 0) {
                layout = R.layout.l_ltr;
            } else if (ll == 1) {
                layout = R.layout.l_rtl;
            } else {
                layout = R.layout.inherited;
            }

            root = inflate(context, layout, this);
        }

        try {

            toggleButtonA = root.findViewById(R.id.toggleButtonA);
            toggleButtonB = root.findViewById(R.id.toggleButtonB);

            title = findViewById(R.id.title);
            error = findViewById(R.id.error);

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
                            checkedChangeListener.onChecked(ButtonsEnum.BUTTON_NON);
                        }
                    } else {
                        value = 1;
                        checkedChangeListener.onChecked(ButtonsEnum.BUTTON_A);
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
                            checkedChangeListener.onChecked(ButtonsEnum.BUTTON_NON);
                        }
                    } else {
                        value = 0;
                        checkedChangeListener.onChecked(ButtonsEnum.BUTTON_B);
                    }
                    refreshButtons();
                }
            });

            if (attrs == null) {
                isMandatory = true;
                toggleButtonA.performClick();
            } else {
                isMandatory = a.getBoolean(R.styleable.ToggleButton_tb_isMandatory, false);
                value = a.getInteger(R.styleable.ToggleButton_tb_default_button, -1);
                switch (value) {
                    case 0:
                        toggleButtonB.performClick();
                        break;

                    case 1:
                        toggleButtonA.performClick();
                        break;

                    default:
                        toggleButtonB.setChecked(false);
                        toggleButtonA.setChecked(false);
                }

                final String t = a.getString(R.styleable.ToggleButton_tb_title);
                setTitle(t);

                final int titleTextSize = a
                        .getDimensionPixelSize(R.styleable.ToggleButton_tb_titleTextSize, -1);
                if (titleTextSize != -1) {
                    title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
                }

                final int titleColor = a.getColor(R.styleable.ToggleButton_tb_titleTextColor,
                        ContextCompat.getColor(context, android.R.color.primary_text_light));
                title.setTextColor(titleColor);

                String tba = a.getString(R.styleable.ToggleButton_tb_A_text);
                setButtonAText(tba);

                String tbb = a.getString(R.styleable.ToggleButton_tb_B_text);
                setButtonBText(tbb);

                if (a.hasValue(R.styleable.ToggleButton_tb_background_A)) {
                    int buttonABackgroundResId = a.getResourceId(R.styleable.ToggleButton_tb_background_A,
                            R.drawable.bg_toggle_selector);
                    setBackgroundA(buttonABackgroundResId);
                } else {
                    int buttonAbgType = a.getInt(R.styleable.ToggleButton_tb_background_A_type, 1);
                    setBackgroundAType(buttonAbgType);
                }

                if (a.hasValue(R.styleable.ToggleButton_tb_background_B)) {
                    int buttonBBackgroundResId = a.getResourceId(R.styleable.ToggleButton_tb_background_B,
                            R.drawable.bg_toggle_selector);
                    setBackgroundB(buttonBBackgroundResId);
                } else {
                    int buttonBbgType = a.getInt(R.styleable.ToggleButton_tb_background_B_type, 1);
                    setBackgroundBType(buttonBbgType);
                }

                toggleButtonA.requestLayout();
                toggleButtonB.requestLayout();
            }

        } catch (Exception e) {
            Log.e(TAG, "initWithAttrs: " + e);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public void setBackgroundA(int buttonABackgroundResId) {
        toggleButtonA.setBackgroundResource(buttonABackgroundResId);
    }

    public void setBackgroundB(int buttonABackgroundResId) {
        toggleButtonB.setBackgroundResource(buttonABackgroundResId);
    }


    /**
     * @deprecated use {@link #setBackgroundA(int)}
     */
    @Deprecated
    public void setBackgroundAType(@ButtonBgEnum int type) {
        switch (type) {
            case BLUE:
                toggleButtonA.setBackgroundResource(R.drawable.bg_toggle_selector);
                break;

            case GREEN:
                toggleButtonA.setBackgroundResource(R.drawable.bg_toggle_green);
                break;
        }

    }

    /**
     * @deprecated use {@link #setBackgroundB(int)}
     */
    @Deprecated
    public void setBackgroundBType(@ButtonBgEnum int type) {
        switch (type) {
            case BLUE:
                toggleButtonB.setBackgroundResource(R.drawable.bg_toggle_selector);
                break;

            case GREEN:
                toggleButtonB.setBackgroundResource(R.drawable.bg_toggle_green);
                break;
        }

    }

    public synchronized Integer getValue() {
        return value;
    }

    public void setTitleVisibility(int visibility) {
        this.title.setVisibility(visibility);
    }

    public void setTitle(String title) {
        setTitle(title, null);
    }

    public void setTitle(String title, Typeface typeface) {
        if (typeface != null) {
            this.title.setTypeface(typeface);
        }

        if (isMandatory) {
            this.title.setText(title + ' ' + '*');
        } else {
            this.title.setText(title);
        }
    }

    public void setTitle(@StringRes int titleResId) {
        setTitle(getContext().getString(titleResId), null);
    }

    /**
     * @param titleTextSize in DP
     */
    public void setTitleTextSize(@Dimension float titleTextSize) {
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, titleTextSize);
    }

    public void setTitleTextSize(@DimenRes int titleTextSizeRes) {
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
                getContext().getResources().getDimensionPixelSize(titleTextSizeRes));
    }

    public void setTitleTextColor(ColorStateList titleTextColor) {
        title.setTextColor(titleTextColor);
    }

    public void setTitleTextColor(@ColorRes int titleTextColorRes) {
        title.setTextColor(ContextCompat.getColor(getContext(), titleTextColorRes));
    }

    public void setErrorTextColor(@ColorRes int color) {
        this.error.setTextColor(ContextCompat.getColor(getContext(), color));
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

    public void setButtonAText(String text) {
        setButtonAText(text, null, null);
    }

    public void setButtonATypeface(Typeface typeface) {
        setButtonAText(null, null, typeface);
    }

    public void setButtonATextColor(ColorStateList colorStateList) {
        setButtonAText(null, colorStateList, null);
    }

    /**
     * @param text      is {@link String}, may be empty or null.
     * @param typeface  is {@link Typeface}, may be null
     * @param textColor is a {@link ColorStateList} for Checkable Components[state_checked should implement], may be {@code null}.
     */
    public void setButtonAText(String text, ColorStateList textColor, Typeface typeface) {
        if (text != null) {
            this.toggleButtonA.setTextOff(text);
            this.toggleButtonA.setTextOn(text);
            this.toggleButtonA.setText(text);
        }
        if (textColor != null) {
            this.toggleButtonA.setTextColor(textColor);
        }

        if (typeface != null) {
            this.toggleButtonA.setTypeface(typeface);
        }
    }

    /**
     * @param size float, textSize as dip
     */
    public void setButtonATextSize(float size) {
        this.toggleButtonA.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    /**
     * @param size float, textSize as dip
     */
    public void setButtonBTextSize(float size) {
        this.toggleButtonA.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    public void setButtonACompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        toggleButtonA.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void setButtonACompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        toggleButtonA.setCompoundDrawables(left, top, right, bottom);
    }

    public void setButtonBCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        toggleButtonB.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void setButtonBCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        toggleButtonB.setCompoundDrawables(left, top, right, bottom);
    }

    public void setButtonBText(String text) {
        setButtonBText(text, null, null);
    }

    public void setButtonBTypeface(Typeface typeface) {
        setButtonBText(null, null, typeface);
    }

    public void setButtonBTextColor(ColorStateList colorStateList) {
        setButtonBText(null, colorStateList, null);
    }

    public void setButtonBText(String text, ColorStateList textColor, Typeface typeface) {
        if (text != null) {
            this.toggleButtonB.setTextOff(text);
            this.toggleButtonB.setTextOn(text);
            this.toggleButtonB.setText(text);
        }
        if (textColor != null) {
            this.toggleButtonB.setTextColor(textColor);
        }
        if (typeface != null) {
            this.toggleButtonB.setTypeface(typeface);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.checkedChangeListener = listener;
    }

    @Retention(RetentionPolicy.SOURCE)
    @Documented
    @IntDef({ButtonsEnum.BUTTON_A, ButtonsEnum.BUTTON_B, ButtonsEnum.BUTTON_NON})
    public @interface ButtonsEnum {

        int BUTTON_A = 1;
        int BUTTON_B = 0;
        int BUTTON_NON = -1;
    }

    public @interface ButtonBgEnum {

        int BLUE = 1;
        int GREEN = 2;
    }

    public interface OnCheckedChangeListener {

        void onChecked(@ButtonsEnum int button);
    }
}
