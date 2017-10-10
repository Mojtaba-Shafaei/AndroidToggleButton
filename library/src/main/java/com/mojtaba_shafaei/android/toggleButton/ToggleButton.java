package com.mojtaba_shafaei.android.toggleButton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mojtaba_shafaei.android.toggleButton.ToggleButton.ButtonBgEnum.BLUE;
import static com.mojtaba_shafaei.android.toggleButton.ToggleButton.ButtonBgEnum.GREEN;

public class ToggleButton extends LinearLayoutCompat {
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
				 if(null == value){
						toggleButtonA.setChecked(false);
						toggleButtonB.setChecked(false);
				 } else if(value == 1){
						toggleButtonA.setChecked(true);
						toggleButtonB.setChecked(false);
				 } else if(value == 0){
						toggleButtonA.setChecked(false);
						toggleButtonB.setChecked(true);
				 }
				 toggleButtonA.requestLayout();
				 toggleButtonB.requestLayout();

			} catch(Exception e) {
				 Log.e(TAG, "refreshButtons: " + e);
			}
	 }

	 private void initWithAttrs(Context context, AttributeSet attrs) {
			TypedArray a = null;
			View root;

			if(attrs == null){
				 root = inflate(context, R.layout.inherited, this);

			} else{
				 a = context.getTheme().obtainStyledAttributes(
							attrs,
							R.styleable.ToggleButton,
							0, 0);

				 final int ll = a.getInt(R.styleable.ToggleButton_tb_layoutDirection, ViewCompat.LAYOUT_DIRECTION_INHERIT);
				 root = inflate(context, R.layout.inherited, this);
				 ViewCompat.setLayoutDirection(root, ll);
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
							 if(null != value && value == 1){
									if(isMandatory){
										 value = 1;
									} else{
										 value = null;
										 checkedChangeListener.onChecked(ButtonsEnum.BUTTON_NON);
									}
							 } else{
									value = 1;
									checkedChangeListener.onChecked(ButtonsEnum.BUTTON_A);
							 }
							 refreshButtons();
						}
				 });

				 toggleButtonB.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							 if(null != value && value == 0){
									if(isMandatory){
										 value = 0;
									} else{
										 value = null;
										 checkedChangeListener.onChecked(ButtonsEnum.BUTTON_NON);
									}
							 } else{
									value = 0;
									checkedChangeListener.onChecked(ButtonsEnum.BUTTON_B);
							 }
							 refreshButtons();
						}
				 });

				 if(attrs == null){
						isMandatory = true;
						toggleButtonA.setChecked(true);
						checkedChangeListener.onChecked(ButtonsEnum.BUTTON_A);

				 } else{
						isMandatory = a.getBoolean(R.styleable.ToggleButton_tb_isMandatory, false);
						final int defaultButton = a.getInteger(R.styleable.ToggleButton_tb_default_button, -1);
						switch(defaultButton) {
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

						final String t = a.getString(R.styleable.ToggleButton_tb_title);
						setTitle(t);

						final int titleTextSize = a.getDimensionPixelSize(R.styleable.ToggleButton_tb_titleTextSize, -1);
						if(titleTextSize != -1){
							 title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
						}

						final int titleColor = a.getColor(R.styleable.ToggleButton_tb_titleTextColor, ContextCompat.getColor(context, android.R.color.primary_text_light));
						title.setTextColor(titleColor);

						String tba = a.getString(R.styleable.ToggleButton_tb_A_text);
						this.toggleButtonA.setTextOff(tba);
						this.toggleButtonA.setTextOn(tba);
						this.toggleButtonA.setText(tba);

						String tbb = a.getString(R.styleable.ToggleButton_tb_B_text);
						this.toggleButtonB.setTextOff(tbb);
						this.toggleButtonB.setTextOn(tbb);
						this.toggleButtonB.setText(tbb);

						if(a.hasValue(R.styleable.ToggleButton_tb_background_A)){
							 int buttonABackgroundResId = a.getResourceId(R.styleable.ToggleButton_tb_background_A, R.drawable.bg_toggle_selector);
							 setBackgroundA(buttonABackgroundResId);
						} else{
							 int buttonAbgType = a.getInt(R.styleable.ToggleButton_tb_background_A_type, 1);
							 setBackgroundAType(buttonAbgType);
						}

						if(a.hasValue(R.styleable.ToggleButton_tb_background_B)){
							 int buttonBBackgroundResId = a.getResourceId(R.styleable.ToggleButton_tb_background_B, R.drawable.bg_toggle_selector);
							 setBackgroundB(buttonBBackgroundResId);
						} else{
							 int buttonBbgType = a.getInt(R.styleable.ToggleButton_tb_background_B_type, 1);
							 setBackgroundBType(buttonBbgType);
						}

						toggleButtonA.requestLayout();
						toggleButtonB.requestLayout();
				 }

			} catch(Exception e) {
				 Log.e(TAG, "initWithAttrs: " + e);
			} finally {
				 if(a != null)
						a.recycle();
			}
	 }

	 public void setBackgroundA(int buttonABackgroundResId) {
			toggleButtonA.setBackgroundResource(buttonABackgroundResId);
	 }

	 public void setBackgroundB(int buttonABackgroundResId) {
			toggleButtonB.setBackgroundResource(buttonABackgroundResId);
	 }

	 public void setBackgroundAType(@ButtonBgEnum int type) {
			switch(type) {
				 case BLUE:
						toggleButtonA.setBackgroundResource(R.drawable.bg_toggle_selector);
						break;

				 case GREEN:
						toggleButtonA.setBackgroundResource(R.drawable.bg_toggle_green);
						break;
			}

	 }

	 public void setBackgroundBType(@ButtonBgEnum int type) {
			switch(type) {
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

	 public void setTitle(String title) {
			if(TextUtils.isEmpty(title)){
				 this.title.setVisibility(GONE);
			} else{
				 this.title.setVisibility(VISIBLE);
			}

			if(isMandatory){
				 this.title.setText(title + " *");
			} else{
				 this.title.setText(title);
			}
	 }

	 public void setTitle(@StringRes int titleResId) {
			setTitle(getContext().getString(titleResId));
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

	 @Retention(RetentionPolicy.SOURCE)
	 @Documented
	 @IntDef({BLUE, ButtonBgEnum.GREEN})
	 public @interface ButtonBgEnum {
			int BLUE = 1;
			int GREEN = 2;
	 }

	 public interface OnCheckedChangeListener {
			void onChecked(@ButtonsEnum int button);
	 }
}
