package com.mojtaba_shafaei.android.android_toggle_button_example;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.mojtaba_shafaei.androidToggleButton.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Typeface typeface = Typeface.createFromAsset(getAssets(), "AlfaSlabOne-Regular.ttf");

        findViewById(R.id.btn_menu_more).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        });

        ToggleButton toggleButton = findViewById(R.id.tb3);
        toggleButton.setTitleTypeface(typeface);
        toggleButton.setErrorEnabled(true);
        toggleButton.setError(R.string.errorText);
        toggleButton.setOnCheckedChangeListener(button -> {
            if (toggleButton.getValue() ==null) {
                Toast.makeText(this, "non button checked", Toast.LENGTH_SHORT).show();
            } else if (toggleButton.getValue().equals(0)) {
                Toast.makeText(this, "button value = 0", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "button value = 1", Toast.LENGTH_SHORT).show();
            }
        });

        final View root = findViewById(R.id.root);

        findViewById(R.id.btnRtl).setOnClickListener(view -> ViewCompat.setLayoutDirection(root, ViewCompat.LAYOUT_DIRECTION_RTL));

        findViewById(R.id.btnLtr).setOnClickListener(view -> ViewCompat.setLayoutDirection(root, ViewCompat.LAYOUT_DIRECTION_LTR));

        final ToggleButton toggle = findViewById(R.id.toggle);
        toggle.setButtonAText("تجربه کاری");
        toggle.setButtonBText("رشته تحصیلی");
        toggle.setOnCheckedChangeListener(button -> Log.i("TAG", "onChecked: " + button + ",value=" + toggle.getValue()));

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bgs_button);
        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.bgs_button);
        // toggle.setButtonACompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        // toggle.setButtonBCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);

        findViewById(R.id.btn_test_val).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: " + toggle.getValue());

            }
        });
    }
}
