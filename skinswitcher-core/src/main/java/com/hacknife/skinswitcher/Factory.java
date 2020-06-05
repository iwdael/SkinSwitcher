package com.hacknife.skinswitcher;

import android.view.LayoutInflater;

import androidx.lifecycle.LifecycleEventObserver;

import com.hacknife.skinswitcher.SkinSwitcherAdapter;
import com.hacknife.skinswitcher.SkinSwitcherFactory;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public interface Factory extends LayoutInflater.Factory2, LifecycleEventObserver {
    void skinSwitch();

    SkinSwitcherFactory addSkinSwitcherAdapters(SkinSwitcherAdapter... adapters);
}
