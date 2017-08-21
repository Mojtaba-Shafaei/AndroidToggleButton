package com.mojtaba_shafaei.android.android_toggle_button_example;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mojtaba_shafaei.android.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Typeface typeface = Typeface.createFromAsset(getAssets(), "AlfaSlabOne-Regular.ttf");

        findViewById(R.id.btn_menu_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        ToggleButton toggleButton = findViewById(R.id.tb3);
        toggleButton.setTitleTypeface(typeface);
        toggleButton.setErrorEnabled(true);
        toggleButton.setError(R.string.errorText);

        final View root = findViewById(R.id.root);

        findViewById(R.id.btnRtl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCompat.setLayoutDirection(root, ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        });

        findViewById(R.id.btnLtr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCompat.setLayoutDirection(root, ViewCompat.LAYOUT_DIRECTION_LTR);
            }
        });
    }
}
