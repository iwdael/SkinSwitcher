package com.hacknife.example;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hacknife.skinswitcher.SkinSwitcher;
import com.hacknife.skinswitcher.SkinSwitcherFactory;


/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SkinSwitcher.addSkinSwitcherAdapter(new Adapter());
        SkinSwitcher.setFactory(getLifecycle(), getLayoutInflater(), new SkinSwitcherFactory());
    }


}
