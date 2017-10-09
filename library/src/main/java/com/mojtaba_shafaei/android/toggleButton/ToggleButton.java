package com.mojtaba_shafaei.android.toggleButton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class ToggleButton extends LinearLayoutCompat {
	 private final String TAG = "MyToggleButton";
	 private Integer value = null;
	 private TextView title, error;
	 private android.widget.ToggleButton toggleButtonA, toggleButtonB;
	 private boolean isMandatory = false;

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
									}
							 } else{
									value = 1;
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
									}
							 } else{
									value = 0;
							 }
							 refreshButtons();
						}
				 });

				 if(attrs == null){
						isMandatory = true;
						toggleButtonA.setChecked(true);


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
						if(isMandatory){
							 this.title.setText(t + " *");
						} else{
							 this.title.setText(t);
						}

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
