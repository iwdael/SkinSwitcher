package com.hacknife.example;

import android.os.Bundle;
import android.widget.TextView;
/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class MainActivity extends BaseActivity {
    TextView tv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_view = findViewById(R.id.tv_view);
    }

}
