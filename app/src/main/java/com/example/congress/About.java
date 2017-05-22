package com.example.congress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Hank on 12/1/2016.
 */

public class About extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);
        final ImageView button = (ImageView) findViewById(R.id.but);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            };
        });
    }
}
