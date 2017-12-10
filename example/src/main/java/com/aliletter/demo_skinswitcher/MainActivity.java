package com.aliletter.demo_skinswitcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aliletter.skinswitcher.SkinFactory;

public class MainActivity extends BaseActivity {
    TextView tv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_view = findViewById(R.id.tv_view);
        tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TAG", "-----------------onClick----------------");
                SkinFactory.initSkinFactory(getApplicationContext());
                SkinFactory.loadSkinPackage("/sdcard/Music/app-debug.apk");
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }

}
