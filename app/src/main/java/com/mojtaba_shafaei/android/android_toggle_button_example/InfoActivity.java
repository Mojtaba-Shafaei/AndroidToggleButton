package com.mojtaba_shafaei.android.android_toggle_button_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ((TextView) findViewById(R.id.version)).setText("version: " + BuildConfig.VERSION_NAME);
    }
}
